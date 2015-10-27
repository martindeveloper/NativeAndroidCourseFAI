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
    private BaseAdapter mNewsListAdapter;
    private ArrayList<INewsListFragmentListener> mListeners;
    private boolean mIsCreateEventDispatched = false;

    public interface INewsListFragmentListener {
        void onNewsItemClick(AdapterView<?> parent, View view, int position, long id);
        void onNewsFragmentCreated(NewsListFragment fragment);
    }

    public NewsListFragment() {
        // TODO: Retain state on layout change - one panel vs. two panels
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

        // We already have adapter
        if(mNewsListAdapter != null) {
            setListViewAdapter(mNewsListAdapter);
        }

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mIsCreateEventDispatched) {
            // Listeners were already informed
            return;
        }

        for (INewsListFragmentListener listener : mListeners) {
            listener.onNewsFragmentCreated(this);
        }

        mIsCreateEventDispatched = true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (INewsListFragmentListener listener : mListeners) {
            listener.onNewsItemClick(parent, view, position, id);
        }
    }

    public void setListViewAdapter(BaseAdapter adapter) {
        mNewsListAdapter = adapter;
        mNewsListView.setAdapter(mNewsListAdapter);
    }
}
