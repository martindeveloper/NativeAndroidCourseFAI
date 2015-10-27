package com.martinpernica.androidcourseapplication;

import android.content.Context;
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
import com.martinpernica.androidcourseapplication.Http.Request.HttpRequestThread;
import com.martinpernica.androidcourseapplication.Http.Request.HttpRequestContainer;
import com.martinpernica.androidcourseapplication.News.Model.NewsEntity;
import com.martinpernica.androidcourseapplication.News.NewsDetailFragment;
import com.martinpernica.androidcourseapplication.News.NewsListFragment;

import org.json.JSONArray;
import org.json.JSONObject;

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

        mFragmentManager = getSupportFragmentManager();

        mNewsDetailFragment = (NewsDetailFragment) mFragmentManager.findFragmentById(R.id.news_detail_fragment);

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
            mNewsDetailFragment.showArticle(item);
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
            JSONArray newsArray = new JSONArray(response);
            mNewsArray.clear();

            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject jsonobject = newsArray.getJSONObject(i);

                NewsEntity newsEntity = new NewsEntity();
                newsEntity.Title = jsonobject.getString("title");
                newsEntity.Description = jsonobject.getString("description");

                mNewsArray.add(newsEntity);
            }

            mNewsListAdapter = new ArrayAdapter<>(NewsActivity.this, android.R.layout.simple_list_item_1, mNewsArray);

            // To touch views we must be on UI thread
            NewsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fragment.setListViewAdapter(mNewsListAdapter);
                }
            });
        } catch (Exception ex) {
            NewsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast errorToast = Toast.makeText(NewsActivity.this, "Error: Can not download latest news :(", Toast.LENGTH_LONG);
                    errorToast.show();
                }
            });
        }
    }

    @Override
    public void onNewsFragmentCreated(final NewsListFragment fragment) {
        // This method is called also when display is rotated, we do not want download data again
        if (mNewsArray.size() != 0) {
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
                onNewsDownloaded(fragment, response);
            }

            @Override
            public void onError(final int httpCode, String response) {
                NewsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast errorToast = Toast.makeText(NewsActivity.this, String.format("Error: Can not download latest news - API respond code was %d :(", httpCode), Toast.LENGTH_LONG);
                        errorToast.show();
                    }
                });
            }
        });
    }
}
