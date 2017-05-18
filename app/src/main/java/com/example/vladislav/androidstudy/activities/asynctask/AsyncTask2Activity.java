package com.example.vladislav.androidstudy.activities.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTask2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task2);
        DemoAsyncTask demoAsyncTask = new DemoAsyncTask(this);
        demoAsyncTask.execute("http://developer.android.com");
    }

    class DemoAsyncTask extends AsyncTask<String, Integer, String> {

        private Context mContext;
        private TextView textView;
        private int progressCount;

        public DemoAsyncTask(Context context) {
            mContext = context.getApplicationContext();
            textView = (TextView) findViewById(R.id.asynctask2_text_view);
        }

        @Override
        protected void onPreExecute() {
            textView.setText("Asynctask2 performs some operation ...");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            ((TextView)findViewById(R.id.asynctask2_progress_text_view))
                .setText("Downloaded " + values[0] * 10 + " %");
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            String string = "No response";
                try {
                    connection = (HttpURLConnection) new URL(params[0])
                            .openConnection();
                    string = params[0] + " - " + connection.getResponseMessage();
                    // Faking the progress by incrementing a progress value and wait 500ms.
                    for (int i=0; i < 10; i++) {
                        publishProgress(++progressCount);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    string = "Error getting a response message from " + params[0];
                    connection.disconnect();
                }
            return string;
        }

        @Override
        protected void onPostExecute(String string) {
            textView.setText(string);
            ((ProgressBar)findViewById(R.id.asynctask2_progress_bar)).setVisibility(TextView.GONE);
        }
    }
}