package com.springboot.cms.service.impl;


import com.springboot.cms.dto.JwtDto;
import com.springboot.cms.dto.UserDTO;
import com.springboot.cms.entity.UserEntity;
import com.springboot.cms.exception.AlreadyExistsException;
import com.springboot.cms.exception.InvalidCredentialsException;
import com.springboot.cms.repository.RoleRepository;
import com.springboot.cms.repository.UserRepository;
import com.springboot.cms.security.CustomUserDetails;
import com.springboot.cms.security.JwtTokenProvider;
import com.springboot.cms.service.UserService;
import com.springboot.cms.utils.ResponseUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public ResponseUser register(UserDTO user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new AlreadyExistsException("User", "username", user.getUsername());
        }

      if (user.getUsername() == null || user.getUsername().isBlank()){
          throw new IllegalArgumentException("USERNAME IS NULL");
      }

        for (String role : user.getRoles()) {
            if (!role.equals("ADMIN") && !role.equals("USER")) {
                throw new IllegalArgumentException("Invalid role: " + role);
            }
        }

        UserEntity entity = new UserEntity();

        entity.setPassword(passwordEncoder.encode(user.getPassword()));
        entity.setUsername(user.getUsername());
        entity.setRoles(roleRepository.findAllByRoleNameIn(user.getRoles()));
        UserEntity savedUser = userRepository.save(entity);

        ResponseUser respUser = new ResponseUser();
        respUser.setUsername(user.getUsername());
        respUser.setId(savedUser.getId());
        respUser.setRoles(user.getRoles());
        return respUser;
    }

    public JwtDto login(UserDTO user) {
        try {
            // Perform authentication
            // 1. pass encoder hash password
            // 2. userDetail service
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            // Retrieve user details from the authenticated token
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            // Generate JWT token
            String accessToken = jwtTokenProvider.generateToken(userDetails);
            Date expriedDate = jwtTokenProvider.extractExpiration(accessToken);

            return JwtDto.builder()
                    .accessToken(accessToken)
                    .expiredIn(expriedDate)
                    .build();
        } catch (AuthenticationException e) {
            // Handle authentication failure
            log.error("Wrong username or password {}", e.getMessage(), e);
            throw new InvalidCredentialsException("Wrong username or password");
        }
    }

}
