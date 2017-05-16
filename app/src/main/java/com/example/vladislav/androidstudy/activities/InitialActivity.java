package com.example.vladislav.androidstudy.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.activities.asynctask.AsyncTasksActivity;
import com.example.vladislav.androidstudy.activities.fragments_activity.FragmentsActivity;
import com.example.vladislav.androidstudy.activities.fragments_activity.FragmentsDynamicActivity;
import com.example.vladislav.androidstudy.intentstudy.IntentsActivity;

public class InitialActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ACTIVITY_RESULT_ID = "result";
    private final String mLogTag = getClass().getCanonicalName();
    private String mYouTubeVideoID = "Fee5vbFLYM4";
    private Button mButton;

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

        // That's how we reach the resources.
//        Log.i("Log message: ", "Application name is:" + getResources().getString(R.string.app_name));

        Log.i(mLogTag, "onCreate");

    }

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

    // This callback fires when a mButton from ResultActivity returns a result. It's done in a
    // OnClickListener() in a android:id="@+id/go_back_button" mButton.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // When nothing retrieved, finish the method.
        if (data == null) {
            return;
        }
        String text = data.getStringExtra(InitialActivity.ACTIVITY_RESULT_ID);
        Toast toast = Toast.makeText(
                getApplicationContext(),
                "Received result is: " + text,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
        // Don't forget about next row in a manifest.xml
        // android:configChanges="orientation|keyboardHidden
    }

    private void setButtonsClicks() {
        mButton = (Button) findViewById(R.id.layouting_button);
        // Making a mButton to be clickable and click to perform a transfer to a layouting activity.
        // Another way of doing this - make a separate method in this class that invokes another
        // activity and assign it to a onClick() method in a respectful layout's mButton
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
        mButton = (Button) findViewById(R.id.return_result_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitialActivity.this, ResultActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        mButton = (Button) findViewById(R.id.list_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitialActivity.this, ListsViewingActivity.class);
                startActivity(intent);
            }
        });
//        mButton = (Button) findViewById(R.id.youtube_runner_button);
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                runYoutube(mYouTubeVideoID);
//            }
//        });
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
        // Making a mButton to be clickable and click to perform a transfer to a layouting activity.
        // Another way of doing this - make a separate method in this class that invokes another
        // activity and assing it to a onClick() method in a respectful layout's mButton
        // (say - mButton is - android:id="@+id/widgets_button",
        // attribute is - android:onClick="gotoWidgetsActivity")
        // gotoWidgetsActivity - the very method invoked.
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitialActivity.this, RotationActivity.class);
                startActivity(intent);
            }
        });
        mButton = (Button) findViewById(R.id.alert_dialogue_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InitialActivity.this);
                builder.setTitle("Alert Dialogue")
                        .setItems(new CharSequence[]{"case 1", "case2"}, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                Toast.makeText(InitialActivity.this,
                                        Integer.toString(which + 1), Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.create().show();
            }
        });
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
    }

    public void sendEmail(View view) {
        // An implicit intent.
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("message/rfc822");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"vladislav_mail@list.ru"});
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Android intent testing");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This message was sent from an application being " +
                "developed in Android Studio, to check its operating.");
        // Show a chooser that provides a list of an apps that can handle this intent.
        startActivity(Intent.createChooser(sendIntent, "Choose mail app"));

        // Verify that the intent will resolve to an activity
//        if (sendIntent.resolveActivity(getPackageManager()) != null) {
//            startActivity(sendIntent);
//        }

        Intent chooser = Intent.createChooser(sendIntent, "Choose mail app");
//         Verify that the intent will resolve to an activity
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }

    }

    public void runYoutube(String video_id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video_id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + video_id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    //    This mButton is attached to Onclick() method in a mButton in activity_initial.xml
//    with id - android:id="@+id/widgets_button".
//    Check an attribute - android:onClick="gotoWidgetsActivity".
//    Another way to do this - make a onCLickListener for a mButton in this class.
//    Implementing a listener is correct programming practice.
    public void gotoWidgetsActivity(View view) {
        Intent intent = new Intent(InitialActivity.this, WidgetsActivity.class);
        startActivity(intent);
    }

    public void gotoWidgets2Activity(View view) {
        Intent intent = new Intent(InitialActivity.this, Widgets2Activity.class);
        startActivity(intent);
    }

    public void gotoAligningActivity(View view) {
        Intent intent = new Intent(InitialActivity.this, AligningActivity.class);
        startActivity(intent);
    }

    public void gotoMenuActivity(View view) {
        Intent intent = new Intent(InitialActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void gotoFragmentsActivity(View view) {
        Intent intent = new Intent(InitialActivity.this, FragmentsActivity.class);
        startActivity(intent);
    }

    public void gotoFragmentsDynamicActivity(View view) {
        Intent intent = new Intent(InitialActivity.this, FragmentsDynamicActivity.class);
        startActivity(intent);
    }

    public void gotoImageActivity(View view) {
        Intent intent = new Intent(InitialActivity.this, ImageActivity.class);
        startActivity(intent);
    }

    public void gotoServicesActivity(View view) {
        Intent intent = new Intent(InitialActivity.this, ServicesActivity.class);
        intent.putExtra("isIntendedService", ((CheckBox) findViewById(R.id.intent_service_checkbox)).isChecked());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.intent_button: {
                intent = new Intent(this, IntentsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.call_button: {
                int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            1
                    );
                } else {
                    startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse(
                            "tel: 8 927 641 34 83")));
                }
                break;
            }
            case R.id.context_menu_button: {
                intent = new Intent(this, ContextMenuActivity.class);
                startActivity(intent);
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
        }
    }
}