package com.valychbreak.mymedialib.utils;

import org.apache.commons.lang3.StringUtils;


public class TmdbUtils {
    private static final String TMDB_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w342";

    public static String getPosterImageLink(String posterPath) {
        if (StringUtils.isNotBlank(posterPath) && !posterPath.equals("null")) {
            return TMDB_IMAGE_BASE_URL + posterPath;
        } else {
            return "";
        }
    }
}
