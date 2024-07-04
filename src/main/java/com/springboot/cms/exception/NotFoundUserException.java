package com.springboot.cms.exception;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException(Long id) {
        super("Not found user with id " + id);
    }

    public NotFoundUserException() {
    }
}
