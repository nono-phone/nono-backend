package com.vn.aptech.smartphone.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    //@Schema(example = "4xx ClientError")
    private HttpStatus status;
    private String message;
}
