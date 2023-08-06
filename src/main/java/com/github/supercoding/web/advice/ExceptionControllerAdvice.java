package com.github.supercoding.web.advice;

import com.github.supercoding.service.exception.InvalidValueException;
import com.github.supercoding.service.exception.NotAcceptException;
import com.github.supercoding.service.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    //전역적으로 NotFoundException 발생하면 ExceptionControllerAdvice가 처리할 것이다!
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handlerNotFoundException(NotFoundException nfe){
        log.error("client 요청 이후에 문제가 있어 다음과 같이 출력합니다."+nfe.getMessage());
        return nfe.getMessage();
    }

    //전역적으로 NotAcceptException 발생하면 ExceptionControllerAdvice가 처리할 것이다!
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(NotAcceptException.class)
    public String handlerNotAcceptException(NotAcceptException nae){
        log.error("client 요청이 거부됩니다."+nae.getMessage());
        return nae.getMessage();
    }

    //전역적으로 BadRequestException 발생하면 ExceptionControllerAdvice가 처리할 것이다!
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidValueException.class)
    public String handlerInvalidValueException(InvalidValueException ive){
        log.error("client 요청이 유효하지 않은 값입니다."+ive.getMessage());
        return ive.getMessage();
    }
}

