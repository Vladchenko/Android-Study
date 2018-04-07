package com.example.vladislav.androidstudy.jobs.criminalrecords.ui;


import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.criminalrecords.Crime;
import com.example.vladislav.androidstudy.jobs.criminalrecords.CriminalRecordsAdapter;
import com.example.vladislav.androidstudy.jobs.criminalrecords.DBHelper;
import com.example.vladislav.androidstudy.jobs.criminalrecords.ICrimeItemClickListener;
import com.example.vladislav.androidstudy.jobs.criminalrecords.data_providing.CrimesAsyncTask;
import com.example.vladislav.androidstudy.jobs.criminalrecords.data_providing.CrimesCursorLoader;

import java.util.List;

import static com.example.vladislav.androidstudy.jobs.criminalrecords.CriminalRecordsAdapter.DATE_FORMAT;

/**
 * Fragment representing a list of a crimes
 */
public class CriminalRecordListFragment extends Fragment implements ICrimeItemClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final String FRAGMENT_TAG = CriminalRecordListFragment.class.getSimpleName();
    private List<Crime> mCrimes;

    private FloatingActionButton mActionButton;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mEmptyListTextView;
    private ProgressBar mProgressBar;

    private DBHelper mDbHelper;

    public static CriminalRecordListFragment newInstance() {
        CriminalRecordListFragment fragment = new CriminalRecordListFragment();
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
//        mDbHelper.createTableWithColumns(mDbHelper.getWritableDatabase());
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCriminalRecordFragment(true, null);
            }
        });
        setupAddButtonForegroundColor(R.color.color_white);
        // Getting loadermanager for cursorloader and initializing it.
        getActivity().getSupportLoaderManager().initLoader(0, null, this);
        if (mCrimes == null) {
            getActivity().getSupportLoaderManager().getLoader(0).forceLoad();
        } else {
            setupRecyclerView(mCrimes);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    // Callback method. It is invoked when a recyclerview's crime item clicked.
    @Override
    public void onCrimeItemClick(String crimeId) {
        // Get a crime entry on such id from DB and launch CriminalRecordFragment with this entry
        // in bundle.
        Crime crime = mDbHelper.getCrimeById(mDbHelper.getReadableDatabase(), crimeId);
        if (crime != null) {
            Bundle bundle = new Bundle();
            bundle.putString(CriminalRecordFragment.CRIME_ID, crime.getId());
            bundle.putString(CriminalRecordFragment.CRIME_TITLE_KEY, crime.getTitle());
            bundle.putString(CriminalRecordFragment.CRIME_DESCRIPTION_KEY, crime.getDescription());
            bundle.putString(CriminalRecordFragment.CRIME_DATE_KEY, DATE_FORMAT.format(crime.getDate()));
            bundle.putBoolean(CriminalRecordFragment.CRIME_SOLVED_KEY, crime.isSolved());
            addCriminalRecordFragment(true, bundle);
        } else {
            Toast.makeText(getActivity(), "No crime found to edit. Something is wrong.", Toast.LENGTH_SHORT);
        }

    }

    public void setupRecyclerView(List<Crime> crimes) {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CriminalRecordsAdapter(crimes, this);
        mRecyclerView.setAdapter(mAdapter);
        displayEmptyListMessage(crimes);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri contentUri = Uri.parse("content://com.example.vladislav.androidstudy.jobs." +
                "criminalrecords.data_providing.CrimesContentProvider/crimes");
        return new CursorLoader(getActivity(), contentUri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        loader.forceLoad();
        if (cursor != null) {
            setupRecyclerView(mDbHelper.getCrimeData(cursor));
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void addCriminalRecordFragment(boolean addToBackStack, Bundle bundle) {
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

    public void displayEmptyListMessage(List<Crime> crimes) {
        if (crimes == null || crimes.size() == 0) {
            mEmptyListTextView.setVisibility(View.VISIBLE);
        } else {
            mEmptyListTextView.setVisibility(View.INVISIBLE);
        }
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public void setCrimes(List<Crime> mCrimes) {
        this.mCrimes = mCrimes;
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }


}
