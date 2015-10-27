package com.martinpernica.androidcourseapplication.Http;

import com.martinpernica.androidcourseapplication.Http.Request.HttpRequestContainer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {
    public void sendRequest(HttpRequestContainer container) {
        HttpURLConnection connection = null;

        try{
            HttpEndpoint endpoint = container.getEndpoint();

            URL endpointUrl = new URL(endpoint.Url);

            connection = (HttpURLConnection)endpointUrl.openConnection();
            connection.setRequestMethod(connection.getRequestMethod());

            int responseCode = connection.getResponseCode();
            String responseBody = readInputStreamToString(connection.getInputStream());

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
        }
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
