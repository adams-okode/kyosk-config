package com.kyosk.config.v1.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyosk.config.configurations.MongoConfig;
import com.kyosk.config.entities.KConfig;
import com.kyosk.config.v1.repositories.KConfigRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// TODO: Coverage Report
@ExtendWith(MockitoExtension.class)
@Import(MongoConfig.class)
class KConfigServiceTest {

    @Mock
    private KConfigRepository kConfigRepository;

    @InjectMocks
    private KConfigService kConfigService;

    private List<KConfig> dummyConfigurations = new ArrayList<>();

    private KConfig masterTestkConfig = new KConfig();

    @BeforeEach
    void setUp() throws JsonProcessingException {
        /**
         * Set up x no of data-centers for test
         */
        for (int i = 0; i < 5; i++) {
            KConfig kConfig = new KConfig();
            kConfig.setName("datacenter-" + i);
            String jsonString = "{\"monitoring\":{\"enabled\":\"true\"},\"limits\":{\"cpu\":{\"enabled\":\"false\",\"value\":\"300m\"}}}";
            Map map = new ObjectMapper().readValue(jsonString, HashMap.class);
            kConfig.setMetadata(map);
            dummyConfigurations.add(kConfig);
        }

        /**
         * Set up Global Object to be reused for other tests
         */
        masterTestkConfig.setName("datacenter-10");
        String jsonString = "{\"monitoring\":{\"enabled\":\"true\"},\"limits\":{\"cpu\":{\"enabled\":\"false\",\"value\":\"300m\"}}}";
        Map map = new ObjectMapper().readValue(jsonString, HashMap.class);
        masterTestkConfig.setMetadata(map);


    }

    @AfterEach
    void tearDown() {
        dummyConfigurations = new ArrayList<>();
        masterTestkConfig = new KConfig();
    }

    @Test
    void findAllConfigs() {
        doReturn(dummyConfigurations).when(kConfigRepository).findAll();
        List<KConfig> testedConfig = kConfigService.findAllConfigs();
        Integer responseLength = testedConfig.size();
        assertTrue(responseLength == 5);
    }

    @Test
    void createConfig() throws JsonProcessingException {
        doReturn(masterTestkConfig).when(kConfigRepository).save(masterTestkConfig);

        /**
         * Test Normal Creation
         */
        KConfig savedConfig = kConfigService.createConfig(masterTestkConfig);
        assertEquals(savedConfig.getName(), masterTestkConfig.getName());
    }

    @Test
    void findConfig() throws Exception {
        doReturn(Optional.of(masterTestkConfig)).when(kConfigRepository).findByName("datacenter-10");

        KConfig testedConfig = kConfigService.findConfig("datacenter-10");
        /**
         * Assert that names are equal
         */
        assertEquals(testedConfig.getName(), masterTestkConfig.getName());

        assertEquals(assertThrows(Exception.class, () -> {
            kConfigService.findConfig("datacenter-20");
        }).getMessage(), "Config: datacenter-20 Doesn't exist");
    }

    @Test
    void updateConfig() throws Exception {
        doReturn(Optional.of(masterTestkConfig)).when(kConfigRepository).findByName("datacenter-10");
        doReturn(masterTestkConfig).when(kConfigRepository).save(masterTestkConfig);

        KConfig savedConfig = kConfigService.updateConfig(masterTestkConfig.getName(), masterTestkConfig.getMetadata());
        assertEquals(assertThrows(Exception.class, () -> {
            kConfigService.updateConfig("datacenter-20", masterTestkConfig.getMetadata());
        }).getMessage(), "Config: datacenter-20 Doesn't exist");
    }

    @Test
    void deleteConfig() throws Exception {
        doReturn(Optional.of(masterTestkConfig)).when(kConfigRepository).findByName("datacenter-10");
        doNothing().when(kConfigRepository).delete(masterTestkConfig);

        kConfigService.deleteConfig("datacenter-10");

        verify(kConfigRepository, Mockito.times(1)).delete(masterTestkConfig);
        verify(kConfigRepository, Mockito.times(1)).findByName("datacenter-10");

        assertEquals(assertThrows(Exception.class, () -> {
            kConfigService.deleteConfig("datacenter-20");
        }).getMessage(), "Config: datacenter-20 Doesn't exist");
    }

    @Test
    void searchConfigs() {
        doReturn(List.of(masterTestkConfig)).when(kConfigRepository).complexSearchQuery("metadata.monitoring.enabled", "true");
        List<KConfig> testedConfig = kConfigService.searchConfigs("metadata.monitoring.enabled", "true");
        Integer responseLength = testedConfig.size();
        assertTrue(responseLength >= 1);
    }
}