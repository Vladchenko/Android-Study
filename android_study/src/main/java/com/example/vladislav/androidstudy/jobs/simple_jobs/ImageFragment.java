package com.example.vladislav.androidstudy.jobs.simple_jobs;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vladislav.androidstudy.R;

/**
 * Fragment displaying an image
 */
public class ImageFragment extends Fragment {

    public static final String FRAGMENT_TAG = ImageFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

}
