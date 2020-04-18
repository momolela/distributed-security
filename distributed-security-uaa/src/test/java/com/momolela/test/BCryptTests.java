package com.momolela.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptTests {
    public static void main(String[] args) {
        System.out.println("=="+new BCryptPasswordEncoder().encode("secret")+"==");
    }
}
