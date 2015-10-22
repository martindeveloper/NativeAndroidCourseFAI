package com.martinpernica.androidcourseapplication.News;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.martinpernica.androidcourseapplication.R;

public class NewsDetailActivity extends AppCompatActivity {

    private TextView mNewsDetailHeadline;
    private TextView mNewsDetailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        mNewsDetailHeadline = (TextView)findViewById(R.id.news_detail_headline);
        mNewsDetailText = (TextView)findViewById(R.id.news_detail_text);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            String articleString = extras.getString("ARTICLE");
            this.ShowArticle(articleString);
        }
    }

    public void ShowArticle(String article) {
        mNewsDetailHeadline.setText(article);
    }

}
