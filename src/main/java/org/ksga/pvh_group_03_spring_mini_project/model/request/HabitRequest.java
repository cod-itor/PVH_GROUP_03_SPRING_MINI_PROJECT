package org.ksga.pvh_group_03_spring_mini_project.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitRequest {
    @NotBlank(message = "must not be blank")
    private String title;

    @NotBlank(message = "must not be blank")
    private String description;

    @NotNull(message = "Frequency cannot be null")
    private String frequency;
}
