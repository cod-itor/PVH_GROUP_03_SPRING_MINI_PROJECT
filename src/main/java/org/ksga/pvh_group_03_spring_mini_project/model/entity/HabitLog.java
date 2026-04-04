package org.ksga.pvh_group_03_spring_mini_project.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitLog {
    private UUID habitLogId;
    private LocalDateTime logDate;
    private String status;
    @JsonIgnore
    private UUID habitId;
    private Integer xpEarned;
    private Habit habit;
}
