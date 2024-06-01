package com.example.dragon.repository;

import com.example.dragon.entity.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepo extends JpaRepository<ParticipantEntity, Long> {

}
