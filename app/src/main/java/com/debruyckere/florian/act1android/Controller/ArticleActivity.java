package com.debruyckere.florian.act1android.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.debruyckere.florian.act1android.R;

public class ArticleActivity extends AppCompatActivity {

    private WebView mWebView;
    private String mHtmlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        mWebView =findViewById(R.id.Article_WebViews);

        mHtmlContent = (String)getIntent().getSerializableExtra("ETRA_HTML");
        mWebView.loadData(mHtmlContent,"Text/html; charset=UTF-8", null);
    }
}
