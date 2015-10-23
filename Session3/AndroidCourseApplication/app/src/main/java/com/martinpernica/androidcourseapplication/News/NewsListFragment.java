package com.martinpernica.androidcourseapplication.News;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.martinpernica.androidcourseapplication.R;

import java.util.ArrayList;

public class NewsListFragment extends Fragment implements ListView.OnItemClickListener {

    private ListView mNewsListView;
    private ArrayList<INewsListFragmentListener> mListeners;

    public interface INewsListFragmentListener {
        void onNewsItemClick(AdapterView<?> parent, View view, int position, long id);
        void onNewsFragmentCreated(NewsListFragment fragment);
    }

    public NewsListFragment() {
        mListeners = new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof INewsListFragmentListener) {
            mListeners.add((INewsListFragmentListener) context);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_news_list, container, false);

        mNewsListView = (ListView)fragmentView.findViewById(R.id.news_list_view);
        mNewsListView.setOnItemClickListener(this);

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (INewsListFragmentListener listener :
                mListeners) {
            listener.onNewsFragmentCreated(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (INewsListFragmentListener listener :
                mListeners) {
            listener.onNewsItemClick(parent, view, position, id);
        }
    }

    public void setListViewAdapter(BaseAdapter adapter) {
        mNewsListView.setAdapter(adapter);
    }
}
