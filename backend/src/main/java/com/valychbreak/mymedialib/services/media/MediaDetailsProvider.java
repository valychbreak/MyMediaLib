package com.valychbreak.mymedialib.services.media;

import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.entity.media.Media;
import com.valychbreak.mymedialib.exception.ExternalAPIException;

public interface MediaDetailsProvider {
    MediaShortDetails getShortDetails(Media media);
    MediaFullDetails getDetails(Media media) throws ExternalAPIException;
}
