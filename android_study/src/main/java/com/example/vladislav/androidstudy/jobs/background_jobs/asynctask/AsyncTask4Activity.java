package com.example.vladislav.androidstudy.jobs.background_jobs.asynctask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
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

    private DemoAsyncTask mAsyncTask;
    private static TextView mTextView;
    private Bitmap mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mImage = (Bitmap) savedInstanceState.getParcelable("image");
            ((ImageView) findViewById(R.id.asynctask4_image_view)).setImageBitmap(mImage);
        } else {
            setContentView(R.layout.activity_async_task4);
            mTextView = (TextView) findViewById(R.id.asynctask4_progress_text_view);
            mAsyncTask = (DemoAsyncTask) getLastNonConfigurationInstance();
            if (mAsyncTask == null) {
                mAsyncTask = new DemoAsyncTask(this);
                mAsyncTask.execute("https://images2.alphacoders.com/758/thumb-1920-75892.jpg");
            }
            // передаем в DemoAsyncTask ссылку на текущее AsyncTask4Activity
            mAsyncTask.link(this);
        }
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // The goal is - to not download an image again, but to get it from a bundle.
        // Seems there is no way to do this, using this mechanism, but to save to disk and then restore.
        outState.putParcelable("image", mImage);
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap mImage) {
        this.mImage = mImage;
    }

    static class DemoAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        private Context mContext;
        private AsyncTask4Activity activity;

        public DemoAsyncTask(Context context) {
            mContext = context.getApplicationContext();
        }

        // получаем ссылку на AsyncTask4Activity
        void link(AsyncTask4Activity act) {
            activity = act;
        }

        // обнуляем ссылку
        void unLink() {
            activity = null;
        }

        @Override
        protected void onPreExecute() {
            mTextView.setText("Asynctask4 performs downloading a picture ...");
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return download_Image(params[0]);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mTextView.setText("Downloaded " + values[0].toString() + "%");
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            mTextView.setText("Download complete");
            activity.setImage(result);
            ((ImageView) activity.findViewById(R.id.asynctask4_image_view)).setImageBitmap(result);
            activity.findViewById(R.id.asynctask4_progress_bar).setVisibility(TextView.GONE);
        }

        private Bitmap download_Image(String urlString) {

            // Downloading an mImage with showing a progress.

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
