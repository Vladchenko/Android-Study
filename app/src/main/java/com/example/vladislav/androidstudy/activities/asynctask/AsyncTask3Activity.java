package com.example.vladislav.androidstudy.activities.asynctask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTask3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task3);
        DemoAsyncTask demoAsyncTask = new DemoAsyncTask(this);
        demoAsyncTask.execute("http://techladon.com/wp-content/uploads/2015/02/mortal-kombat-x.jpg");
    }

    class DemoAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        private Context mContext;
        private TextView textView;

        public DemoAsyncTask(Context context) {
            textView = (TextView) findViewById(R.id.asynctask3_progress_text_view);
            mContext = context.getApplicationContext();
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
            ((ImageView) findViewById(R.id.asynctask3_image_view)).setImageBitmap(result);
            ((ProgressBar) findViewById(R.id.asynctask3_progress_bar)).setVisibility(TextView.GONE);
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
