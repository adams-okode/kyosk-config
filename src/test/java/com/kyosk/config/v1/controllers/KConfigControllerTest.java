package com.kyosk.config.v1.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyosk.config.entities.KConfig;
import com.kyosk.config.v1.services.KConfigService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = KConfigController.class)
class KConfigControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KConfigService kConfigService;

    private List<KConfig> dummyConfigurations = new ArrayList<>();

    private KConfig masterTestkConfig = new KConfig();

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws JsonProcessingException {
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
    }

    @Test
    void getAllConfigs() throws Exception {
        doReturn(dummyConfigurations).when(kConfigService).findAllConfigs();
        mockMvc.perform(get("/configs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()", is(dummyConfigurations.size())));
    }

    @Test
    void createConfig() throws Exception {
        doReturn(masterTestkConfig).when(kConfigService).createConfig(masterTestkConfig);
        this.mockMvc.perform(post("/configs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(masterTestkConfig)))
                .andExpect(status().isOk());
    }

    @Test
    void getConfig() throws Exception {
        doReturn(masterTestkConfig).when(kConfigService).findConfig(masterTestkConfig.getName());
        mockMvc.perform(get("/configs/{name}", masterTestkConfig.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is(masterTestkConfig.getName())));
    }

    @Test
    void updateConfig() throws Exception {
        doReturn(masterTestkConfig).when(kConfigService).createConfig(masterTestkConfig);
        this.mockMvc.perform(put("/configs/{name}", masterTestkConfig.getName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(masterTestkConfig.getMetadata())))
                .andExpect(status().isOk());
    }

    @Test
    void deleteConfig() throws Exception {
        mockMvc.perform(delete("/configs/{name}", masterTestkConfig.getName()))
                .andExpect(status().isOk());
    }

    @Test
    void searchConfigs() throws Exception {
        doReturn(List.of(masterTestkConfig)).when(kConfigService).searchConfigs("metadata.monitoring.enabled", "true");
        mockMvc.perform(get("/search")
                .param("metadata.monitoring.enabled","true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()", greaterThan(0)));
    }
}