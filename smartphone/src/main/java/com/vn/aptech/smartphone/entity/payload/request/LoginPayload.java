package com.vn.aptech.smartphone.entity.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
public class LoginPayload {
    @Email
    @NotNull
    @JsonProperty("username")
    private String email;

    @NotBlank
    @Length(min = 5, max = 24)
    private String password;
}
