package com.example.vladislav.androidstudy.jobs.criminalrecords;


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

import static com.example.vladislav.androidstudy.jobs.criminalrecords.CriminalRecordsActivity.DATABASE_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class CriminalRecordFragment extends Fragment {

    public static final String FRAGMENT_TAG = CriminalRecordFragment.class.getSimpleName();

    public static final String CRIME_TITLE_KEY = "Crime record key";
    public static final String CRIME_DESCRIPTION_KEY = "Crime description key";
    public static final String CRIME_DATE_KEY = "Crime date key";
    public static final String CRIME_SOLVED_KEY = "Crime date key";

    private Button mCancelButton;
    private Button mSaveButton;
    private Button mDateButton;
    private Crime mCrime;
    private CheckBox mSolvedCheckBox;
    private EditText mDescriptionEditText;
    private EditText mTitleEditText;

    private DBHelper mDbHelper;

    public static CriminalRecordFragment newInstance() { //int someInt
        CriminalRecordFragment myFragment = new CriminalRecordFragment();
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
            mTitleEditText.setText(args.getString(CRIME_TITLE_KEY));
            mDescriptionEditText.setText(args.getString(CRIME_DESCRIPTION_KEY));
            mDateButton.setText("CREATION DATE: " + args.getString(CRIME_DATE_KEY));
            mSolvedCheckBox.setChecked(Boolean.parseBoolean(args.getString(CRIME_SOLVED_KEY)));
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
                mCrime.setDescription(mDescriptionEditText.getText().toString());
                mCrime.setTitle(mTitleEditText.getText().toString());
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
        mDbHelper = new DBHelper(getActivity(), DATABASE_NAME);
    }
}