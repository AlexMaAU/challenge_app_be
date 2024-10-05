package com.example.challengeApp.repository;

import com.example.challengeApp.entity.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepo extends JpaRepository<Challenge, Long> {
    Page<Challenge> findByMonthNameIgnoreCase(String monthName, Pageable pageable);
}
