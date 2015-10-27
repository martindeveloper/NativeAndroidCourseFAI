package com.martinpernica.androidcourseapplication.Http;

import android.util.Log;

import com.martinpernica.androidcourseapplication.Http.Request.HttpRequestContainer;
import com.martinpernica.androidcourseapplication.Http.Response.HttpResponseContainer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {
    public HttpResponseContainer sendRequest(HttpRequestContainer container) {
        HttpURLConnection connection = null;
        HttpResponseContainer responseContainer = new HttpResponseContainer();

        try{
            HttpEndpoint endpoint = container.getEndpoint();

            URL endpointUrl = new URL(endpoint.Url);

            connection = (HttpURLConnection)endpointUrl.openConnection();
            connection.setRequestMethod(connection.getRequestMethod());

            int responseCode = connection.getResponseCode();

            responseContainer.Response = readInputStreamToString(connection.getInputStream());
            responseContainer.StatusCode = responseCode;
            responseContainer.IsOK = responseCode == 200;
            responseContainer.RequestContainer = container;

        }catch(Exception ex) {
            Log.d(HttpHelper.class.toString(), String.format("Unhandled exception - %s", ex.getMessage()));
        }finally {
            if(connection != null){
                connection.disconnect();
            }
        }

        return responseContainer;
    }

    public String readInputStreamToString(InputStream inputStream) {
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
