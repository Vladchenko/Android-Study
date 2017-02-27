package com.example.vladislav.androidstudy.fragments_activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vladislav.androidstudy.R;

/*
 When fragments are included into an activity, one has to implement OnFragmentInteractionListener from all the fragments
  */
public class FragmentsActivity extends AppCompatActivity implements
        Fragment1.OnFragmentInteractionListener, Fragment2.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
