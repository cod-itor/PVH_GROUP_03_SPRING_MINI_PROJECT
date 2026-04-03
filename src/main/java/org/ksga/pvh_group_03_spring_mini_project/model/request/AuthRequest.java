package org.ksga.pvh_group_03_spring_mini_project.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotBlank(message = "Username or Email cannot be blank!")
    private String identifier;
    @NotBlank(message = "Password cannot be blank!")
    private String password;
}
