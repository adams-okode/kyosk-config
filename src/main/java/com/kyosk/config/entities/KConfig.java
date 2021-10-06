package com.kyosk.config.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Indexed;

import java.util.Map;

@Data
@Document(collection = "configs")
public class KConfig {

    @Id
    private String name;

    private Map<String, Object> metadata;
}
