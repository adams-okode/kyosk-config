package com.kyosk.config.v1.controllers;

import com.kyosk.config.entities.KConfig;
import com.kyosk.config.v1.dtos.responses.GeneralResponse;
import com.kyosk.config.v1.services.KConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class KConfigController {

    @Autowired
    private KConfigService kConfigService;

    @GetMapping(path = "/configs")
    public ResponseEntity<GeneralResponse<List<KConfig>>> getAllConfigs() {
        GeneralResponse response = new GeneralResponse("Success", HttpStatus.OK, kConfigService.findAllConfigs(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping(path = "/configs")
    public ResponseEntity<GeneralResponse<Boolean>> createConfig(@RequestBody KConfig<Object> kConfig) {
        kConfigService.createConfig(kConfig);
        GeneralResponse response = new GeneralResponse("Config Created", HttpStatus.OK, true, LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(path = "/configs/{name}")
    public ResponseEntity<GeneralResponse<KConfig>> getConfig(@PathVariable String name) throws Exception {
        GeneralResponse response = new GeneralResponse("Success", HttpStatus.OK, kConfigService.findConfig(name), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping(path = "/configs/{name}")
    public ResponseEntity<GeneralResponse<Boolean>> updateConfig(@PathVariable String name, @RequestBody KConfig<Object> kConfig) {
        kConfigService.createConfig(kConfig);
        GeneralResponse response = new GeneralResponse("Config Updated", HttpStatus.OK, true, LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping(path = "/configs/{name}")
    public ResponseEntity<GeneralResponse<Boolean>> deleteConfig(@PathVariable String name) throws Exception {
        kConfigService.deleteConfig(name);
        GeneralResponse response = new GeneralResponse("Config Deleted", HttpStatus.OK, true, LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(path = "/search")
    public ResponseEntity<GeneralResponse<List<KConfig>>> searchConfigs(@RequestParam Map<String, Object> allRequestParams) throws Exception {
        Optional<String> firstKey = allRequestParams.keySet().stream().findFirst();
        if (firstKey.isEmpty()) {
            throw new Exception("Please provide Valid Search Params");
        }
        String field = firstKey.get();
        Object value = allRequestParams.get(field);

        GeneralResponse response = new GeneralResponse("Config Deleted", HttpStatus.OK, kConfigService.searchConfigs(field, value), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
