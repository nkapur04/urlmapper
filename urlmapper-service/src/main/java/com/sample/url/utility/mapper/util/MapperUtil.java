package com.sample.url.utility.mapper.util;

import io.seruco.encoding.base62.Base62;
import jakarta.xml.bind.DatatypeConverter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Slf4j
@UtilityClass
public class MapperUtil {

    private static final char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private final static int base = 62;
    private final static String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    @Deprecated
    private static String generateRandomChars(String candidateChars, int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }
        return sb.toString();
    }

    public static String generateBase62Code(String candidateChars) {
        StringBuilder sb = new StringBuilder();
        final byte[] encoded = Base62.createInstance().encode(candidateChars.getBytes());
        return new String(encoded);
    }

    private static String generateSHA256Code(String candidateChars) {
        byte[] hash;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            hash = messageDigest.digest(candidateChars.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return byteArray2Hex(hash);
    }

    public static String shortenUrl(String url) {
        String shortUrl;
        StringBuilder urlBuilder = new StringBuilder();
        //Step 1: Generate an MD5 hash of the long URL
        shortUrl = generateSHA256Code(url);
        urlBuilder = new StringBuilder(generateSHA256Code(url));

        //Step 2: Get First 6 bytes of the hash
        shortUrl = shortUrl.substring(0, 6);
        urlBuilder.substring(0, 6);
        log.debug("Step 2: " + shortUrl);

        //Step 3: Convert these bytes to decimal:
        shortUrl = toHexadecimal(shortUrl);
        log.debug("Step 3: " + shortUrl);

        //Step 4: Encode the result into a Base62 encoded string
        shortUrl = encode(Long.valueOf(shortUrl));
        log.debug("Step 4: " + shortUrl);
        return shortUrl;
    }

    private static String byteArray2Hex(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for (final byte b : bytes) {
            sb.append(hex[(b & 0xF0) >> 4]);
            sb.append(hex[b & 0x0F]);
        }
        return sb.toString();
    }

    private static String toHexadecimal(String text) {
        byte[] myBytes;
        try {
            myBytes = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return DatatypeConverter.printHexBinary(myBytes);
    }

    private static String encode(long number) {
        StringBuilder stringBuilder = new StringBuilder(1);
        do {
            stringBuilder.insert(0, characters.charAt((int) (number % base)));
            number /= base;
        } while (number > 0);
        return stringBuilder.toString();
    }


}
