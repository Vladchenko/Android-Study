package com.example.vladislav.androidstudy.activities;

import static com.example.vladislav.androidstudy.Utils.showToast;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

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
public class InitialActivity extends AppCompatActivity {

    public static final String ACTIVITY_RESULT_ID = "result";
    private final String mLogTag = getClass().getCanonicalName();
    private final String mYouTubeVideoID = "Fee5vbFLYM4";
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

    /**
     * Start this activity
     * @param context to start activity
     * @return  intent that starts this activity
     */
    public static Intent newIntent(@NonNull Context context, String id, String value) {
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
        super.onActivityResult(requestCode, resultCode, data);
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
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
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
        Button button = findViewById(R.id.layouting_button);
        // Making a button to be clickable and click to perform a transfer to a layouting mActivity.
        // Another way of doing this - make a separate method in this class that invokes another
        // mActivity and assign it to a onClick() method in a respectful mLayout's button
        // (say - button is - android:id="@+id/widgets_button",
        // attribute is - android:onClick="gotoWidgetsActivity")
        // gotoWidgetsActivity - the very method invoked.
        button.setOnClickListener(v -> startActivity(LayoutingActivity.newIntent(this)));
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:123"));
//                startActivity(intent);
//            }
//        });
        button = findViewById(R.id.return_result_button);
        button.setOnClickListener(v -> startActivityForResult(ResultActivity.newIntent(InitialActivity.this), 1));
        button = findViewById(R.id.list_button);
        button.setOnClickListener(v -> startActivity(ListsViewActivity.newIntent(this)));
        button = findViewById(R.id.youtube_runner_button);
        button.setOnClickListener(v -> {
                // It seems that emulator won't run video, one has to use a mobile device for it.
                runYoutubeVideo(mYouTubeVideoID);
        });
//
//        (findViewById(R.id.banks_details_button)).setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(new Intent(InitialActivity.this, BanksDetailsActivity.class));
//                    }
//                }
//        );
        button = findViewById(R.id.rotation_button);
        // Making a button to be clickable and click to perform a transfer to a layouting mActivity.
        // Another way of doing this - make a separate method in this class that invokes another
        // mActivity and assign it to a onClick() method in a respectful mLayout's button
        // (say - button is - android:id="@+id/widgets_button",
        // attribute is - android:onClick="gotoWidgetsActivity")
        // gotoWidgetsActivity - the very method invoked.
        button.setOnClickListener(v ->
                startActivity(RotationActivity.newIntent(InitialActivity.this,
                new Planet(10, 15, 20))));
        // One might also use android:onClick="gotoWidgetsActivity" in activity_initial.xml
        // to attach a handler, but v ->  is not a good approach.
        button = findViewById(R.id.alert_dialogue_button);
        button.setOnClickListener(v -> mButtonsHandlers.showAlertDialog());
        button = findViewById(R.id.intent_button);
        button.setOnClickListener(v -> startActivity(IntentsActivity.newIntent(this)));
        button = findViewById(R.id.call_button);
        button.setOnClickListener(v ->  mButtonsHandlers.makePhoneCall());
        button = findViewById(R.id.context_menu_button);
        button.setOnClickListener(v -> startActivity(ContextMenuActivity.newIntent(this)));
        button = findViewById(R.id.programmatic_layout_button);
        button.setOnClickListener(v -> startActivity(new Intent(this, ProgrammaticLayoutActivity.class)));
        button = findViewById(R.id.asynctask_button);
        button.setOnClickListener(v -> startActivity(new Intent(this, AsyncTasksActivity.class)));
        button = findViewById(R.id.services_button);
        button.setOnClickListener(v -> startActivity(new Intent(this, ServicesActivity.class)));
        button = findViewById(R.id.parcelable_button);
        button.setOnClickListener(v -> mButtonsHandlers.gotoParcelable());
        button = findViewById(R.id.send_email_button);
        button.setOnClickListener(v -> mButtonsHandlers.sendEmail());
        button = findViewById(R.id.image_button);
        button.setOnClickListener(v -> mButtonsHandlers.gotoImageActivity());
        button = findViewById(R.id.fragments_button);
        button.setOnClickListener(v -> mButtonsHandlers.gotoFragmentsActivity());
        button = findViewById(R.id.menu_button);
        button.setOnClickListener(v -> mButtonsHandlers.gotoMenuActivity());
        button = findViewById(R.id.widgets_button);
        button.setOnClickListener(v -> mButtonsHandlers.gotoWidgetsActivity());
        button = findViewById(R.id.widgets2_button);
        button.setOnClickListener(v -> mButtonsHandlers.gotoWidgets2Activity());
        button = findViewById(R.id.aligning_button);
        button.setOnClickListener(v -> mButtonsHandlers.gotoAligningActivity());
        button = findViewById(R.id.add_buttons_button);
        button.setOnClickListener(v -> mButtonsHandlers.gotoAddButtons());
        button = findViewById(R.id.one_fragment_button);
        button.setOnClickListener(v -> mButtonsHandlers.gotoOneFragment());

    }

//    public void gotoServicesActivity(View view) {
//        Intent intent = new Intent(InitialActivity.this, ServicesDemo1Activity.class);
//        intent.putExtra("isIntendedService", ((CheckBox) findViewById(R.id.intent_service_checkbox)).isChecked());
//        startActivity(intent);
//    }

    private void runYoutubeVideo(String videoLink) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoLink));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            showToast(this, "Cannot run Youtube app. Probably it is not installed on a device.");
        }
    }
}