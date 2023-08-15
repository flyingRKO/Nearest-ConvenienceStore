package com.example.nearestsnowboxfinder.snowbox.service;

import com.example.nearestsnowboxfinder.snowbox.entity.Snowbox;
import com.example.nearestsnowboxfinder.snowbox.repository.SnowboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnowboxRepositoryService {
    private final SnowboxRepository snowboxRepository;

    @Transactional(readOnly = true)
    public List<Snowbox> findAll() {
        return snowboxRepository.findAll();
    }

}
