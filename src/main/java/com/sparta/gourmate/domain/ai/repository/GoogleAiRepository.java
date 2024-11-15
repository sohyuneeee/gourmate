package com.sparta.gourmate.domain.ai.repository;

import com.sparta.gourmate.domain.ai.entity.GoogleAi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GoogleAiRepository extends JpaRepository<GoogleAi, UUID> {
}
