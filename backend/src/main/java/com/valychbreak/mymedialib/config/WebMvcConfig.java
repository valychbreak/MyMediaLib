package com.valychbreak.mymedialib.config;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.valychbreak.mymedialib.tools.gson.GsonBuilderTools;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.valychbreak.mymedialib.tools.gson.JsonExclude;

import java.util.List;

/**
 * Created by valych on 3/19/17.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(createGsonHttpMessageConverter());
        super.configureMessageConverters(converters);
    }

    private GsonHttpMessageConverter createGsonHttpMessageConverter() {
        Gson gson = GsonBuilderTools.getGsonBuilder().create();

        GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter();
        gsonConverter.setGson(gson);

        return gsonConverter;
    }

    public static class JsonAnnotationExcludeStrategy
            implements ExclusionStrategy
    {
        @Override
        public boolean shouldSkipClass(Class<?> clazz)
        {
            return false;
        }


        @Override
        public boolean shouldSkipField(FieldAttributes f)
        {
            return f.getAnnotation(JsonExclude.class) != null;
        }
    }
}
