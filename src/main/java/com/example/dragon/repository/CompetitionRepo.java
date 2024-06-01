package com.example.dragon.repository;

import com.example.dragon.entity.CompetitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepo extends JpaRepository<CompetitionEntity, Long> {
}
