package com.sample.url.utility.mapper.config;

import com.sample.url.utility.mapper.exception.InvalidURLException;
import com.sample.url.utility.mapper.exception.SyntaxURLException;
import com.sample.url.utility.mapper.util.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Component
@Slf4j
public class URLValidator {

    public boolean validateURL(String urlStr) {
        try {
            new URL(urlStr).toURI();
            //check if URL is reachable
            return true;
           // return pingURL(urlStr, 10000);
        } catch (MalformedURLException | URISyntaxException e) {
            log.error("Msg:{}, class: {} ", e.getMessage(), e.getClass());
            throw new SyntaxURLException(ErrorCodeEnum.SHORTURLSYN.name());
        }
    }

    @Retryable(retryFor = InvalidURLException.class, maxAttempts = 5)
    public boolean pingURL(String url, int timeout) {
        url = url.replaceFirst("^https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            connection.setRequestProperty("Accept-Encoding", "musixmatch");
            int responseCode = connection.getResponseCode();
            log.debug("responseCode: " +responseCode);
            boolean isReachable = (200 <= responseCode && responseCode <= 399);
            log.debug("isReachable: " +isReachable);
            if(!isReachable)
                throw new InvalidURLException(ErrorCodeEnum.SHORTURLMAL.name());
            return true;
        } catch (Exception exception) {
            throw new InvalidURLException(ErrorCodeEnum.SHORTURLMAL.name());

        }
    }
}
