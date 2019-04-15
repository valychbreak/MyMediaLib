package com.valychbreak.mymedialib.data;

public enum MediaSearchType {
    MEDIA, MOVIE, TVSHOW, UNKNOWN;

    public static MediaSearchType get(String mediaType) {
        for (MediaSearchType mediaSearchType : MediaSearchType.values()) {
            if (mediaSearchType.name().equalsIgnoreCase(mediaType)) {
                return mediaSearchType;
            }
        }

        return UNKNOWN;
    }
}
