package com.ecommerce.api;


import com.ecommerce.dto.domain.ForgetPassword;
import com.ecommerce.dto.domain.LoginRequest;
import com.ecommerce.dto.domain.OTP;
import com.ecommerce.dto.domain.PasswordDTO;
import com.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthResource {
    private final AuthService authService;

    @PostMapping(value = "login",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> login(@Valid @RequestBody LoginRequest loginRequest){
            return ResponseEntity.ok().body(authService.login(loginRequest));
    }
    @PostMapping(value = "forgetPassword",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> forgetPassword(@Valid @RequestBody ForgetPassword forgetPassword){
        return ResponseEntity.ok().body(authService.forgetPassword(forgetPassword));
    }
    @PostMapping(value = "enterOTP",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> enterOTP(@Valid @RequestBody OTP otp){
        return ResponseEntity.ok().body(authService.enterOTP(otp));
    }

    @PostMapping(value = "changePassword",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> changePassword(@Valid @RequestBody PasswordDTO passwordDTO){
        return ResponseEntity.ok().body(authService.changePassword(passwordDTO));
    }
}
