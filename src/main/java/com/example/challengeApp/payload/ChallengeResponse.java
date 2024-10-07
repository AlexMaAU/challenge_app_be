package com.example.challengeApp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeResponse {
    private List<ChallengeDTO> challengeDTOList;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements; // auto-calculate
    private Integer totalPages; // auto-calculate
    private Boolean lastPage; // auto-calculate
}

