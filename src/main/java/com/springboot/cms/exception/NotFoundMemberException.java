package com.springboot.cms.exception;

public class NotFoundMemberException extends CommonException {
    public NotFoundMemberException(Integer id) {
        super("Not found member with id: " + id);
    }

}
