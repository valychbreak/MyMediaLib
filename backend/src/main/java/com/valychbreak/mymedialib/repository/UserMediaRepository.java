package com.valychbreak.mymedialib.repository;

import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by valych on 3/19/17.
 */
public interface UserMediaRepository extends CrudRepository<UserMedia, Long> {
    List<UserMedia> findByUser(User user);
}
