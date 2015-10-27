package com.martinpernica.androidcourseapplication.Http.Request;

import android.content.Context;
import android.os.Handler;

import com.martinpernica.androidcourseapplication.Http.HttpHelper;
import com.martinpernica.androidcourseapplication.Http.Response.HttpResponseContainer;

public class HttpRequestThread {
    private Thread mRequestThread;
    private Handler mMainLooperHandler;
    private boolean mIsWorking = false;

    public HttpRequestThread(Context context) {
        mMainLooperHandler = new Handler(context.getMainLooper());
    }

    public void killRequest() {
        if (mRequestThread != null && mIsWorking) {
            mRequestThread.interrupt();
            mIsWorking = false;
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
                final HttpResponseContainer responseContainer = httpHelper.sendRequest(container);

                mMainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (responseContainer.IsOK) {
                            responseContainer.RequestContainer.onSuccess(responseContainer.Response);
                        } else {
                            responseContainer.RequestContainer.onError(responseContainer.StatusCode, responseContainer.Response);
                        }
                    }
                });

                mIsWorking = false;
            }
        };

        mRequestThread.start();
    }
}
