package com.valychbreak.mymedialib.repository;

import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by valyc on 5/12/2017.
 */
public interface UserMediaCollectionRepository extends CrudRepository<UserMediaCollection, Long> {
    Iterable<UserMediaCollection> findAllByOwner(User owner);
}
