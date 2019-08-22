package com.domain.exception;

/**
 * create by mjl on2018/9/19
 */
public class MyException extends RuntimeException{
    private String message;

    public MyException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
