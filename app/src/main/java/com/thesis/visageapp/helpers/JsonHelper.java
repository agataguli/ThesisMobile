package com.thesis.visageapp.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thesis.visageapp.objects.User;

public class JsonHelper {

    public static User processUserStringJSON(String JSON) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(JSON, User.class);
    }
}
