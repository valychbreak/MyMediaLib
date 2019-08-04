package com.valychbreak.mymedialib.config;

import com.uwetrottmann.tmdb2.Tmdb;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieAPIConfig {
    @Bean
    public Tmdb tmdb(@Value("${tmdb.api.key}") String apiKey) {
        return new Tmdb(apiKey);
    }
}
