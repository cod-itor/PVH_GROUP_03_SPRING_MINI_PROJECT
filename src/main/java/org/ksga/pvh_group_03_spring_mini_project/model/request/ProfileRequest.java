package org.ksga.pvh_group_03_spring_mini_project.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {
    private String username;
    private String profileImageUrl;
}
