package com.example.vladislav.androidstudy.jobs.background_jobs.asynctask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

import java.lang.ref.WeakReference;

/**
 * Async task demo.
 */
public class AsyncTask1Activity extends Activity {

    private DemoAsyncTask asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task1);
        asyncTask = (DemoAsyncTask) getLastNonConfigurationInstance();
        if (asyncTask == null) {
            asyncTask = new DemoAsyncTask();
            asyncTask.execute();
        }
        // передаем в MyTask ссылку на текущее AsyncTask1Activity
        asyncTask.link(this);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AsyncTask1Activity.class);
    }

    public Object onRetainNonConfigurationInstance() {
        // удаляем из DemoAsyncTask ссылку на старое AsyncTask1Activity
        asyncTask.unLink();
        return asyncTask;
    }

    static class DemoAsyncTask extends AsyncTask<String, Void, Integer> {

        private WeakReference<AsyncTask1Activity> mActivityReference;
        private WeakReference<TextView> mAsyncTaskInfoTextView;

        // получаем ссылку на AsyncTask1Activity и textView
        void link(AsyncTask1Activity activity) {
            mActivityReference = new WeakReference<>(activity);
            mAsyncTaskInfoTextView = new WeakReference<>(mActivityReference.get().findViewById(R.id.asynctask1_text_view));
        }

        // обнуляем ссылку
        void unLink() {
            mActivityReference = null;
            mAsyncTaskInfoTextView = null;
        }

        @Override
        protected void onPreExecute() {
            mAsyncTaskInfoTextView.get().setText("Asynctask1 is performing some operation ...");
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
                mAsyncTaskInfoTextView.get().setText("Asynctask1 finished performing its operation.");
                (mActivityReference.get().findViewById(R.id.asynctask1_progress_bar)).setVisibility(TextView.INVISIBLE);
            } else {
                mAsyncTaskInfoTextView.get().setText("Some trouble occurred while asynctask performed its operation.");
            }
        }
    }
}
