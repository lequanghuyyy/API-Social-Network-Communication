package com.springboot.cms.exception;

public class NotFoundContentException extends CommonException {
    public NotFoundContentException(Integer id) {
        super("Not found content with id: " + id);
    }
}
