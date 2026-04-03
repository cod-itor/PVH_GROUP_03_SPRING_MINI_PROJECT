package org.ksga.pvh_group_03_spring_mini_project.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OTPVerifyRequest {
    private String email;
    private String otp;
}
