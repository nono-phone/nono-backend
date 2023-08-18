package com.vn.aptech.smartphone.entity.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@ToString
public class RegisterPayload {

    @Email
    @NotNull
    //@Schema(example = "mango@dqtri.com")
    private String email;

    @NotBlank
    @Length(min = 5, max = 24)
    private String password;
}
