package com.example.vladislav.androidstudy.jobs.simple_jobs;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
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

    public static final String FRAGMENT_TAG = ImageFragment.class.getSimpleName();

    private Activity mActivity;
    private Context mContext;
    private ProgressBar progressBar;
    private RadioButton radioButton;
    private ToggleButton mToggleButton;
    private CheckBox checkBox;
    private TextView textView;
    private int count = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_widgets, container, false);
        mToggleButton = (ToggleButton)view.findViewById(R.id.toggle_button);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        animateButton();
        logicFromActivity();
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

        progressBar = mActivity.findViewById(R.id.progressBar);

        Button button = mActivity.findViewById(R.id.button);
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
        textView = mActivity.findViewById(R.id.helloworld_text_view);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(mContext,
                        R.id.helloworld_text_view + " has been touched", Toast.LENGTH_SHORT);
                toast.show();
            }

//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Toast toast = Toast.makeText(mContext,
//                        R.id.helloworld_text_view + " has been touched", Toast.LENGTH_SHORT);
//                toast.show();
//                return true;
//            }
        });

        radioButton = (RadioButton) mActivity.findViewById(R.id.radioButton);
        // Clicking this radiobutton calls other mActivity (Widgets2Activity).
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This way we call another activity.
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

    private void animateButton() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = mToggleButton.getWidth() / 2;
            int cy = mToggleButton.getHeight() / 2;
            float radius = mToggleButton.getWidth();
            Animator anim = ViewAnimationUtils
                    .createCircularReveal(mToggleButton, cx, cy, radius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mToggleButton.setVisibility(View.INVISIBLE);
                }
            });
            anim.start();
        } else {
            mToggleButton.setVisibility(View.VISIBLE);
        }
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
