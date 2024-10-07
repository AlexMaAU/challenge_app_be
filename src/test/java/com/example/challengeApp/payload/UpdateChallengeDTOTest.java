package com.example.challengeApp.payload;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateChallengeDTOTest {

    @Test
    void testUpdateChallengeDTOConstructorAndGetter() {
        UpdateChallengeDTO updateChallengeDTO = new UpdateChallengeDTO(1L, "April", "April Test");

        assertEquals(1L, updateChallengeDTO.getId());
        assertEquals("April", updateChallengeDTO.getMonthName());
        assertEquals("April Test", updateChallengeDTO.getDescription());
    }

    @Test
    void testUpdateChallengeDTOSetter() {
        UpdateChallengeDTO updateChallengeDTO = new UpdateChallengeDTO();
        updateChallengeDTO.setId(2L);
        updateChallengeDTO.setMonthName("March");
        updateChallengeDTO.setDescription("March Test");

        assertEquals(2L, updateChallengeDTO.getId());
        assertEquals("March", updateChallengeDTO.getMonthName());
        assertEquals("March Test", updateChallengeDTO.getDescription());
    }
}
