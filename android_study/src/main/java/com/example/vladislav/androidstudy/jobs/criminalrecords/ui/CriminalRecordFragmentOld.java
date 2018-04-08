package com.example.vladislav.androidstudy.jobs.criminalrecords.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.criminalrecords.Crime;
import com.example.vladislav.androidstudy.jobs.criminalrecords.DBHelper;

/**
 * Fragment used before using a ViewPager
 *
 * A simple {@link Fragment} subclass.
 */
public class CriminalRecordFragmentOld extends Fragment {

    public static final String FRAGMENT_TAG = CriminalRecordFragmentOld.class.getSimpleName();

    public static final String CRIME_ID = "Crime Id";
    public static final String CRIME_TITLE_KEY = "Crime record key";
    public static final String CRIME_DESCRIPTION_KEY = "Crime description key";
    public static final String CRIME_DATE_KEY = "Crime date key";
    public static final String CRIME_SOLVED_KEY = "Crime solved key";

    private String mId;

    private Button mCancelButton;
    private Button mSaveButton;
    private Button mDateButton;
    private Crime mCrime;
    private CheckBox mSolvedCheckBox;
    private EditText mDescriptionEditText;
    private EditText mTitleEditText;

    private DBHelper mDbHelper;

    public static CriminalRecordFragmentOld newInstance() { //int someInt
        CriminalRecordFragmentOld myFragment = new CriminalRecordFragmentOld();
//        Bundle args = new Bundle();
//        args.putInt("someInt", someInt);
//        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mCrime = new Crime();
        Bundle args = getArguments();
        View view = inflater.inflate(R.layout.criminal_record_fragment, container, false);
        mSolvedCheckBox = (CheckBox)view.findViewById(R.id.solved_checkbox);
        mCancelButton = (Button)view.findViewById(R.id.cancel_button);
        mSaveButton = (Button)view.findViewById(R.id.save_button);
        mDescriptionEditText = (EditText)view.findViewById(R.id.crime_description_edit_text);
        mTitleEditText = (EditText)view.findViewById(R.id.crime_title_edit_text);
        mDateButton = (Button)view.findViewById(R.id.date_button);
        if (args != null) {
            mId = args.getString(CRIME_ID);
            mTitleEditText.setText(args.getString(CRIME_TITLE_KEY));
            mDescriptionEditText.setText(args.getString(CRIME_DESCRIPTION_KEY));
            mDateButton.setText("CREATION DATE: " + args.getString(CRIME_DATE_KEY));
            mSolvedCheckBox.setChecked(args.getBoolean(CRIME_SOLVED_KEY));
//            mCrime.set
            // Set all the fields to Crime
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSolvedCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Removing an existing entry, since we'll need to add a modified one
                mDbHelper.removeCrimeById(mDbHelper.getReadableDatabase(), mId);
                mCrime.setDescription(mDescriptionEditText.getText().toString());
                mCrime.setTitle(mTitleEditText.getText().toString());
                mCrime.setSolved(mSolvedCheckBox.isChecked());
                if (!mCrime.getTitle().isEmpty()
                        && !mCrime.getDescription().isEmpty()) {
                    mDbHelper.putCrimeToTable(mDbHelper.getWritableDatabase(), mCrime);
                    Toast.makeText(getActivity(), "New crime has been added",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Crime title or description is empty",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Make pass to a listfragment
                getFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = DBHelper.getInstance(getActivity(), CriminalRecordsActivity.DATABASE_NAME);
    }
}