package com.sample.url.utility.mapper.service;

import com.sample.url.utility.mapper.config.URLValidator;
import com.sample.url.utility.mapper.dto.ResponseDTO;
import com.sample.url.utility.mapper.entities.UrlMapperEntity;
import com.sample.url.utility.mapper.exception.DataBaseException;
import com.sample.url.utility.mapper.exception.InvalidURLException;
import com.sample.url.utility.mapper.exception.URLExpiredException;
import com.sample.url.utility.mapper.repositories.URLMapperRepo;
import com.sample.url.utility.mapper.util.ErrorCodeEnum;
import com.sample.url.utility.mapper.util.MapperConstants;
import com.sample.url.utility.mapper.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class URLMapperService {


    private final URLMapperRepo urlMapperRepo;
    //private final ApplicationProperties applicationProperties;
    private final URLValidator urlValidator;

    public ResponseDTO shortenURL(String url) {
        //log.info("property: " + applicationProperties.domainName());
        ResponseDTO responseDTO = new ResponseDTO();
        String shortUrl = null;
        String action = null;
        boolean validUrl = urlValidator.validateURL(url);

        log.info("validUrl: " + validUrl);
        if (validUrl) {
            urlValidator.pingURL(url, 10000);
            //fetch if existing url from DB
            Map<String, String> shortUrlMap = fetchURlDetails(url);
            log.info("url Map: " + shortUrlMap);
            for (var entry : shortUrlMap.entrySet()) {
                shortUrl = entry.getKey();
                action = entry.getValue();
            }
            log.debug("shortUrl: " + shortUrl);
            log.debug("action: " + action);
            switch (action) {
                case MapperConstants.NO_ACTION_MODE:
                    responseDTO.setUrl(shortUrl);
                    break;

                case MapperConstants.SAVE_MODE:
                    shortUrl = MapperUtil.shortenUrl(url);

                    //check if shortUrl exists in DB
                    checkIfKeyExists(shortUrl, url);

                    //persist URL in DB
                    persistInDB(url, shortUrl);
                    responseDTO.setUrl(shortUrl);
                    break;

                case MapperConstants.UPDATE_MODE:
                    log.debug("update in db");
                    updateInDB(url,shortUrl);
                    responseDTO.setUrl(shortUrl);
                    break;

                default:
                    log.warn("Unknown action mode: " + action);
                    break;
            }
        }
        else {
            throw new InvalidURLException(ErrorCodeEnum.SHORTURLMAL.name());
        }
        return responseDTO;
    }

    private void updateInDB(String url, String shortUrl) {
        urlMapperRepo.updateEffectiveDate(url,shortUrl, LocalDate.of(9999, 12, 31));
    }

    private String checkIfKeyExists(String shortUrl, String longUrl) {
        String newUrlKey;
        UrlMapperEntity urlMapperEntity = urlMapperRepo.findByUrlKeyAndLongUrlNot(shortUrl, longUrl);
        if (null != urlMapperEntity) {
            newUrlKey = MapperUtil.shortenUrl(longUrl);
            checkIfKeyExists(newUrlKey, longUrl);
        } else {
            return shortUrl;
        }
        return null;
    }

    @CachePut(value = "mapperEntityWithParams", key = "#UrlMapperEntity.effectiveStartDate + '-' + #UrlMapperEntity.effectiveEndDate + '-' + #UrlMapperEntity.longUrl")
    private void persistInDB(String url, String shortUrl) {
        try {
            UrlMapperEntity urlMapperEntity = new UrlMapperEntity();
            urlMapperEntity.setLongUrl(url);
            urlMapperEntity.setUrlKey(shortUrl);
            urlMapperEntity.setCreatedBy(MapperConstants.USER_1);
            urlMapperEntity.setUpdatedBy(MapperConstants.USER_1);
            urlMapperEntity.setUpdatedDate(LocalDate.now());
            urlMapperEntity.setEffectiveStartDate(LocalDate.now());
            urlMapperEntity.setEffectiveEndDate(LocalDate.of(9999, 12, 31));
            urlMapperRepo.save(urlMapperEntity);
        } catch (Exception e) {
            throw new DataBaseException(ErrorCodeEnum.DBEXP.name());
        }
    }

    private Map<String, String> fetchURlDetails(String url) {
        Map<String, String> urlMap = new HashMap<>();

        UrlMapperEntity urlMapperEntity = urlMapperRepo.findByLongUrl(url);
        //String shortUrl = (null != urlMapperEntity && null != urlMapperEntity.getUrlKey() ? urlMapperEntity.getUrlKey() : StringUtils.EMPTY);
        String shortUrl = StringUtils.EMPTY;
        if (null == urlMapperEntity)
            urlMap.put(shortUrl, MapperConstants.SAVE_MODE);
        else {
            shortUrl = urlMapperEntity.getUrlKey();
            LocalDate currentDate = LocalDate.now();
            if (currentDate.compareTo(urlMapperEntity.getEffectiveStartDate()) >=0 && currentDate.compareTo(urlMapperEntity.getEffectiveEndDate())<=0)
                urlMap.put(shortUrl, MapperConstants.NO_ACTION_MODE);
            else
                urlMap.put(shortUrl, MapperConstants.UPDATE_MODE);
        }
        /*        log.info("url from DB: " + shortUrl);*/
        return urlMap;
    }

    public String getLongUrl(String shortUrl) {
        //add validation for special characters
        //fetch from DB
        UrlMapperEntity urlMapperEntity = urlMapperRepo.findByUrlKey(shortUrl);
        String longUrl = (null != urlMapperEntity && null != urlMapperEntity.getLongUrl() ? urlMapperEntity.getLongUrl() : StringUtils.EMPTY);
        log.debug("url from DB: " + longUrl);
        //check for effective date
        if (null != longUrl && !StringUtils.EMPTY.equals(longUrl)) {
            LocalDate effectiveEndDate = (null != urlMapperEntity.getEffectiveEndDate() ? urlMapperEntity.getEffectiveEndDate() : null);
            ChronoLocalDate today = LocalDate.now();
            if (effectiveEndDate.isBefore(today))
                throw new URLExpiredException(ErrorCodeEnum.EXPURL.name());
        }
        return longUrl;

    }
}
