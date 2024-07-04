package com.springboot.cms.service;


import com.springboot.cms.dto.JwtDto;
import com.springboot.cms.dto.UserDTO;
import com.springboot.cms.utils.ResponseUser;

public interface UserService {
    ResponseUser register(UserDTO user);
    JwtDto login(UserDTO user);
}
