package com.valychbreak.mymedialib.services.media;

import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserMediaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserMediaService {
    private UserMediaRepository userMediaRepository;

    public UserMediaService(UserMediaRepository userMediaRepository) {
        this.userMediaRepository = userMediaRepository;
    }

    public boolean isUserFavorite(User user, MediaShortDetails mediaShortDetails) {
        return userMediaRepository.existsByUserAndMediaImdbId(user, mediaShortDetails.getImdbId());
    }
}
