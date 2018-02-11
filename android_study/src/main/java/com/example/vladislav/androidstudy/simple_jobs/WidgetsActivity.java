package com.example.vladislav.androidstudy.simple_jobs;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.activities.ResultActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Activity demonstrating a widgets present in android
 */
public class WidgetsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RadioButton radioButton;
    private ToggleButton toggleButton;
    private CheckBox checkBox;
    private TextView textView;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widgets); // One may put activity_widgets2 instead.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        } else {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Actionbar is absent");
        }

        final ApplicationInfo applicationInfo = getApplicationContext().getApplicationInfo();

        Toast toast = Toast.makeText(getApplicationContext(),
                "This is the toast message", Toast.LENGTH_SHORT);
        // An alternative way of setting a text message to be shown.
//        toast.setText("!");
        toast.show();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Button button = (Button) findViewById(R.id.button);
        // This is the way a button click is processed.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Process name is: " + applicationInfo.processName, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // OnFocusListener doesn't work for TextView and for some other components. But it should
        // work for textEdit.
        textView = (TextView) findViewById(R.id.helloworld_text_view);
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        R.id.helloworld_text_view + " has been touched", Toast.LENGTH_SHORT);
                toast.show();
                return true;
            }
        });

        radioButton = (RadioButton) findViewById(R.id.radioButton);
        // Clicking this radiobutton calls other mActivity (Widgets2Activity).
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This way we call another mActivity.
                Intent intent = new Intent(WidgetsActivity.this, Widgets2Activity.class);
                startActivity(intent);
            }
        });

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WidgetsActivity.this, ResultActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        new MyTask().execute();

    }

    // When a result is back from other mActivity.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String name = data.getStringExtra("name");
        if (resultCode == RESULT_OK) {
            textView.setText("Your name is " + name);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                // Or one could use NavUtils.navigateUpFromSameTask(this);, but in this case,
                // one needs to define a parent activity -
                // https://developer.android.com/training/implementing-navigation/ancestral.html#SpecifyParent
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // This class runs some task in a separate thread.
    // What it actually does - sleeps for 3 seconds and then removes a progress bar from a view.
    class MyTask extends AsyncTask<Integer, Integer, String> {
        // This method runs before execution of a task. Some preparation tasks done here.
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        // This method initiates a running of a task in a background. One cannot refer to a view in it.
        @Override
        protected String doInBackground(Integer... params) {
            for (; count <= 3; count++) {
                try {
                    Thread.sleep(1000);
                    publishProgress(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Task Completed.";
        }

        // This method runs after execution of a task. Some finalizing tasks done here.
        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
        }

        // While task is done, this method fires
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }
    }

}
