package com.alpha.anna.mywebview.MyFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alpha.anna.mywebview.R;

/**
 * Created by Anna on 18.03.2016.
 */
public class WebPageFragment extends Fragment {
    private WebView webPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.webview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView webPage = (WebView) view.findViewById(R.id.webView1);
        //url of the page
        webPage.loadUrl("http://www.puffgames.com/snake-touch/");
        //enable JavaScript
        WebSettings webSettings = webPage.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //links clicked by user
        webPage.setWebViewClient(new WebViewClient());
        //size of the webpage
        webPage.getSettings().setLoadWithOverviewMode(true);
        webPage.getSettings().setUseWideViewPort(true);
    }
}
