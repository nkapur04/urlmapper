package com.sample.url.batches.entities;

import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDate;

@Entity
@Table(name = "URL_MAPPING")
@Data
//@Setter
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

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
