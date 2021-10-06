package com.kyosk.config.v1.dtos.responses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralResponse<T> {
    private String message;
    private HttpStatus status;
    private T data;
    private LocalDateTime timestamp;
}
