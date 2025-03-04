package com.sample.url.utility.mapper.controller;

import com.sample.url.utility.mapper.dto.ErrorDetails;
import com.sample.url.utility.mapper.dto.ResponseDTO;
import com.sample.url.utility.mapper.exception.DataBaseException;
import com.sample.url.utility.mapper.exception.InvalidURLException;
import com.sample.url.utility.mapper.exception.SyntaxURLException;
import com.sample.url.utility.mapper.exception.URLExpiredException;
import com.sample.url.utility.mapper.util.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlingController {

    @ExceptionHandler(SyntaxURLException.class)
    public ResponseEntity<ResponseDTO> malfunctionedUrl(Exception e) {
        log.error("MalformedURLException");
        ResponseDTO responseDTO = new ResponseDTO();
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode(e.getMessage());
        errorDetails.setErrorMessage(ErrorCodeEnum.SHORTURLSYN.value());
        responseDTO.setErrorDetails(errorDetails);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }

    @ExceptionHandler(InvalidURLException.class)
    public ResponseEntity<ResponseDTO> invalidURLException(Exception e) {
        log.error("URISyntaxException");
        ResponseDTO responseDTO = new ResponseDTO();
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode(e.getMessage());
        errorDetails.setErrorMessage(ErrorCodeEnum.SHORTURLMAL.value());
        responseDTO.setErrorDetails(errorDetails);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<ResponseDTO> handleDBException(Exception e) {
        log.error("MalformedURLException");
        ResponseDTO responseDTO = new ResponseDTO();
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode(e.getMessage());
        errorDetails.setErrorMessage(ErrorCodeEnum.DBEXP.value());
        responseDTO.setErrorDetails(errorDetails);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
    }

    @ExceptionHandler(URLExpiredException.class)
    public ResponseEntity<ResponseDTO> expiredURLException(Exception e) {
        log.error("URLExpiredException");
        ResponseDTO responseDTO = new ResponseDTO();
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode(e.getMessage());
        errorDetails.setErrorMessage(ErrorCodeEnum.EXPURL.value());
        responseDTO.setErrorDetails(errorDetails);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }
}
