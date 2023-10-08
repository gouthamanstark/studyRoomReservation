package com.computerpool.library.serviceimpl;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomNumberGenerator {
    
    private static final int passwordLength=10;
    private static final String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int activationCodeLength=40;
    
    public static String generateRandomPassword() {
        
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(passwordLength);
        
        for (int i = 0; i <passwordLength ; i++) {
            int randomIndex = random.nextInt(ALPHANUMERIC_CHARACTERS.length());
            char randomChar = ALPHANUMERIC_CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    
}

public static String generateRandomActivationCode() {
        
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(activationCodeLength);
        
        for (int i = 0; i <activationCodeLength ; i++) {
            int randomIndex = random.nextInt(ALPHANUMERIC_CHARACTERS.length());
            char randomChar = ALPHANUMERIC_CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    
}

  public static long generateRandomNumber() {

        Random random = new Random();
        
        return random.nextInt(10000001,999999999);
    }

}