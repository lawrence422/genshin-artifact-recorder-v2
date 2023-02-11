package com.genshin.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;

public class RandomUtils {
    public static String createRandomString(){
        SecureRandom random=new SecureRandom();
        return new BigInteger(300,random).toString(50).toUpperCase();
    }
}
