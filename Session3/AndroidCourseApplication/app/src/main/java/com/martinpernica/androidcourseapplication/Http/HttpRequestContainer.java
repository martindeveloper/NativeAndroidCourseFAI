package com.martinpernica.androidcourseapplication.Http;

import android.util.Log;

public abstract class HttpRequestContainer {
    public abstract String getHttpMethod();
    public abstract String getEndpointUrl();
    public abstract void onSuccess(String response);
    public abstract void onError(int httpCode, String response);
    public void onException(Exception ex) {
        // TODO: Handle exception properly
        Log.d(HttpRequestContainer.class.toString(), String.format("Unhandled exception - %s", ex.getMessage()));
    }
}