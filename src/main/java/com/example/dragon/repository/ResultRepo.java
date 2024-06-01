package com.example.dragon.repository;

import com.example.dragon.entity.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepo extends JpaRepository<ResultEntity, Long> {
}
