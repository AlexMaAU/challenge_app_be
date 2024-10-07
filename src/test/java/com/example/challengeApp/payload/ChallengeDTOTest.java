package com.example.challengeApp.payload;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChallengeDTOTest {

    @Test
    void testChallengeDTOConstructorAndGetter() {
        ChallengeDTO challengeDTO = new ChallengeDTO(1L, "April", "April Test");

        assertEquals(1L, challengeDTO.getId());
        assertEquals("April", challengeDTO.getMonthName());
        assertEquals("April Test", challengeDTO.getDescription());
    }

    @Test
    void testChallengeDTOSetter() {
        ChallengeDTO challengeDTO = new ChallengeDTO();
        challengeDTO.setId(2L);
        challengeDTO.setMonthName("March");
        challengeDTO.setDescription("March Test");

        assertEquals(2L, challengeDTO.getId());
        assertEquals("March", challengeDTO.getMonthName());
        assertEquals("March Test", challengeDTO.getDescription());
    }
}
