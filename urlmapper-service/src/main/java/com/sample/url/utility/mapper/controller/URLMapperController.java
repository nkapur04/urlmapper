package com.sample.url.utility.mapper.controller;

import com.sample.url.utility.mapper.config.AppProperty;
import com.sample.url.utility.mapper.dto.ResponseDTO;
import com.sample.url.utility.mapper.service.URLMapperService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class URLMapperController {

    private final URLMapperService urlMapperService;

    private final AppProperty appProperty;

    @GetMapping(value = "/checkStatus")
    public ResponseEntity<Object> checkStatus() {
        return new ResponseEntity<>("UP: " + appProperty.getUsername(), HttpStatus.OK);
    }

    @PostMapping("/shorten")
    @Timed(value = "shortenURL", description = "Time taken to shorten the URL")
    public ResponseEntity<ResponseDTO> shortenURL(@RequestBody String url) {
        log.debug("shortUrl Start time: " + LocalDateTime.now());
        ResponseDTO responseDTO = urlMapperService.shortenURL(url);
        log.debug("shortUrl end time: " + LocalDateTime.now());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Object> redirectToLongUrl(@PathVariable String shortUrl) {
        ResponseEntity<Object> responseEntity;
        log.debug("redirect Start time: " + LocalDateTime.now());
        String longUrl = urlMapperService.getLongUrl(shortUrl);
        responseEntity = (longUrl.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND) // could use 301 or 302
                .build() :
                ResponseEntity.status(HttpStatus.FOUND) // could use 301 or 302
                        .location(URI.create(longUrl)) // Set the location header to the long URL
                        .build());
        log.debug("redirect end time: " + LocalDateTime.now());
        return responseEntity;
    }

    @GetMapping("/evictCache")
    public ResponseEntity<Object> evictCache() {
        urlMapperService.evictCache();
        return new ResponseEntity<>("Cache evicted", HttpStatus.OK);
    }
}
