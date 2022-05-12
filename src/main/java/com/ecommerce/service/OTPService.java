package com.ecommerce.service;

import com.ecommerce.dto.domain.OTP;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@SessionScope
public class OTPService {
    private static Map<String,OTP> OTP_VALUE = new HashMap<>(1);
    public int generateOTP(String username) {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        Long timeMillis = System.currentTimeMillis();
        Date date = new Date(timeMillis + 60*5*1000);
        OTP value = new OTP(date,otp,"");
        OTP_VALUE.put(username,value);
        return otp;
    }
    public OTP getOTP(String username) {
        return OTP_VALUE.get(username);
    }
    public boolean isValidOTP(OTP otp){
        Long timeMillis = System.currentTimeMillis();
        Date date = new Date(timeMillis);
        return otp.getDate().before(date);
    }
}
