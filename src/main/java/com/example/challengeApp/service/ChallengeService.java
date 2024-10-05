package com.example.challengeApp.service;

import com.example.challengeApp.payload.ChallengeDTO;
import com.example.challengeApp.payload.ChallengeResponse;
import com.example.challengeApp.payload.UpdateChallengeDTO;
import jakarta.validation.Valid;

public interface ChallengeService {
    ChallengeResponse getAllChallenges(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ChallengeDTO addChallenge(@Valid ChallengeDTO challengeDTO);

    ChallengeResponse getChallengesByMonth(String monthName, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ChallengeDTO updateChallenge(@Valid UpdateChallengeDTO updateChallengeDTO, Long id);

    ChallengeDTO deleteChallenge(Long id);
}
