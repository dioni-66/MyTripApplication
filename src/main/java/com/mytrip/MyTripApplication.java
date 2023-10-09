package com.mytrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyTripApplication {

    /** SECURITY */
    private static final String SECURITY = "security";

    public static void main(String[] args) {
        final SpringApplication springApp = new SpringApplication(MyTripApplication.class);
        // always have security profile activated
        springApp.setAdditionalProfiles(MyTripApplication.SECURITY);
        springApp.run(args);
    }

}
