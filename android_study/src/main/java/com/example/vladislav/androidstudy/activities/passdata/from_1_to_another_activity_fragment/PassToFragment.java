package com.example.vladislav.androidstudy.activities.passdata.from_1_to_another_activity_fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.vladislav.androidstudy.R;

import static com.example.vladislav.androidstudy.activities.passdata.from_1_to_another_activity_fragment.
        PassFromActivity.mKey;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassToFragment extends Fragment {

    private TextView mTextView;
    private String mText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mText = bundle.getString(mKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pass_to, container, false);
        mTextView = (TextView)view.findViewById(R.id.get_data_text_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextView.setText(mText);
    }
}
