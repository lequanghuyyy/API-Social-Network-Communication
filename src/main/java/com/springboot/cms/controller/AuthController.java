package com.springboot.cms.controller;


import com.springboot.cms.dto.JwtDto;
import com.springboot.cms.dto.UserDTO;
import com.springboot.cms.service.UserService;
import com.springboot.cms.utils.ResponseUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    // Response: ResponseEntity<>
    @PostMapping("/signup")
    public ResponseEntity<ResponseUser> signup(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.register(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody UserDTO loginDTO) {
        return ResponseEntity.ok(userService.login(loginDTO));
    }
}
