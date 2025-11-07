package vn.spring.booking.config;

import org.springframework.context.annotation.*;
import vn.spring.common.config.FeignClientBasicAuthConfig;
import vn.spring.common.config.FeignClientLoginConfig;
import vn.spring.common.config.FeignClientTokenConfig;

@Configuration
@Lazy(false)
@ComponentScan(
    value = {"vn.spring.common", "vn.spring.booking"},
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
