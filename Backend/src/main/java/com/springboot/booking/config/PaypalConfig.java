package com.springboot.booking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalConfig {
    private String clientId;
    private String secret;
    private String mode;
}
