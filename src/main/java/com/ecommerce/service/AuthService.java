package com.ecommerce.service;

import com.ecommerce.dto.domain.ForgetPassword;
import com.ecommerce.dto.domain.LoginRequest;
import com.ecommerce.dto.domain.OTP;
import com.ecommerce.dto.domain.PasswordDTO;

import java.util.Map;

public interface AuthService {
    Map<String,String> login(LoginRequest loginRequest);
    Map<String,String> forgetPassword(ForgetPassword forgetPassword);
    Map<String,String> enterOTP(OTP otp);
    Map<String,String> changePassword(PasswordDTO passwordDTO);
}
