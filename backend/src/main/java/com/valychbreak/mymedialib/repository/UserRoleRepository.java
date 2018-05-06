package com.valychbreak.mymedialib.repository;

import com.valychbreak.mymedialib.entity.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by valych on 4/1/17.
 */
public interface UserRoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(String role);
}
