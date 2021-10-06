package com.kyosk.config.v1.repositories;


import com.kyosk.config.entities.KConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface KConfigRepository extends MongoRepository<KConfig, String> {

    Optional<KConfig> findByName(String name);

    @Query(value = "{ '?0' : ?1 }")
    List<KConfig> complexSearchQuery(String field, Object value);
}
