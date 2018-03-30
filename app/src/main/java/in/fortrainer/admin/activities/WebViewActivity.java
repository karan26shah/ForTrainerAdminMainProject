package in.fortrainer.admin.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import in.fortrainer.admin.R;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = "WebVIew";
    Context context;
    WebView webView;
    ProgressBar progressBar;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        setUpWebView();
        loadURL();


    }

    private void loadURL() {
 //       linkURL = getActivity().getIntent().getStringExtra(WebTab.EXTRA_URL);
        webView.loadUrl(getString(R.string.base_url)+getString(R.string.api_version) + "/"+"web_views/reports");
    }

    private void setUpWebView() {

        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setAllowFileAccess(true);
        webView.setScrollContainer(false);
        webView.setWebViewClient(new WebViewClient());
    }

    private class WebViewClient extends android.webkit.WebViewClient{

    }
}
