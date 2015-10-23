package com.martinpernica.androidcourseapplication.News;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.martinpernica.androidcourseapplication.R;

public class NewsDetailFragment extends Fragment {

    private TextView mNewsDetailHeadline;
    private TextView mNewsDetailText;

    private String mPendingArticle;

    public NewsDetailFragment() {}

    public void showArticle(String article) {
        if (mNewsDetailHeadline != null) {
            mNewsDetailHeadline.setText(article);
        }else {
            mPendingArticle = article;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_news_detail, container, false);

        mNewsDetailHeadline = (TextView)fragmentView.findViewById(R.id.news_detail_headline);
        mNewsDetailText = (TextView)fragmentView.findViewById(R.id.news_detail_text);

        if (mPendingArticle != null) {
            showArticle(mPendingArticle);
            mPendingArticle = null;
        }

        return fragmentView;
    }


}
