package com.valychbreak.mymedialib.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface CustomCrudRepository<T, ID> extends CrudRepository<T, ID> {
    @Deprecated(forRemoval = true)
    default T findOne(ID entityId) {
        Optional<T> foundEntity = this.findById(entityId);
        return foundEntity.orElse(null);
    }
}
