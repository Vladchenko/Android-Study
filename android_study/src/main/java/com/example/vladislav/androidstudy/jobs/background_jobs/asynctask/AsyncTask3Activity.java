package com.example.vladislav.androidstudy.jobs.background_jobs.asynctask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTask3Activity extends AppCompatActivity {

    private DemoAsyncTask asyncTask;
    private static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task3);
        textView = (TextView) findViewById(R.id.asynctask3_progress_text_view);
        asyncTask = (DemoAsyncTask) getLastNonConfigurationInstance();
        if (asyncTask == null) {
            asyncTask = new DemoAsyncTask(this);
            asyncTask.execute("http://techladon.com/wp-content/uploads/2015/02/mortal-kombat-x.jpg");
        }
        // передаем в DemoAsyncTask ссылку на текущее AsyncTask3Activity
        asyncTask.link(this);
    }

    static class DemoAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        private Context mContext;
        private AsyncTask3Activity activity;

        public DemoAsyncTask(Context context) {
            mContext = context.getApplicationContext();
        }

        // получаем ссылку на AsyncTask2Activity
        void link(AsyncTask3Activity act) {
            activity = act;
        }

        // обнуляем ссылку
        void unLink() {
            activity = null;
        }

        @Override
        protected void onPreExecute() {
            textView.setText("Asynctask3 performs downloading a picture ...");
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return download_Image(params[0]);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            textView.setText("Downloaded " + values[0].toString() + "%");
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            textView.setText("Download complete");
            ((ImageView) activity.findViewById(R.id.asynctask3_image_view)).setImageBitmap(result);
            ((ProgressBar) activity.findViewById(R.id.asynctask3_progress_bar)).setVisibility(TextView.GONE);
        }

        // Downloading an image right away.
        private Bitmap download_Image(String urlString) {

            Bitmap bmp = null;
            try {
                URL ulrn = new URL(urlString);
                HttpURLConnection con = (HttpURLConnection) ulrn.openConnection();
                InputStream is = con.getInputStream();
                bmp = BitmapFactory.decodeStream(is);
                if (null != bmp)
                    return bmp;
            } catch (Exception e) {
                // TODO Log it.
            }
            return bmp;

        }

    }
}
