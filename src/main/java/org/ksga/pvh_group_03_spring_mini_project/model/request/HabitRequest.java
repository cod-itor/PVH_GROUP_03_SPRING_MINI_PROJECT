package org.ksga.pvh_group_03_spring_mini_project.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitRequest {
    @NotBlank(message = "Description must not be blank.")
    private String title;

    @NotBlank(message = "Description must not be blank.")
    private String description;

    @NotNull(message = "Frequency cannot be null")
    @Pattern(regexp = "^(DAILY|WEEKLY|MONTHLY)$",
             message = "Frequency must be DAILY, WEEKLY, or MONTHLY in uppercase.")
    private String frequency;
}
