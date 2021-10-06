package com.kyosk.config.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Indexed;

@Data
@Document(collation = "configs")
public class KConfig<T> {

    @Id
    private String name;

    private T metadata;
}
