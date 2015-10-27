package com.martinpernica.androidcourseapplication.Http.Request;

import com.martinpernica.androidcourseapplication.Http.HttpEndpoint;

public abstract class HttpRequestContainer {
    public abstract HttpEndpoint getEndpoint();
    public abstract void onSuccess(String response);
    public abstract void onError(int httpCode, String response);
}