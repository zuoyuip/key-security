package org.zuoyu.exception;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 自定义异常拦截.
 *
 * @author zuoyu
 **/
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * 自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<R<Object>> handlerCustomException(CustomException e) {
        log.error("errorCode is : " + e.getCode() + "\t" + "errorMessage is : " + e.getMsg());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.failed(e.getMsg()));
    }

    /**
     * 无权限
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<R<Object>> handlerAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<R<Object>> handlerNullPointerException(NullPointerException e) {
        return exceptionFormat(e.getMessage());
    }

    /**
     * sql异常
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<R<Object>> handlerSqlException(SQLException e) {
        return exceptionFormat(e.getErrorCode(), e.getMessage());
    }

    /**
     * 类型转换异常
     */
    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<R<Object>> handlerClassCastException(ClassCastException e) {
        return exceptionFormat(e.getMessage());
    }

    /**
     * IO异常
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<R<Object>> handlerIoException(IOException e) {
        return exceptionFormat(e.getMessage());
    }

    /**
     * 未知方法异常
     */
    @ExceptionHandler(NoSuchMethodException.class)
    public ResponseEntity<R<Object>> handlerNoSuchMethodException(NoSuchMethodException e) {
        return exceptionFormat(e.getMessage());
    }

    /**
     * 数组越界异常
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<R<Object>> handlerIndexOutOfBoundsException(IndexOutOfBoundsException e) {
        return exceptionFormat(e.getMessage());
    }

    /**
     * 栈溢出异常
     */
    @ExceptionHandler(StackOverflowError.class)
    public ResponseEntity<R<Object>> handlerStackOverflowError(StackOverflowError e) {
        return exceptionFormat(e.getMessage());
    }


    /**
     * 格式化
     */
    private ResponseEntity<R<Object>> exceptionFormat(String message) {
        log.error("errorMessage is : " + message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.failed("服务器异常"));
    }

    private ResponseEntity<R<Object>> exceptionFormat(int code, String message) {
        log.error("errorCode is : " + code + "\t" + "errorMessage is : " + message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.failed("服务器异常"));
    }
}
