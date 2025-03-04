package com.sample.url.utility.mapper.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "URL_MAPPING")
@Data
public class UrlMapperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer urlId;
    private String urlKey;
    private String longUrl;
    private LocalDate effectiveStartDate;
    private LocalDate effectiveEndDate;
    private String createdBy;
    private LocalDate updatedDate;
    private String updatedBy;
}
