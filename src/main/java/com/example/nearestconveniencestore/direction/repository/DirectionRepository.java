package com.example.nearestconveniencestore.direction.repository;

import com.example.nearestconveniencestore.direction.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
    Optional<List<Direction>> findDirectionByInputAddress(String address);
}
