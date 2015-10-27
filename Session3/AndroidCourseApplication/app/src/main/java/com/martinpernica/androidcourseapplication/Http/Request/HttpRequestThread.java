package com.martinpernica.androidcourseapplication.Http.Request;

import com.martinpernica.androidcourseapplication.Http.HttpHelper;

public class HttpRequestThread {
    private Thread mRequestThread;
    private boolean mIsWorking = false;

    public HttpRequestThread() {
    }

    public void killRequest() {
        if (mRequestThread != null && mIsWorking) {
            mRequestThread.interrupt();
        }
    }

    public void startRequest(final HttpRequestContainer container) {
        if(mIsWorking) {
            // Do not send next request if one is in queue
            return;
        }

        mIsWorking = true;

        mRequestThread = new Thread() {
            public void run() {
                HttpHelper httpHelper = new HttpHelper();
                httpHelper.sendRequest(container);
            }
        };

        mRequestThread.start();
    }
}
