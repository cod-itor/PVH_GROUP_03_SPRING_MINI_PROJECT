package org.ksga.pvh_group_03_spring_mini_project.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ksga.pvh_group_03_spring_mini_project.helper.Frequency;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitResponse {
    private UUID habitId;
    private String title;
    private String description;
    private Frequency frequency;
    private Boolean isActive;
    private AppUserResponse appUserResponse;
    private LocalDateTime createdAt;
}
