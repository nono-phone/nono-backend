package com.vn.aptech.smartphone.entity.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InfoPayload {
    private String phone;
    private String image;
    private String gender;
    @JsonProperty(value = "data_of_birth")
    private String dateOfBirth;
}
