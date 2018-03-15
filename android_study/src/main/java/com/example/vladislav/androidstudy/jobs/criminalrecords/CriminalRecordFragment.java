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

import java.util.Date;

import static com.example.vladislav.androidstudy.jobs.criminalrecords.CriminalRecordsActivity.DATABASE_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class CriminalRecordFragment extends Fragment {

    public static final String FRAGMENT_TAG = CriminalRecordFragment.class.getSimpleName();

    private CheckBox mSolvedCheckBox;
    private Button mCancelButton;
    private Button mSaveButton;
    private Crime mCrime;
    private EditText mDescriptionEditText;
    private EditText mTitleEditText;

    private DBHelper mDbHelper;

    public CriminalRecordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.criminal_record_fragment, container, false);
        mSolvedCheckBox = (CheckBox)view.findViewById(R.id.solved_checkbox);
        mCancelButton = (Button)view.findViewById(R.id.cancel_button);
        mSaveButton = (Button)view.findViewById(R.id.save_button);
        mDescriptionEditText = (EditText)view.findViewById(R.id.crime_description_edit_text);
        mTitleEditText = (EditText)view.findViewById(R.id.crime_title_edit_text);
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
                mCrime = new Crime();
                mCrime.setDescription(mDescriptionEditText.getText().toString());
                mCrime.setTitle(mTitleEditText.getText().toString());
                if (!mCrime.getTitle().isEmpty()
                        && !mCrime.getDescription().isEmpty()) {
                    mDbHelper.putCrimeToTable(mDbHelper.getWritableDatabase(), mCrime);
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
            }
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
//        mDbHelper = new DBHelper(getActivity(), DATABASE_NAME);
//        mDbHelper.createTableWithColumns(mDbHelper.getWritableDatabase());
    }
}