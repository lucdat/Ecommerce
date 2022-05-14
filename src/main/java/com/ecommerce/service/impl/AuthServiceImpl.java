package com.ecommerce.service.impl;

import com.ecommerce.dto.domain.ForgetPassword;
import com.ecommerce.dto.domain.LoginRequest;
import com.ecommerce.dto.domain.OTP;
import com.ecommerce.dto.domain.PasswordDTO;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repositories.UserRepo;
import com.ecommerce.security.JwtUtils;
import com.ecommerce.service.AuthService;
import com.ecommerce.service.OTPService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final OTPService otpService;
    private final JavaMailSender emailSender;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Map<String,String>  login(LoginRequest loginRequest) {

        Map<String,String> response = new HashMap<>();
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User)authentication.getPrincipal();
            String access_token = jwtUtils.generateToken(user);
            response.put("access_token",access_token);


        }catch (Exception ex){
            response.put("message","Username or password not correct !");
        }
        return response;
    }

    @Override
    public Map<String, String> forgetPassword(ForgetPassword forgetPassword) {
        Map<String,String> response = new HashMap<>();
        try{
            com.ecommerce.domain.User user = userRepo.findByUsername(forgetPassword.getUsername());
            if(user == null) throw new ResourceNotFoundException(String.format("%s not found !",forgetPassword.getUsername()));
            long otp = otpService.generateOTP(forgetPassword.getUsername());

            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user.getEmail());
                message.setSubject("OTP");
                message.setText("OTP : " + otp );
                emailSender.send(message);
                response.put("message","Your OTP has been sent to your email");
            }catch (Exception ex){
                log.error(ex.getMessage());
                response.put("message","Send mail error!");
            }
        }catch (Exception ex){
            response.put("message",String.format("%s user not found!",forgetPassword.getUsername()));
        }
        return response;
    }

    @Override
    public Map<String, String> enterOTP(OTP otp) {
        Map<String,String> response = new HashMap<>();
        OTP value = otpService.getOTP(otp.getUsername());
        if(value == null)  response.put("message","OTP not available");
        else if(otp.getOtp().equals(value.getOtp())){
            Random random = new Random();
            Integer password = random.nextInt(900000);
            com.ecommerce.domain.User user = userRepo.findByUsername(otp.getUsername());
            user.setPassword(passwordEncoder.encode(password+""));
            userRepo.save(user);
            response.put("password",password+"");
        }else response.put("message","OTP not correct!");
        return response;
    }

    @Override
    public Map<String, String> changePassword(PasswordDTO passwordDTO) {
        Map<String,String> response = new HashMap<>();
        com.ecommerce.domain.User user = userRepo.findByUsername(passwordDTO.getUsername());
        if(user==null) throw new ResourceNotFoundException(String.format("%s not found",passwordDTO.getUsername()));
        if(!passwordEncoder.matches(passwordDTO.getPasswordOld(), user.getPassword())) response.put("message","Password old not valid");
        if(!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword()))response.put("message","Password not valid");
        user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
        userRepo.save(user);
        response.put("message","success");
        return response;
    }
}
