package com.example.vladislav.androidstudy.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.activities.dynamic_layout.ProgrammaticLayoutActivity;
import com.example.vladislav.androidstudy.activities.orientation_change.RotationActivity;
import com.example.vladislav.androidstudy.beans.Planet;
import com.example.vladislav.androidstudy.intents.IntentsActivity;
import com.example.vladislav.androidstudy.jobs.background_jobs.asynctask.AsyncTasksActivity;
import com.example.vladislav.androidstudy.jobs.listviewing.ListsViewActivity;
import com.example.vladislav.androidstudy.jobs.simple_jobs.LayoutingActivity;
import com.example.vladislav.androidstudy.logic.ButtonsHandlers;
import com.example.vladislav.androidstudy.services.ServicesActivity;

/**
 * This activity is picked to be the foremost that is invoked
 */
public class InitialActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ACTIVITY_RESULT_ID = "result";
    private final String mLogTag = getClass().getCanonicalName();
    private String mYouTubeVideoID = "Fee5vbFLYM4";
    private Button mButton;
    private ButtonsHandlers mButtonsHandlers;

    // This method required to run this app in a cellphone
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        setButtonsClicks();
        mButtonsHandlers = new ButtonsHandlers(this);

        // That's how we reach the resources.
        Log.i("Log message: ", "Application name is:" + getResources().getString(R.string.app_name));
        // getResources() may be omitted
        Log.i("Log message: ", "Application name is:" + getString(R.string.app_name));

        Log.i(mLogTag, "onCreate");
//        utils.showToast(this, "onCreate");
    }

    public static Intent newIntent(Context context, String id, String value) {
        Intent intent = new Intent(context, InitialActivity.class);
        intent.putExtra(id, value);
        return intent;
    }

    // region Lifecycle

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(mLogTag, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(mLogTag, "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(mLogTag, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(mLogTag, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(mLogTag, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(mLogTag, "onDestroy");
    }

    // endregion

    // This callback fires when a mButton from ResultActivity returns a result. It's done in a
    // OnClickListener() in a android:id="@+id/go_back_button" mButton.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // When nothing retrieved, finish the method.
        if (data != null) { // btw, can data be null at all ?
            String text = data.getStringExtra(InitialActivity.ACTIVITY_RESULT_ID);
            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    "Received result is: " + text,
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // and shows message saying that it is a landscape mode
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // or shows message saying that it is a portrait mode
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
        // Don't forget about next row in a manifest.xml
        // android:configChanges="orientation|keyboardHidden|screenSize
    }

    private void setButtonsClicks() {
        mButton = (Button) findViewById(R.id.layouting_button);
        // Making a mButton to be clickable and click to perform a transfer to a layouting mActivity.
        // Another way of doing this - make a separate method in this class that invokes another
        // mActivity and assign it to a onClick() method in a respectful mLayout's mButton
        // (say - mButton is - android:id="@+id/widgets_button",
        // attribute is - android:onClick="gotoWidgetsActivity")
        // gotoWidgetsActivity - the very method invoked.
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitialActivity.this, LayoutingActivity.class);
                startActivity(intent);
            }
        });
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:123"));
//                startActivity(intent);
//            }
//        });
        mButton = (Button) findViewById(R.id.return_result_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(ResultActivity.newIntent(InitialActivity.this), 1);
            }
        });
        mButton = (Button) findViewById(R.id.list_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitialActivity.this, ListsViewActivity.class);
                startActivity(intent);
            }
        });
        mButton = (Button) findViewById(R.id.youtube_runner_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // It seems that emulator won't run video, one has to use a mobile device for it.
                runYoutubeVideo(mYouTubeVideoID);
            }
        });
//
//        ((Button) findViewById(R.id.banks_details_button)).setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(new Intent(InitialActivity.this, BanksDetailsActivity.class));
//                    }
//                }
//        );
        mButton = (Button) findViewById(R.id.rotation_button);
        // Making a mButton to be clickable and click to perform a transfer to a layouting mActivity.
        // Another way of doing this - make a separate method in this class that invokes another
        // mActivity and assign it to a onClick() method in a respectful mLayout's mButton
        // (say - mButton is - android:id="@+id/widgets_button",
        // attribute is - android:onClick="gotoWidgetsActivity")
        // gotoWidgetsActivity - the very method invoked.
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RotationActivity.newIntent(InitialActivity.this,
                        new Planet(10, 15, 20)));
            }
        });
        mButton = (Button) findViewById(R.id.alert_dialogue_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.intent_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.call_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.context_menu_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.programmatic_layout_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.asynctask_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.services_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.parcelable_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.send_email_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.image_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.fragments_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.menu_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.widgets_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.widgets2_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.aligning_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.add_buttons_button);
        mButton.setOnClickListener(this);

    }

//    public void gotoServicesActivity(View view) {
//        Intent intent = new Intent(InitialActivity.this, ServicesDemo1Activity.class);
//        intent.putExtra("isIntendedService", ((CheckBox) findViewById(R.id.intent_service_checkbox)).isChecked());
//        startActivity(intent);
//    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.send_email_button: {
                // One might also use android:onClick="gotoWidgetsActivity" in activity_initial.xml
                // to attach a handler, but this is not a good approach since a mLayout.
                mButtonsHandlers.sendEmail();
                break;
            }
            case R.id.image_button: {
                mButtonsHandlers.gotoImageActivity();
                break;
            }

            case R.id.fragments_button: {
                mButtonsHandlers.gotoFragmentsActivity();
                break;
            }
            case R.id.menu_button: {
                mButtonsHandlers.gotoMenuActivity();
                break;
            }
            case R.id.aligning_button: {
                mButtonsHandlers.gotoAligningActivity();
                break;
            }
            case R.id.widgets2_button: {
                mButtonsHandlers.gotoWidgets2Activity();
                break;
            }
            case R.id.widgets_button: {
                mButtonsHandlers.gotoWidgetsActivity();
                break;
            }
            case R.id.intent_button: {
                intent = new Intent(this, IntentsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.call_button: {
                mButtonsHandlers.makePhoneCall();
                break;
            }
            case R.id.context_menu_button: {
                startActivity(ContextMenuActivity.newIntent(this));
                break;
            }
            case R.id.programmatic_layout_button: {
                intent = new Intent(this, ProgrammaticLayoutActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.asynctask_button: {
                intent = new Intent(this, AsyncTasksActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.services_button: {
                intent = new Intent(this, ServicesActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.parcelable_button: {
                mButtonsHandlers.gotoParcelable();
                break;
            }
            case R.id.alert_dialogue_button: {
                mButtonsHandlers.showAlertDialog();
                break;
            }
            case R.id.add_buttons_button: {
                mButtonsHandlers.gotoAddButtons();
                break;
            }
            case R.id.one_fragment_button: {
                mButtonsHandlers.gotoOneFragment();
                break;
            }
        }
    }

    private void runYoutubeVideo(String videoLink) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videoLink)));
    }
}