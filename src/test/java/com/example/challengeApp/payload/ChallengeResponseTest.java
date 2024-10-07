package com.example.challengeApp.payload;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class ChallengeResponseTest {

    @Test
    void testChallengeResponseConstructorAndGetter() {
        ChallengeResponse challengeResponse = new ChallengeResponse(
                Arrays.asList(new ChallengeDTO(), new ChallengeDTO()),
                0, // pageNumber
                10, // pageSize
                20L, // totalElements
                2, // totalPages
                true // lastPage
        );

        assertEquals(2, challengeResponse.getChallengeDTOList().size());
        assertEquals(0, challengeResponse.getPageNumber());
        assertEquals(10, challengeResponse.getPageSize());
        assertEquals(20L, challengeResponse.getTotalElements());
        assertEquals(2, challengeResponse.getTotalPages());
        assertTrue(challengeResponse.getLastPage());
    }

    @Test
    void testChallengeResponseSetter() {
        ChallengeResponse challengeResponse = new ChallengeResponse();
        challengeResponse.setChallengeDTOList(Arrays.asList(new ChallengeDTO()));
        challengeResponse.setPageNumber(0);
        challengeResponse.setPageSize(10);
        challengeResponse.setTotalElements(20L);
        challengeResponse.setTotalPages(1);
        challengeResponse.setLastPage(true);

        assertEquals(1, challengeResponse.getChallengeDTOList().size());
        assertEquals(0, challengeResponse.getPageNumber());
        assertEquals(10, challengeResponse.getPageSize());
        assertEquals(20L, challengeResponse.getTotalElements());
        assertEquals(1, challengeResponse.getTotalPages());
        assertEquals(true, challengeResponse.getLastPage());
    }
}
