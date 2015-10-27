package com.martinpernica.androidcourseapplication.Http.Request;

import android.os.AsyncTask;

import com.martinpernica.androidcourseapplication.Http.HttpHelper;
import com.martinpernica.androidcourseapplication.Http.Response.HttpResponseContainer;

import java.util.ArrayList;

public class HttpRequestAsyncTask extends AsyncTask<HttpRequestContainer, Integer, ArrayList<HttpResponseContainer>> {
    protected ArrayList<HttpResponseContainer> doInBackground(HttpRequestContainer... containers) {
        HttpHelper httpHelper = new HttpHelper();
        ArrayList<HttpResponseContainer> responseList = new ArrayList<>();

        for (HttpRequestContainer container : containers) {
            HttpResponseContainer response = httpHelper.sendRequest(container);
            responseList.add(response);
        }

        return responseList;
    }

    protected void onPostExecute(ArrayList<HttpResponseContainer> responseList) {
        for (HttpResponseContainer response : responseList) {
            if (response.IsOK) {
                response.RequestContainer.onSuccess(response.Response);
            } else {
                response.RequestContainer.onError(response.StatusCode, response.Response);
            }
        }
    }
}
