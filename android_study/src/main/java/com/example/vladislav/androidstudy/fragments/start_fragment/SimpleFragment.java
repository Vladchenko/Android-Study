package com.example.vladislav.androidstudy.fragments.start_fragment;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vladislav.androidstudy.R;

/**
 * This fragment displays its lifecycle callbacks
 */
public class SimpleFragment extends Fragment {

    private static final String TAG = SimpleFragment.class.getSimpleName();
    public static final String FRAGMENT_TAG = "SimpleFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, TAG + " onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
        Log.i(TAG, TAG + " onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, TAG + " onCreateView");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simple, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, TAG + " onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, TAG + " onActivityCreated");
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i(TAG, TAG + " onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, TAG + " onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, TAG + " onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, TAG + " onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, TAG + " onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, TAG + " onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, TAG + " onDetach()");
    }
}
