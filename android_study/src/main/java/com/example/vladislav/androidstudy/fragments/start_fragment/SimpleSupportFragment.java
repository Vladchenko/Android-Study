package com.example.vladislav.androidstudy.fragments.start_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vladislav.androidstudy.R;

/**
 * Fragment that extends android.support.v4.app.Fragment, but not android.app.Fragment. This is a
 * fragment that is compatible with an older versions of android OS.
 */
public class SimpleSupportFragment extends Fragment {

    public static final String FRAGMENT_TAG = "SimpleSupportFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simple_support, container, false);
    }

}
