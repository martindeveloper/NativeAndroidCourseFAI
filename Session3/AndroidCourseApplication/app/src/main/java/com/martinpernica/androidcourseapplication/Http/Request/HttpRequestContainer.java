package com.martinpernica.androidcourseapplication.Http.Request;

import android.util.Log;

import com.martinpernica.androidcourseapplication.Http.HttpEndpoint;

public abstract class HttpRequestContainer {
    public abstract HttpEndpoint getEndpoint();
    public abstract void onSuccess(String response);
    public abstract void onError(int httpCode, String response);
    public void onException(Exception ex) {
        // TODO: Handle exception properly
        Log.d(HttpRequestContainer.class.toString(), String.format("Unhandled exception - %s", ex.getMessage()));
    }
}