package com.vn.aptech.smartphone.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    //@Schema(example = "4xx ClientError")
    private LocalDateTime timestamp;
    private String message;
    private String path;
    private HttpStatus errorCode;
}
