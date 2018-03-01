package com.example.vladislav.androidstudy.simple_jobs;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.activities.ResultActivity;

/**
 * Fragment displaying some android widgets
 */
public class WidgetsFragment extends Fragment {

    private Activity mActivity;
    private Context mContext;
    private ProgressBar progressBar;
    private RadioButton radioButton;
    private ToggleButton toggleButton;
    private CheckBox checkBox;
    private TextView textView;
    private int count = 1;

    public static final String FRAGMENT_TAG = ImageFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_widgets, container, false);
    }

    private void logicFromActivity() {

        mActivity = getActivity();
        mContext = mActivity.getApplicationContext();
        final ApplicationInfo applicationInfo = mContext.getApplicationInfo();

        Toast toast = Toast.makeText(mContext,
                "This is the toast message", Toast.LENGTH_SHORT);
        // An alternative way of setting a text message to be shown.
//        toast.setText("!");
        toast.show();

        progressBar = (ProgressBar) mActivity.findViewById(R.id.progressBar);

        Button button = (Button) mActivity.findViewById(R.id.button);
        // This is the way a button click is processed.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(mContext, "Process name is: "
                        + applicationInfo.processName, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // OnFocusListener doesn't work for TextView and for some other components. But it should
        // work for textEdit.
        textView = (TextView) mActivity.findViewById(R.id.helloworld_text_view);
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast toast = Toast.makeText(mContext,
                        R.id.helloworld_text_view + " has been touched", Toast.LENGTH_SHORT);
                toast.show();
                return true;
            }
        });

        radioButton = (RadioButton) mActivity.findViewById(R.id.radioButton);
        // Clicking this radiobutton calls other mActivity (Widgets2Activity).
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This way we call another mActivity.
//                Intent intent = new Intent(WidgetsActivity.this, Widgets2Activity.class);
//                startActivity(intent);
            }
        });

        checkBox = (CheckBox) mActivity.findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ResultActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        new MyTask().execute();

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
