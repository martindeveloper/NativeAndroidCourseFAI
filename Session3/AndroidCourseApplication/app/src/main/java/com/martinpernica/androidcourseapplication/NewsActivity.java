package com.martinpernica.androidcourseapplication;

import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.martinpernica.androidcourseapplication.News.Model.NewsEntity;
import com.martinpernica.androidcourseapplication.News.NewsDetailFragment;
import com.martinpernica.androidcourseapplication.News.NewsListFragment;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements NewsListFragment.INewsListFragmentListener {

    private ArrayList<NewsEntity> mNewsArray = new ArrayList<>();
    private ArrayAdapter<NewsEntity> mNewsListAdapter;

    private NewsDetailFragment mNewsDetailFragment;

    private FragmentManager mFragmentManager;
    private NewsListFragment mListFragment;
    private NewsDetailFragment mDetailFragment;

    private boolean mIsDetailFragmentPresents = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_layout);

        populateTempData();

        mFragmentManager = getSupportFragmentManager();

        mNewsDetailFragment = (NewsDetailFragment)mFragmentManager.findFragmentById(R.id.news_detail_fragment);

        Resources res = getResources();
        mIsDetailFragmentPresents = res.getBoolean(R.bool.news_has_two_panels);

        if (!mIsDetailFragmentPresents) {
            mListFragment = new NewsListFragment();

            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.news_fragment_container, mListFragment);
            fragmentTransaction.disallowAddToBackStack();
            fragmentTransaction.commit();
        }

        mNewsListAdapter = new ArrayAdapter<>(NewsActivity.this, android.R.layout.simple_list_item_1, mNewsArray);
    }

    private void populateTempData() {
        for (int i = 0; i < 30; i++) {
            NewsEntity entity = new NewsEntity();
            entity.Title = String.format("Item %d", (i + 1));
            mNewsArray.add(entity);
        }
    }

    @Override
    public void onNewsItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsEntity item = mNewsListAdapter.getItem(position);

        if (mIsDetailFragmentPresents) {
            mNewsDetailFragment.showArticle(item);
        }else {
            mDetailFragment = new NewsDetailFragment();
            mDetailFragment.showArticle(item);

            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            fragmentTransaction.replace(R.id.news_fragment_container, mDetailFragment);

            fragmentTransaction.addToBackStack(String.format("%d", position));
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onNewsFragmentCreated(NewsListFragment fragment) {
        fragment.setListViewAdapter(mNewsListAdapter);
    }
}
