package com.valychbreak.mymedialib.testtools;

import com.google.gson.Gson;
import com.valychbreak.mymedialib.utils.gson.GsonBuilderTools;

public class TestUtils {
    public static String json(Object object) {
        Gson gson = GsonBuilderTools.getGsonBuilder().create();
        return gson.toJson(object);
    }
}
