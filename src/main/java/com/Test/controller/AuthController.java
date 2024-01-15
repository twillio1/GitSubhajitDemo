package com.Test.controller;

import com.Test.entity.Role;
import com.Test.entity.User;
import com.Test.payload.SigninDto;
import com.Test.payload.SignupDto;
import com.Test.repository.RoleRepository;
import com.Test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> validateUser(@RequestBody SigninDto signinDto)
    {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(signinDto.getUsernameOrEmail(), signinDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return new ResponseEntity<>("User Sign in successfully",HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody SignupDto signupDto)
    {
        if(userRepository.existsByUsername(signupDto.getUsername())){
            return new ResponseEntity<>("User already exists with username "+signupDto.getUsername(), HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByemail(signupDto.getEmail())){
            return new ResponseEntity<>("User already exists with email "+signupDto.getEmail(), HttpStatus.BAD_REQUEST);
        }

        User user=new User();

        user.setName(signupDto.getName());
        user.setEmail(signupDto.getEmail());
        user.setUsername(signupDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN").get();

        user.setRoles(Collections.singleton(role));

        userRepository.save(user);

        return new ResponseEntity<>("User Registered Successfully",HttpStatus.CREATED);

    }
}
