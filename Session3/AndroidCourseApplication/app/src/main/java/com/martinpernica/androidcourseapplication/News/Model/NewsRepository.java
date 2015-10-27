package com.martinpernica.androidcourseapplication.News.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsRepository {
    public ArrayList<NewsEntity> parseJsonResponse(String response) throws JSONException{
        JSONArray newsArray = new JSONArray(response);
        ArrayList<NewsEntity> newsEntityArrayList = new ArrayList<>();

        for (int i = 0; i < newsArray.length(); i++) {
            JSONObject jsonobject = newsArray.getJSONObject(i);

            NewsEntity newsEntity = new NewsEntity();
            newsEntity.Title = jsonobject.getString("title");
            newsEntity.Description = jsonobject.getString("description");

            newsEntityArrayList.add(newsEntity);
        }

        return newsEntityArrayList;
    }
}
