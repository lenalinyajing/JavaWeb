package com.xsz.common.exception;//package java.com.xsz.common.config;

import com.xsz.common.domain.ResponseBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.ParseException;

@ControllerAdvice
public class ControllerExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBo NotFindException(NullPointerException e){
        return ResponseBo.failure(404,"找不到相关信息---"+e.getMessage());
    }

    @ExceptionHandler(ParseException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBo NotFindException(ParseException e){
        logger.error("日期或者字符串格式化异常",e);
        return ResponseBo.error(e.getMessage());
    }
}
