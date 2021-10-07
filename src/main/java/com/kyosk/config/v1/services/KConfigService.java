package com.kyosk.config.v1.services;

import com.kyosk.config.entities.KConfig;
import com.kyosk.config.v1.repositories.KConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Service
@Validated
public class KConfigService {

    @Autowired
    private KConfigRepository kConfigRepository;

    /**
     * Find All available configs
     *
     * @return
     */
    public List<KConfig> findAllConfigs() {
        return kConfigRepository.findAll();
    }

    /**
     * Create the Config
     *
     * @param kConfig
     */
    public KConfig createConfig(@Valid KConfig kConfig) {
        return kConfigRepository.save(kConfig);
    }


    /**
     * Find Config By Name
     *
     * @param name
     * @return
     * @throws Exception
     */
    public KConfig findConfig(String name) throws Exception {
        KConfig kConfig = kConfigRepository.findByName(name).orElse(null);
        if (kConfig == null) {
            throw new Exception("Config: " + name + " Doesn't exist");
        }
        return kConfig;
    }


    /**
     * Update the Config
     *
     * @param name
     * @param kConfigMetaData
     */
    public KConfig updateConfig(String name, Map kConfigMetaData) throws Exception {
        KConfig config = kConfigRepository.findByName(name).orElse(null);
        if (config == null) {
            throw new Exception("Config: " + name + " Doesn't exist");
        }
        config.setMetadata(kConfigMetaData);
        return kConfigRepository.save(config);
    }

    /**
     * Delete the Config
     *
     * @param name
     */
    public void deleteConfig(String name) throws Exception {
        KConfig kConfig = kConfigRepository.findByName(name).orElse(null);
        if (kConfig == null) {
            throw new Exception("Config: " + name + " Doesn't exist");
        }
        kConfigRepository.delete(kConfig);
    }

    /**
     * Search using Dynamic Keys
     *
     * @param field
     * @param value
     * @return
     */
    public List<KConfig> searchConfigs(String field, Object value) {
        return kConfigRepository.complexSearchQuery(field, value);
    }

}
