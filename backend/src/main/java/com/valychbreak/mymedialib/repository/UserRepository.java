package com.valychbreak.mymedialib.repository;

import com.valychbreak.mymedialib.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by valych on 2/25/17.
 */
public interface UserRepository extends CustomCrudRepository<User, Long> {
     List<User> findByUsername(String username);
     User findFirstByUsername(String username);
     List<User> findByUsernameAndPassword(String username, String password);
     List<User> findByUsernameOrNameIgnoreCaseContaining(String username, String name);
}
