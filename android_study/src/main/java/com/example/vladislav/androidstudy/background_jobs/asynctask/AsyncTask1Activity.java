package com.example.vladislav.androidstudy.background_jobs.asynctask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

public class AsyncTask1Activity extends Activity {

    private DemoAsyncTask asyncTask;
    private static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task1);
        textView = (TextView) findViewById(R.id.asynctask1_text_view);
        asyncTask = (DemoAsyncTask) getLastNonConfigurationInstance();
        if (asyncTask == null) {
            asyncTask = new DemoAsyncTask(this);
            asyncTask.execute();
        }
        // передаем в MyTask ссылку на текущее AsyncTask1Activity
        asyncTask.link(this);
    }

    public Object onRetainNonConfigurationInstance() {
        // удаляем из DemoAsyncTask ссылку на старое AsyncTask1Activity
        asyncTask.unLink();
        return asyncTask;
    }

    static class DemoAsyncTask extends AsyncTask<String, Void, Integer> {

        private Context mContext;

        private AsyncTask1Activity activity;

        public DemoAsyncTask(Context context) {
            mContext = context.getApplicationContext();
        }

        // получаем ссылку на AsyncTask1Activity
        void link(AsyncTask1Activity act) {
            activity = act;
        }

        // обнуляем ссылку
        void unLink() {
            activity = null;
        }

        @Override
        protected void onPreExecute() {

            textView.setText("Asynctask1 performs some operation ...");
        }

        @Override
        protected Integer doInBackground(String... params) {
            // Imitating some work.
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return -1;
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer != -1) {
                textView.setText("Asynctask1 finished performing its operation.");
                ((ProgressBar) activity.findViewById(R.id.asynctask1_progress_bar)).setVisibility(TextView.INVISIBLE);
            } else {
                textView.setText("Some trouble occurred while asynctask performed its operation.");
            }
        }
    }
}
