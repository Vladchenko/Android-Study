package com.example.vladislav.androidstudy.jobs;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vladislav.androidstudy.R;

public class WebBrowserActivity extends AppCompatActivity {

    private EditText mWebSiteEditText;
    private Button mGoButton;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_web_browser);

        defineViews();
        setWebView();
        setButtonClickListener();
    }

    void defineViews() {
        mWebSiteEditText = (EditText) findViewById(R.id.web_site_edit_text);
        mGoButton = (Button) findViewById(R.id.open_web_site_button);
        mWebView = (WebView) findViewById(R.id.web_container);
    }

    void setWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                WebBrowserActivity.this.setProgress(progress * 1000);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(WebBrowserActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Sets clicklistener for a "Go" button
     */
    void setButtonClickListener() {
        mGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl(mWebSiteEditText.getText().toString());
            }
        });
    }

}
