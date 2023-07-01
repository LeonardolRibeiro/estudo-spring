package com.leolaia.estudospring.controllers;

import com.leolaia.estudospring.data.vo.v1.security.AccountCredentialsVO;
import com.leolaia.estudospring.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Tag(name = "Authentication endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping(value = "/signin")
    @Operation(summary = "Authenticates a user and returns a token")
    public ResponseEntity signin(@RequestBody AccountCredentialsVO data){
        if (checkIfDataIsNotNull(data)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        }

        var token = authService.signin(data);
        if (token == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        }

        return token;
    }

    @PutMapping(value = "/refresh/{username}")
    @Operation(summary = "Refresh token for authenticated user and returns a token")
    public ResponseEntity refreshToken(@PathVariable("username") String username,
                                       @RequestHeader("Authorization") String refreshToken) {
        if (isBlank(username) || isBlank(refreshToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        var token = authService.refreshToken(username, refreshToken);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return token;
    }

    private static boolean checkIfDataIsNotNull(AccountCredentialsVO data) {
        return data == null || isBlank(data.getUserName()) || isBlank(data.getPassword());
    }


}
