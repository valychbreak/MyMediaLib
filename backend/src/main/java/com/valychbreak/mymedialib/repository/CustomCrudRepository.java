package com.valychbreak.mymedialib.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomCrudRepository<T, ID> extends CrudRepository<T, ID> {
    @Deprecated(forRemoval = true)
    default T findOne(ID entityId) {
        Optional<T> foundEntity = this.findById(entityId);
        return foundEntity.orElse(null);
    }

    @Deprecated(forRemoval = true)
    default void save(Iterable<T> entities) {
        this.saveAll(entities);
    }

    @Deprecated(forRemoval = true)
    default void delete(Iterable<T> entities) {
        this.deleteAll(entities);
    }
}
