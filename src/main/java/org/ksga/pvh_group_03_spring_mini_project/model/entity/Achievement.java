package org.ksga.pvh_group_03_spring_mini_project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Achievement {
    private UUID achievementId;
    private String title;
    private String description;
    private String badge;
    private Integer xpRequired;
}
