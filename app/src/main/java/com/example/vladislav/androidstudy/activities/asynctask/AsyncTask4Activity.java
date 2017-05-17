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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AsyncTask4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task4);
        DemoAsyncTask demoAsyncTask = new DemoAsyncTask(this);
        demoAsyncTask.execute("http://techladon.com/wp-content/uploads/2015/02/mortal-kombat-x.jpg");
    }

    class DemoAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        private Context mContext;
        private TextView textView;

        public DemoAsyncTask(Context context) {
            textView = (TextView) findViewById(R.id.asynctask4_progress_text_view);
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
            ((ImageView) findViewById(R.id.asynctask4_image_view)).setImageBitmap(result);
            ((ProgressBar) findViewById(R.id.asynctask4_progress_bar)).setVisibility(TextView.GONE);
        }

        private Bitmap download_Image(String urlString) {

            // Downloading an image with showing a progress.

            byte data[] = new byte[1024];
            long total = 0;
            int count = 0;
            URL url = null;
            int fileSize = -1;
            int response = -1;
            InputStream in = null;
            URLConnection conn = null;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                conn = url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
//            try {
//                httpConn.setRequestMethod("GET");
//            } catch (ProtocolException e) {
//                e.printStackTrace();
//            }
            try {
                httpConn.connect();
                response = httpConn.getResponseCode();
                fileSize = httpConn.getContentLength();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (response == HttpURLConnection.HTTP_OK) {
                try {
                    in = httpConn.getInputStream();
                    while ((count = in.read(data)) != -1) {
                        total += count;
                        bos.write(data, 0, count);
                        publishProgress((int) (total * 100 / fileSize));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return BitmapFactory.decodeByteArray(bos.toByteArray(), 0, bos.size());

        }

    }
}
