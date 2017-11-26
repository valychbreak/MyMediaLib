package com.valychbreak.mymedialib.config;

import com.uwetrottmann.tmdb2.Tmdb;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieAPIConfig {
    @Bean
    public Tmdb tmdb() {
        return new Tmdb("01e924145da414b33cdc651619bb694b");
    }
}
