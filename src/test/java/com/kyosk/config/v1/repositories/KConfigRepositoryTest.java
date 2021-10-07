package com.kyosk.config.v1.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyosk.config.entities.KConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class KConfigRepositoryTest {

    private KConfig kConfig = new KConfig();

    @Autowired
    private KConfigRepository underTestkConfigRepository;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        kConfig.setName("datacenter-1");
        String jsonString = "{\"monitoring\":{\"enabled\":\"true\"},\"limits\":{\"cpu\":{\"enabled\":\"false\",\"value\":\"300m\"}}}";
        Map map = new ObjectMapper().readValue(jsonString, HashMap.class);
        kConfig.setMetadata(map);
        underTestkConfigRepository.save(kConfig);
    }


    @AfterEach
    void tearDown() {
        underTestkConfigRepository.deleteAll();
    }

    @Test
    void findByName() throws JsonProcessingException {
        KConfig testedConfig = underTestkConfigRepository.findByName("datacenter-1").orElse(null);
        /**
         * Assert that names are equal
         */
        assertEquals(testedConfig.getName(), kConfig.getName());

        /**
         * Assert that object nit found
         */
        testedConfig = underTestkConfigRepository.findByName("datacenter-5").orElse(null);
        assertNull(testedConfig);
    }

    @Test
    void complexSearchQuery() {
        System.out.println(underTestkConfigRepository.findAll());

        List<KConfig> testedConfig = underTestkConfigRepository.complexSearchQuery("metadata.monitoring.enabled", "true");
        Integer responseLength = testedConfig.size();
        assertTrue(responseLength >= 1);
    }

}