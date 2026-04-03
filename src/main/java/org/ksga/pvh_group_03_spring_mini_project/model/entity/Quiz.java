package org.ksga.pvh_group_03_spring_mini_project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    private Integer quizId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private Boolean isRealTime;
    private Integer timeLimit;
    private String accessCode;
    private LocalDateTime startAt;
    private Integer totalScore;

    private Integer subjectId;
    private Integer creatorId;
}
