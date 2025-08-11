package com.fitness.stat_service.repository;

import com.fitness.stat_service.model.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StatsRepository extends JpaRepository<Stats, Long> {
    Optional<Stats> findByUsername(String username);
}
