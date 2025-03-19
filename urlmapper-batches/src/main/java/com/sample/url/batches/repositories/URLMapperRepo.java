package com.sample.url.batches.repositories;

import com.sample.url.batches.entities.UrlMapperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface URLMapperRepo extends JpaRepository<UrlMapperEntity, Integer> {

    UrlMapperEntity findByEffectiveEndDateGreaterThan(@Param("effectiveEndDate") LocalDate effectiveEndDate);
}
