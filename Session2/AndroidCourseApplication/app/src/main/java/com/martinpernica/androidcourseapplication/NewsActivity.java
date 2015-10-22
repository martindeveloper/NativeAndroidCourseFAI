package com.martinpernica.androidcourseapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.martinpernica.androidcourseapplication.News.NewsDetailActivity;
import com.martinpernica.androidcourseapplication.News.NewsDetailFragment;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<String> newsArray = new ArrayList<>();
    private ArrayAdapter<String> mNewsListAdapter;

    private ListView mNewsListView;
    private NewsDetailFragment mNewsDetailFragment;

    private boolean isDetailFragmentPresents = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_layout);

        mNewsListView = (ListView)findViewById(R.id.news_list_view);

        mNewsDetailFragment = (NewsDetailFragment)getFragmentManager().findFragmentById(R.id.news_detail);

        isDetailFragmentPresents = (mNewsDetailFragment != null);

        // TODO: Remove this temp data
        for (int i = 0; i < 30; i++) {
            newsArray.add(String.format("Item %d", (i + 1)));
        }

        // Set on item click listener to this class
        mNewsListView.setOnItemClickListener(NewsActivity.this);

        // Fill data to list view
        fillListView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = mNewsListAdapter.getItem(position);

        if (isDetailFragmentPresents) {
            mNewsDetailFragment.ShowArticle(item);
        }else {
            Intent newsDetailActivityIntent = new Intent(NewsActivity.this, NewsDetailActivity.class);
            newsDetailActivityIntent.putExtra("ARTICLE", item);

            startActivity(newsDetailActivityIntent);
        }
    }

    private void fillListView() {
        mNewsListAdapter = new ArrayAdapter<String>(NewsActivity.this, android.R.layout.simple_list_item_1, this.newsArray);
        mNewsListView.setAdapter(mNewsListAdapter);
    }
}
