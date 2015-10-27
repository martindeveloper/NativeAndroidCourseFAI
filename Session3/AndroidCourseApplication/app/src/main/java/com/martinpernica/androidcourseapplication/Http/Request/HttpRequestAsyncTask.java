package com.martinpernica.androidcourseapplication.Http.Request;

import android.os.AsyncTask;

import com.martinpernica.androidcourseapplication.Http.HttpHelper;

public class HttpRequestAsyncTask extends AsyncTask<HttpRequestContainer, Integer, Boolean> {
    protected Boolean doInBackground(HttpRequestContainer... containers) {
        HttpHelper httpHelper = new HttpHelper();

        for (HttpRequestContainer container : containers) {
            httpHelper.sendRequest(container);
        }

        return true;
    }
}
