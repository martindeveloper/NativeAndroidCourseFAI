package com.martinpernica.androidcourseapplication.Http;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestAsync {
    private Thread mRequestThread;
    private boolean mIsWorking = false;

    public HttpRequestAsync() {
    }

    public void startRequest(final HttpRequestContainer container) {
        if(mIsWorking) {
            return;
        }

        mIsWorking = true;

        mRequestThread = new Thread() {
            public void run() {
                HttpURLConnection connection = null;
                try{
                    URL endpoint = new URL(container.getEndpointUrl());

                    connection = (HttpURLConnection)endpoint.openConnection();
                    connection.setRequestMethod(connection.getRequestMethod());

                    int responseCode = connection.getResponseCode();
                    String responseBody = ReadInputStreamToString(connection.getInputStream());

                    if(responseCode == 200) {
                        container.onSuccess(responseBody);
                    }else {
                        container.onError(responseCode, responseBody);
                    }
                }catch(Exception ex) {
                    container.onException(ex);
                }finally {
                    if(connection != null){
                        connection.disconnect();
                    }

                    mIsWorking = false;
                }
            }
        };

        mRequestThread.start();
    }

    private String ReadInputStreamToString(InputStream inputStream) {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String inputLine;

        try {
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
        }catch (Exception ex) {
            // TODO: Handle exception properly
            return "";
        }

        return stringBuilder.toString();
    }
}
