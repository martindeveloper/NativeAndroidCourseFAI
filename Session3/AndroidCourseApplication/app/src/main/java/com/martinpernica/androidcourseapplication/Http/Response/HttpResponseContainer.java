package com.martinpernica.androidcourseapplication.Http.Response;


import com.martinpernica.androidcourseapplication.Http.Request.HttpRequestContainer;

public class HttpResponseContainer {
    public int StatusCode;
    public String Response;
    public boolean IsOK = false;
    public HttpRequestContainer RequestContainer;
}
