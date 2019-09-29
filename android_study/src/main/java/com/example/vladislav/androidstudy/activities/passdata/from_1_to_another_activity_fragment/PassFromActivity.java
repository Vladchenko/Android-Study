package com.example.vladislav.androidstudy.activities.passdata.from_1_to_another_activity_fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vladislav.androidstudy.R;

/**
 * This activity is meant to send data from
 */
public class PassFromActivity extends AppCompatActivity {

    public static final String mKey = "someConstant";
    public static final String mKey2 = "someConstant2";

    public static Intent newIntent(Context context) {
        return new Intent(context, PassFromActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_from);
        startActivityWithParcelable();
//        startFragmentWithData();
    }

    // Passing parameters using putExtra() method in Intent.
//    https://stackoverflow.com/questions/768969/passing-a-bundle-on-startactivity
    private void startActivityWithData() {

        String stringParam = "Value from 1st activity";
        Integer intParam = 5;

        Intent i = new Intent(this, PassToActivity.class);

        i.putExtra(mKey, stringParam);
        i.putExtra(mKey2, intParam);

        startActivity(i);

        // These parameters could be used in a called activity like following:
        // Bundle bundle = i.getExtras();
        // String value = bundle.get(mKey);
        // or
        // String value = getIntent().getExtras().getString(mKey)
    }

    // Passing parameters using Bundle from Intent.
//    https://stackoverflow.com/questions/768969/passing-a-bundle-on-startactivity
    private void startActivityWithData2() {

        String value = "Value2 from 1st activity";
        Intent intent = new Intent(this, PassToActivity.class);
        // There is no bundle instance in intent initially. One has to put a new bundle inside it.
        // Next row throws NPE.
        Bundle extras = intent.getExtras();
        extras.putString(mKey, value);
        startActivity(intent);

        // These parameters could be used in a called activity like following:
        // Bundle bundle = i.getExtras();
        // String value = bundle.get(mKey);
        // or
        // String value = getIntent().getExtras().getString(mKey)
    }

    // Passing parameters creating a new Bundle.
    // Intent doesn't have a bundle, once created, one has to add it manually.
    private void startActivityWithData3() {

        String value = "Value3 from 1st activity";
        Intent intent = new Intent(this, PassToActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(mKey, value);
        intent.putExtras(bundle);
        startActivity(intent);

        // These parameters could be used in a called activity like following:
        // Bundle bundle = i.getExtras();
        // String value = bundle.get(mKey);
        // or
        // String value = getIntent().getExtras().getString(mKey)
    }

    private void startActivityWithParcelable() {
        // Starting an activity with passing some data into it, using bundle.
        // Do not forget to write unit tests on parcelables.
        Bundle bundle = new Bundle();
        bundle.putParcelable(mKey, new MyParcelable2());
        Intent intent = new Intent(this, PassToActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    // Passing data from activity to fragment
    void startFragmentWithData() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString(mKey, "String from StartFragmentActivity");

        Fragment fragment = new PassToFragment();
        fragment.setArguments(bundle);

        transaction.add(R.id.fragment_layout, fragment);
        transaction.commit();
    }
}
