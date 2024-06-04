package com.example.dragon.dto.competition;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCompetition {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Date start is mandatory")
    private String dateStart;

    @NotBlank(message = "Date end is mandatory")
    private String dateEnd;
}
