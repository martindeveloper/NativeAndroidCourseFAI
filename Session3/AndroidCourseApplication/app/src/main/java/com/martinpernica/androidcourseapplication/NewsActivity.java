package com.martinpernica.androidcourseapplication;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.martinpernica.androidcourseapplication.Http.HttpEndpoint;
import com.martinpernica.androidcourseapplication.Http.Request.HttpRequestAsyncTask;
import com.martinpernica.androidcourseapplication.Http.Request.HttpRequestContainer;
import com.martinpernica.androidcourseapplication.News.Model.NewsEntity;
import com.martinpernica.androidcourseapplication.News.Model.NewsRepository;
import com.martinpernica.androidcourseapplication.News.NewsDetailFragment;
import com.martinpernica.androidcourseapplication.News.NewsListFragment;

import org.json.JSONException;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements NewsListFragment.INewsListFragmentListener {

    private NewsRepository mRepository;
    private ArrayList<NewsEntity> mNewsEntityArrayList = new ArrayList<>();
    private ArrayAdapter<NewsEntity> mNewsListAdapter;

    private FragmentManager mFragmentManager;
    private NewsListFragment mListFragment;
    private NewsDetailFragment mDetailFragment;

    private boolean mIsDetailFragmentPresents = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_layout);

        mRepository = new NewsRepository();

        mFragmentManager = getSupportFragmentManager();

        mDetailFragment = (NewsDetailFragment) mFragmentManager.findFragmentById(R.id.news_detail_fragment);

        Resources res = getResources();
        mIsDetailFragmentPresents = res.getBoolean(R.bool.news_has_two_panels);

        if (!mIsDetailFragmentPresents) {
            mListFragment = new NewsListFragment();

            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.news_fragment_container, mListFragment);
            fragmentTransaction.disallowAddToBackStack();
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onNewsItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsEntity item = mNewsListAdapter.getItem(position);

        if (mIsDetailFragmentPresents) {
            mDetailFragment.showArticle(item);
        } else {
            mDetailFragment = new NewsDetailFragment();
            mDetailFragment.showArticle(item);

            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            fragmentTransaction.replace(R.id.news_fragment_container, mDetailFragment);

            fragmentTransaction.addToBackStack(String.format("%d", position));
            fragmentTransaction.commit();
        }
    }

    private void onNewsDownloaded(final NewsListFragment fragment, String response) {
        try {
            mNewsEntityArrayList = mRepository.parseJsonResponse(response);
            mNewsListAdapter = new ArrayAdapter<>(NewsActivity.this, android.R.layout.simple_list_item_1, mNewsEntityArrayList);

            // To touch views we must be on UI thread
            NewsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fragment.setListViewAdapter(mNewsListAdapter);
                }
            });
        } catch (JSONException ex) {
            NewsActivity.this.showDownloadErrorToast();
        }
    }

    private void showDownloadErrorToast() {
        NewsActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast errorToast = Toast.makeText(NewsActivity.this, "Error: Can not download latest news :(", Toast.LENGTH_LONG);
                errorToast.show();
            }
        });
    }

    @Override
    public void onNewsFragmentCreated(final NewsListFragment fragment) {
        // This method is called also when user is returning from detail fragment to list
        if (mNewsEntityArrayList.size() != 0) {
            fragment.setListViewAdapter(mNewsListAdapter);
            return;
        }

        AsyncTask requestAsyncTask = new HttpRequestAsyncTask().execute(new HttpRequestContainer() {
            @Override
            public HttpEndpoint getEndpoint() {
                HttpEndpoint endpoint = new HttpEndpoint();
                endpoint.Method = HttpEndpoint.METHOD_GET;
                endpoint.Url = "http://private-c6f6b-nativeandroidcoursefai.apiary-mock.com/news";

                return endpoint;
            }

            @Override
            public void onSuccess(String response) {
                NewsActivity.this.onNewsDownloaded(fragment, response);
            }

            @Override
            public void onError(final int httpCode, String response) {
                NewsActivity.this.showDownloadErrorToast();
            }
        });
    }
}
