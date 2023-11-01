package com.example.vladislav.androidstudy.jobs.criminalrecords.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.criminalrecords.Crime;
import com.example.vladislav.androidstudy.jobs.criminalrecords.DBHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class CriminalRecordFragment extends Fragment {

    public static final String FRAGMENT_TAG = CriminalRecordFragment.class.getSimpleName();

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

    public static CriminalRecordFragment newInstance() {
        CriminalRecordFragment myFragment = new CriminalRecordFragment();
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mCrime = new Crime();
        Bundle args = getArguments();
        View view = inflater.inflate(R.layout.criminal_record_fragment, container, false);
        mSolvedCheckBox = (CheckBox)view.findViewById(R.id.solved_checkbox);
        mCancelButton = view.findViewById(R.id.cancel_button);
        mSaveButton = view.findViewById(R.id.save_button);
        mDescriptionEditText = view.findViewById(R.id.crime_description_edit_text);
        mTitleEditText = view.findViewById(R.id.crime_title_edit_text);
        mDateButton = view.findViewById(R.id.date_button);
        if (args != null) {
            mId = args.getString(CRIME_ID);
            mTitleEditText.setText(args.getString(CRIME_TITLE_KEY));
            mDescriptionEditText.setText(args.getString(CRIME_DESCRIPTION_KEY));
            mDateButton.setText("CREATION DATE: " + args.getString(CRIME_DATE_KEY));
            mSolvedCheckBox.setChecked(args.getBoolean(CRIME_SOLVED_KEY));
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
                String message;
                Crime crime = mDbHelper.getCrimeById(mDbHelper.getReadableDatabase(), mId);
                mCrime.setDescription(mDescriptionEditText.getText().toString());
                mCrime.setTitle(mTitleEditText.getText().toString());
                mCrime.setSolved(mSolvedCheckBox.isChecked());
                if (!mCrime.getTitle().isEmpty()
                        && !mCrime.getDescription().isEmpty()) {
                    if (crime != null) {
                        mDbHelper.updateCrime(mDbHelper.getWritableDatabase(), crime);
                        message = "Crime has been modified";
                    } else {
                        mDbHelper.putCrimeToTable(mDbHelper.getWritableDatabase(), mCrime);
                        message = "New crime has been added";
                    }
//                    mDbHelper.close();
//                    if (mId == null) {
//                        message = "New crime has been added";
//                    } else {
//                        message = "Crime has been modified";
//                    }
                    Toast.makeText(getActivity(), message,
                            Toast.LENGTH_SHORT).show();
                    // Make a callback to inform a list fragment that a new entry is added or an
                    // existing one modified.
//                    getActivity().getSupportLoaderManager().getLoader(0).startLoading();
                    // Make pass to a listfragment
                    getFragmentManager().popBackStack();
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