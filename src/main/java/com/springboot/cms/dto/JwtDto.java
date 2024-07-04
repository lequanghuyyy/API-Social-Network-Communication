package com.springboot.cms.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class JwtDto {
    private String accessToken;
    private Date expiredIn;
}

