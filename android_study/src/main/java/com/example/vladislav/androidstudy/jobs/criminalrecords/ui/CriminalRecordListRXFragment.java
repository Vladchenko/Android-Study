package com.example.vladislav.androidstudy.jobs.criminalrecords.ui;


import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.criminalrecords.Crime;
import com.example.vladislav.androidstudy.jobs.criminalrecords.CriminalRecordsAdapter;
import com.example.vladislav.androidstudy.jobs.criminalrecords.DBHelper;
import com.example.vladislav.androidstudy.jobs.criminalrecords.ICrimeItemClickListener;
import com.example.vladislav.androidstudy.jobs.criminalrecords.ParcelableCrimesList;
import com.example.vladislav.androidstudy.jobs.criminalrecords.data_providing.CrimesRxLoader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Fragment representing a list of a crimes
 */
public class CriminalRecordListRXFragment extends Fragment implements ICrimeItemClickListener {

    public static final String FRAGMENT_TAG = CriminalRecordListRXFragment.class.getSimpleName();
    public static final String CRIME_POSITION = "Crime position";
    public static final String CRIMES_LIST = "Crimes list";

    private ParcelableCrimesList mCrimes;

    private FloatingActionButton mActionButton;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mEmptyListTextView;
    private ProgressBar mProgressBar;

    private DBHelper mDbHelper;
    private Disposable mCrimesObservable;

    public static CriminalRecordListRXFragment newInstance() {
        CriminalRecordListRXFragment fragment = new CriminalRecordListRXFragment();
        // Dropping a database in case a previous one is obsolete
//        mDbHelper.dropTable(mDbHelper.getWritableDatabase());
//        Bundle args = new Bundle();
//        args.putInt("someInt", someInt);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDbHelper = DBHelper.getInstance(getActivity(), CriminalRecordsActivity.DATABASE_NAME);
        // Dropping a table to remove all the entries at once
//        mDbHelper.dropTable(mDbHelper.getReadableDatabase());
        // Creating a table if it doesn't exist
        mDbHelper.createTableWithColumns(mDbHelper.getWritableDatabase());

//        mCrimesObservable = createObservable()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(result -> processSuccess(),
//                        result -> processError());

        setupRecyclerView(getCrimes());

        View view = inflater.inflate(R.layout.fragment_criminal_record_list, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActionButton = (FloatingActionButton) view.findViewById(R.id.add_crime_button);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.crime_records_recycler_view);
        mEmptyListTextView = (TextView) view.findViewById(R.id.empty_crime_list_text_view);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCrimeFragment(true, null);
            }
        });
        setupAddButtonForegroundColor(R.color.color_white);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDbHelper.close();
        mCrimesObservable.dispose();
    }

    // Callback method. It is invoked when a recyclerview's crime item clicked.
    @Override
    public void onCrimeItemClick(int crimeId) {
        addCrimeViewPagerFragment(true, crimeId);
    }

    private void processSuccess() {
        initViews();
        setupRecyclerView(mCrimes);
    }

    private void processError() {
        //TODO
    }

    private Observable createObservable() {
        return Observable.create(
                emitter -> {
                    Thread thread = new Thread(() -> {
                        try {
                            mCrimes = mDbHelper.getCrimeData(mDbHelper.getReadableDatabase());
                            emitter.onNext(mCrimes);
                        } catch (Exception e) {
                            emitter.onError(e);
                        }
                    });
                    // Imitating some work by putting thread to sleep
                    thread.sleep(3000);
                    thread.start();
                }
        );
    }

    private List<Crime> getCrimes() {
        CrimesRxLoader crimesRxLoader = new CrimesRxLoader(mDbHelper);
        return crimesRxLoader.getCrimes();
    }

    private void setupRecyclerView(List<Crime> crimes) {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CriminalRecordsAdapter(crimes, this);
        mRecyclerView.setAdapter(mAdapter);
        displayEmptyListMessage(crimes);
        mAdapter.notifyDataSetChanged();
    }

    private void addCrimeViewPagerFragment(boolean addToBackStack, int crimePosition) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment crimesViewPagerFragment = fragmentManager.findFragmentByTag(
                CrimesViewPagerFragment.FRAGMENT_TAG);
        if (crimesViewPagerFragment == null) {
            crimesViewPagerFragment = CrimesViewPagerFragment.newInstance();
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        if (crimePosition > -1) {
            Bundle bundle = new Bundle();
            bundle.putInt(CRIME_POSITION, crimePosition);
            bundle.putParcelableArrayList(CRIMES_LIST, mCrimes);
            crimesViewPagerFragment.setArguments(bundle);
        }
        transaction.replace(R.id.fragment_container, crimesViewPagerFragment,
                CrimesViewPagerFragment.FRAGMENT_TAG)
                .commit();
    }

    private void addCrimeFragment(boolean addToBackStack, Bundle bundle) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment criminalRecordFragment = fragmentManager.findFragmentByTag(
                CriminalRecordFragment.FRAGMENT_TAG);
        if (criminalRecordFragment == null) {
            criminalRecordFragment = CriminalRecordFragment.newInstance();
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        if (bundle != null) {
            criminalRecordFragment.setArguments(bundle);
        }
        transaction.replace(R.id.fragment_container, criminalRecordFragment,
                CriminalRecordFragment.FRAGMENT_TAG)
                .commit();
    }

    private void setupAddButtonForegroundColor(int color) {
        PorterDuff.Mode mMode = PorterDuff.Mode.SRC_ATOP;
        Drawable d = getActivity().getResources().getDrawable(R.drawable.ic_add_black_24dp);
        d.setColorFilter(getResources().getColor(color), mMode);
    }

    private void displayEmptyListMessage(List<Crime> crimes) {
        if (crimes == null || crimes.size() == 0) {
            mEmptyListTextView.setVisibility(View.VISIBLE);
        } else {
            mEmptyListTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void initViews() {
        mProgressBar.setVisibility(View.GONE);
    }
}
