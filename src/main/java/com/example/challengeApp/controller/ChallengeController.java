package com.example.challengeApp.controller;

import com.example.challengeApp.config.AppConstants;
import com.example.challengeApp.payload.ChallengeDTO;
import com.example.challengeApp.payload.ChallengeResponse;
import com.example.challengeApp.payload.UpdateChallengeDTO;
import com.example.challengeApp.service.ChallengeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @GetMapping
    public ResponseEntity<ChallengeResponse> getAllChallenges(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CHALLENGE_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR) String sortOrder
    ) {
        ChallengeResponse challengeResponse = challengeService.getAllChallenges(pageNumber, pageSize, sortBy, sortOrder);

        if(challengeResponse == null || challengeResponse.getChallengeDTOList() == null || challengeResponse.getChallengeDTOList().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(challengeResponse, HttpStatus.OK);
    }

    @GetMapping("/{monthName}")
    public ResponseEntity<ChallengeResponse> getChallengesByMonth(
            @PathVariable String monthName,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CHALLENGE_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR) String sortOrder
    ) {
        ChallengeResponse challengeResponse = challengeService.getChallengesByMonth(monthName, pageNumber, pageSize, sortBy, sortOrder);

        if(challengeResponse == null || challengeResponse.getChallengeDTOList() == null || challengeResponse.getChallengeDTOList().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(challengeResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ChallengeDTO> addChallenge(@Valid @RequestBody ChallengeDTO challengeDTO) {
        ChallengeDTO savedChallengeDTO = challengeService.addChallenge(challengeDTO);
        return new ResponseEntity<>(savedChallengeDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChallengeDTO> updateChallenge(@Valid @RequestBody UpdateChallengeDTO updateChallengeDTO, @PathVariable Long id) {
        ChallengeDTO updatedChallengeDTO = challengeService.updateChallenge(updateChallengeDTO, id);
        return new ResponseEntity<>(updatedChallengeDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ChallengeDTO> deleteChallenge(@PathVariable Long id) {
        ChallengeDTO deletedChallengeDTO = challengeService.deleteChallenge(id);
        return new ResponseEntity<>(deletedChallengeDTO, HttpStatus.OK);
    }

}
