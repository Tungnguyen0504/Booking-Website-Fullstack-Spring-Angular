package com.springboot.booking.config;

import org.springframework.context.annotation.*;
import vn.library.common.config.FeignClientBasicAuthConfig;
import vn.library.common.config.FeignClientLoginConfig;
import vn.library.common.config.FeignClientTokenConfig;

@Configuration
@Lazy(false)
@ComponentScan(
    value = {"vn.library.common", "com.springboot.booking"},
    excludeFilters = {
      @ComponentScan.Filter(
          type = FilterType.ASSIGNABLE_TYPE,
          value = FeignClientBasicAuthConfig.class),
      @ComponentScan.Filter(
          type = FilterType.ASSIGNABLE_TYPE,
          value = FeignClientTokenConfig.class),
      @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = FeignClientLoginConfig.class)
    })
@PropertySource({"classpath:application.yml", "classpath:common-application.properties"})
public class ComponentConfig {}
