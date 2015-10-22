package com.martinpernica.androidcourseapplication.News;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.martinpernica.androidcourseapplication.R;

public class NewsDetailFragment extends Fragment {

    private TextView mNewsDetailHeadline;
    private TextView mNewsDetailText;

    public NewsDetailFragment() {
        // Required empty public constructor
    }

    public void ShowArticle(String article) {
        mNewsDetailHeadline.setText(article);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_news_detail, container, false);

        mNewsDetailHeadline = (TextView)fragmentView.findViewById(R.id.news_detail_headline);
        mNewsDetailText = (TextView)fragmentView.findViewById(R.id.news_detail_text);

        return fragmentView;
    }


}
