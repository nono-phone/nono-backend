package com.vn.aptech.smartphone.entity.payload.request;

import com.vn.aptech.smartphone.common.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UpdateRolePayload {
    @NotNull
    private Role role;
}
