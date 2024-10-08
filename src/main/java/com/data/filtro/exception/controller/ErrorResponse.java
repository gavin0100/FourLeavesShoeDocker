package com.data.filtro.exception.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
//import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponse implements Serializable {
    private int statusCode;
    private String message;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Instant timestamp;
}
