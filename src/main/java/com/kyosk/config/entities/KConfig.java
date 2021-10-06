package com.kyosk.config.entities;

import com.mongodb.lang.NonNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@Accessors(chain = true)
@Document(collection = "configs")
public class KConfig {

    @Indexed(unique=true)
    @NotBlank
    @NotNull
    @NotEmpty
    private String name;

    private Map<String, Object> metadata;
}
