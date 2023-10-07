package com.vn.aptech.smartphone.entity.payload.request;

import com.vn.aptech.smartphone.entity.SafeguardUser;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserPayload {
    List<SafeguardUser> users;
}
