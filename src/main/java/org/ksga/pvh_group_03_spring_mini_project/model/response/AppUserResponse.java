package org.ksga.pvh_group_03_spring_mini_project.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserResponse {
    private UUID appUserId;
    private String username;
    private String email;
    private Integer level;
    private Integer xp;
    private String profileImageUrl;
    private Boolean isVerified;
    private LocalDateTime createdAt;

}
