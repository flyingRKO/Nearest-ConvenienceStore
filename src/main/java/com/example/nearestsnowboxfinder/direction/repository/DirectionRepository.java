package com.example.nearestsnowboxfinder.direction.repository;

import com.example.nearestsnowboxfinder.direction.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
}
