package com.valychbreak.mymedialib.repository;

import com.valychbreak.mymedialib.entity.media.Media;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by valych on 3/19/17.
 */
public interface MediaRepository extends CustomCrudRepository<Media, Long> {
    Media findByImdbId(String imdbId);
    List<Media> findByTitle(String title);
}
