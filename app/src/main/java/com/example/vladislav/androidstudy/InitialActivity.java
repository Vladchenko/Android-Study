package com.example.vladislav.androidstudy;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vladislav.androidstudy.fragments_activity.FragmentsActivity;

public class InitialActivity extends AppCompatActivity {

    Button button;
    String DEBUG_TAG = "Debug tag";
    OrientationEventListener mOrientationListener;

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
        button = (Button) findViewById(R.id.layouting_button);
        // Making a button to be clickable and click to perform a transfer to a layouting activity.
        // Another way of doing this - make a separate method in this class that invokes another
        // activity and assing it to a onClick() method in a respectful layout's button
        // (say - button is - android:id="@+id/widgets_button",
        // attribute is - android:onClick="gotoWidgetsActivity")
        // gotoWidgetsActivity - the very method invoked.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitialActivity.this, LayoutingActivity.class);
                startActivity(intent);
            }
        });
        button = (Button) findViewById(R.id.return_result_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitialActivity.this, ResultActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        button = (Button) findViewById(R.id.list_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitialActivity.this, ListsViewingActivity.class);
                startActivity(intent);
            }
        });
        mOrientationListener = new OrientationEventListener(this,
                SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                Toast toast = Toast.makeText(
                        getApplicationContext(),
                        "Orientation changed to " + orientation,
                        Toast.LENGTH_SHORT);
                toast.show();
                if (orientation == 90) {
                    setContentView(R.layout.activity_initial);
                }
            }
        };

        // Defining if an orientation checking can be performed or not.
        if (mOrientationListener.canDetectOrientation() == true) {
            Log.v(DEBUG_TAG, "Can detect orientation");
            mOrientationListener.enable();
        } else {
            Log.v(DEBUG_TAG, "Cannot detect orientation");
            mOrientationListener.disable();
        }

        // That's how we reach the resources.
        Log.i("Log message: ", "Application name is:" + getResources().getString(R.string.app_name));

    }

    // This callback fires when a button from ResultActivity returns a result. It's done in a
    // OnClickListener() in a android:id="@+id/go_back_button" button.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // When nothing retrieved, finish the method.
        if (data == null) {
            return;
        }
        String name = data.getStringExtra("name");
        Toast toast = Toast.makeText(
                getApplicationContext(),
                "Received result is: " + name,
                Toast.LENGTH_SHORT);
        toast.show();
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        Toast toast = Toast.makeText(
//                getApplicationContext(),
//                "Configuration changed",
//                Toast.LENGTH_SHORT);
//        toast.show();
//    }

    public void sendEmail(View view) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("message/rfc822");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"vladislav_mail@list.ru"});
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Android intent testing");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This message was sent from an application being " +
                "developed in Android Studio, to check its operating.");
        startActivity(Intent.createChooser(sendIntent, "Choose mail app"));
    }

    // This button is attached to Onclick() method in a button in activity_initial.xml
    // with id - android:id="@+id/widgets_button".
    // Check an attribute - android:onClick="gotoWidgetsActivity".
    // Another way to do this - make a onCLickListener for a button in this class.
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

}
