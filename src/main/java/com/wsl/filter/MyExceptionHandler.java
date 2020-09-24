package com.wsl.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author tan
 * @version 1.0
 * @date 2020/9/24 15:16
 */
@RestControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public String errHandler1(UnauthorizedException e){
        log.error("没有通过权限验证!",e);
        return "没有通过权限验证!";
    }


}
