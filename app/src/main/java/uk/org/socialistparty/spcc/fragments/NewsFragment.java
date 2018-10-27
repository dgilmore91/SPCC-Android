package uk.org.socialistparty.spcc.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import uk.org.socialistparty.spcc.R;

public class NewsFragment extends Fragment {

    private WebView webView;

    public NewsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        webView = view.findViewById(R.id.news_fragment_webview);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.loadUrl("https://www.socialistparty.org.uk");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public boolean canWebViewGoBack(){
        return webView.canGoBack();
    }

    public void webViewGoBack(){
        webView.goBack();
    }

}
