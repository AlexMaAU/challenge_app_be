package com.example.challengeApp.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeDTO {
    private Long id;
    @NotBlank
    @NotEmpty
    private String monthName;
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 2000, message = "Description between 2 and 2000")
    private String description;
}
