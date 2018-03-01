package com.example.vladislav.androidstudy.logic;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.vladislav.androidstudy.activities.MenuActivity;
import com.example.vladislav.androidstudy.activities.ParcelableActivity;
import com.example.vladislav.androidstudy.activities.dynamic_layout.AddButtonsActivity;
import com.example.vladislav.androidstudy.beans.Planet;
import com.example.vladislav.androidstudy.fragments.fragments_activity.DynamicFragmentsActivity;
import com.example.vladislav.androidstudy.fragments.fragments_activity.FragmentsActivity;
import com.example.vladislav.androidstudy.fragments.fragments_activity.OneFragmentActivity;
import com.example.vladislav.androidstudy.simple_jobs.AligningActivity;

/**
 * Keeps methods that pass to a respective activities.
 * Created by Влад on 30.07.2017.
 */

public class ButtonsHandlers {

    // An activity to leave from to another activity
    private Activity mInitialActivity;

    public ButtonsHandlers(Activity activity) {
        mInitialActivity = activity;
    }

    public void gotoWidgetsActivity() {
//        Intent intent = new Intent(mInitialActivity, WidgetsActivity.class);
//        mInitialActivity.startActivity(intent);
    }

    public void gotoWidgets2Activity() {
//        Intent intent = new Intent(mInitialActivity, Widgets2Activity.class);
//        mInitialActivity.startActivity(intent);
    }

    public void gotoAligningActivity() {
        Intent intent = new Intent(mInitialActivity, AligningActivity.class);
        mInitialActivity.startActivity(intent);
    }

    public void gotoMenuActivity() {
        Intent intent = new Intent(mInitialActivity, MenuActivity.class);
        mInitialActivity.startActivity(intent);
    }

    public void gotoFragmentsActivity() {
        Intent intent = new Intent(mInitialActivity, FragmentsActivity.class);
        mInitialActivity.startActivity(intent);
    }

    public void gotoFragmentsDynamicActivity() {
        Intent intent = new Intent(mInitialActivity, DynamicFragmentsActivity.class);
        mInitialActivity.startActivity(intent);
    }

    public void gotoImageActivity() {
//        Intent intent = new Intent(mInitialActivity, ImageActivity.class);
//        mInitialActivity.startActivity(intent);
    }

    public void sendEmail() {
        // An implicit intent.
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("message/rfc822");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"vladislav_mail@list.ru"});
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Android intent testing");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This message was sent from an application being " +
                "developed in Android Studio, to check its operating.");
        // Show a chooser that provides a list of an apps that can handle this intent.
        mInitialActivity.startActivity(Intent.createChooser(sendIntent, "Choose mail app"));

        // Verify that the intent will resolve to an activity
//        if (sendIntent.resolveActivity(getPackageManager()) != null) {
//            startActivity(sendIntent);
//        }

        Intent chooser = Intent.createChooser(sendIntent, "Choose mail app");
//         Verify that the intent will resolve to an activity
        if (sendIntent.resolveActivity(mInitialActivity.getPackageManager()) != null) {
            mInitialActivity.startActivity(chooser);
        }

    }

    public void runYoutube(String video_id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video_id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + video_id));
        try {
            mInitialActivity.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            mInitialActivity.startActivity(webIntent);
        }
    }

    public void makePhoneCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                mInitialActivity, Manifest.permission.CALL_PHONE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    mInitialActivity,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1
            );
        } else {
            mInitialActivity.startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse(
                    "tel: 8 927 641 34 83")));
        }
    }

    public void gotoParcelable() {
        Intent intent = new Intent(mInitialActivity, ParcelableActivity.class);
        // To send data to other activity, one can do this using Parcelable interface.
        // Parcel is put to intent and in other activity is read from.
        intent.putExtra("Planet", new Planet(12000,500000,600000));
        mInitialActivity.startActivity(intent);
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mInitialActivity);
        builder.setTitle("Alert Dialogue")
                .setItems(new CharSequence[]{"case 1", "case2"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        Toast.makeText(mInitialActivity,
                                Integer.toString(which + 1), Toast.LENGTH_SHORT).show();
                    }
                });
        builder.create().show();
    }

    public void gotoAddButtons() {
        Intent intent = new Intent(mInitialActivity, AddButtonsActivity.class);
        mInitialActivity.startActivity(intent);
    }

    public void gotoOneFragment() {
        Intent intent = new Intent(mInitialActivity, OneFragmentActivity.class);
        mInitialActivity.startActivity(intent);
    }
}
