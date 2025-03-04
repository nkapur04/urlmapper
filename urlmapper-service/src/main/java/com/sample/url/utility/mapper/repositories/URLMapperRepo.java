package com.sample.url.utility.mapper.repositories;

import com.sample.url.utility.mapper.entities.UrlMapperEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface URLMapperRepo extends JpaRepository<UrlMapperEntity, Integer> {

    @Cacheable(value = "mapperEntityWithParams", key = "#effectiveStartDate + '-' + #effectiveEndDate + '-' + #longUrl")
    UrlMapperEntity findByEffectiveStartDateLessThanEqualAndEffectiveEndDateGreaterThanEqualAndLongUrl(@Param("effectiveStartDate") LocalDate effectiveStartDate,
                                                                                                       @Param("effectiveEndDate") LocalDate effectiveEndDate,
                                                                                                       @Param("longUrl") String longUrl);

    @Cacheable(value = "mapperEntity", key = "#urlKey")
    UrlMapperEntity findByUrlKey(@Param("urlKey") String urlKey);

    UrlMapperEntity findByUrlKeyAndLongUrlNot(@Param("urlKey") String urlKey,@Param("longUrl") String longUrl);

    @Cacheable(value = "mapperEntity", key = "#longUrl")
    UrlMapperEntity findByLongUrl(@Param("longUrl") String longUrl);

    @Modifying
    @Transactional
    @Query("update UrlMapperEntity u set u.effectiveEndDate= :effectiveEndDate where u.longUrl= :longUrl and u.urlKey= :urlKey")
    void updateEffectiveDate(@Param("longUrl") String longUrl, @Param("urlKey") String urlKey,@Param("effectiveEndDate") LocalDate effectiveEndDate);
}
