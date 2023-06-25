package com.example.vladislav.androidstudy.kotlin

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.vladislav.androidstudy.R

class KotlinStudyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_study_activity)

        val mainClass = Main()   // Creates instance
        mainClass.main(this)         // Invokes method main
    }

    fun checkForPermissions() {
        // Here, thisActivity is the current activity
        if (androidx.core.content.ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                // This part I didn't implement,because for my case it isn't needed
                Log.i(TAG, "Unexpected flow")
            } else {
                // No explanation needed; request the permission
//                ActivityCompat.requestPermissions(
//                    this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                    MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE
//                )

                // MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission is already granted, call the function that does what you need
//            onFileWritePermissionGranted()
        }
    }

    companion object {
        private const val TAG = "KotlinStudyActivity"
    }
}
