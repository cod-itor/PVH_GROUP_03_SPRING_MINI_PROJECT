package org.ksga.pvh_group_03_spring_mini_project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer userId;
    private String fullName;
    private String email;
    private String profilePicture;
}
