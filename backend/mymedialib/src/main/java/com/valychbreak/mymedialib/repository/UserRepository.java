package com.valychbreak.mymedialib.repository;

import com.valychbreak.mymedialib.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by valych on 2/25/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {
     List<User> findByUsername(String username);
}
