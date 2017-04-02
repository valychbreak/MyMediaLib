package com.valychbreak.mymedialib.repository;

import com.valychbreak.mymedialib.entity.UserRole;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by valych on 4/1/17.
 */
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    UserRole findByRole(String role);
}
