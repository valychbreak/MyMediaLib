package com.valychbreak.mymedialib.utils.gson;

import com.google.gson.GsonBuilder;
import com.valychbreak.mymedialib.config.WebMvcConfig;

/**
 * Created by valych on 4/28/17.
 */
public class GsonBuilderTools {
    public static GsonBuilder getGsonBuilder() {
        return new GsonBuilder()
                //.excludeFieldsWithoutExposeAnnotation()
                //.setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .setExclusionStrategies(new WebMvcConfig.JsonAnnotationExcludeStrategy());
    }
}
