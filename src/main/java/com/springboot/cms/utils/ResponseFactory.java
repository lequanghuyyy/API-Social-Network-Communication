package com.springboot.cms.utils;


import com.springboot.cms.constant.AppConstant;
import com.springboot.cms.dto.common.BaseResponse;
import org.springframework.http.ResponseEntity;

public class ResponseFactory {
    private ResponseFactory() {}

    public static <T> ResponseEntity<BaseResponse<T>> ok(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setData(data);
        response.setStatus(AppConstant.SUCCESS_CODE);
        response.setDescription("success");

        return ResponseEntity.ok(response);
    }
}
