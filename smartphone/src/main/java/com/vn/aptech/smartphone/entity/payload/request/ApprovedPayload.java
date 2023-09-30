package com.vn.aptech.smartphone.entity.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApprovedPayload {
    private Long idStaff;
    private Long idOrder;
}
