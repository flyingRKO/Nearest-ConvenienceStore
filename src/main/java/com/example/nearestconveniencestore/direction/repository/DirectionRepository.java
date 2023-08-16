package com.example.nearestconveniencestore.direction.repository;

import com.example.nearestconveniencestore.direction.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
}
