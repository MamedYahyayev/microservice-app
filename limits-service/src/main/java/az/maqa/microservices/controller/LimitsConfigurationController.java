package az.maqa.microservices.controller;

import az.maqa.microservices.bean.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigurationController {

    @Value("${limits.service.minimum}")
    private int minimum;
    @Value("${limits.service.maximum}")
    private int maximum;


    @GetMapping("/limits")
    public LimitConfiguration retrieveLimitsFromConfiguration() {
        return new LimitConfiguration(maximum, minimum);
    }


    @GetMapping("/fault-tolerance-example")
    @HystrixCommand(fallbackMethod = "fallBackRetrieveConfiguration")
    public LimitConfiguration retrieveConfiguration(){
        throw new RuntimeException("Not available");
    }


    public LimitConfiguration fallBackRetrieveConfiguration(){
        return new LimitConfiguration(9, 999);
    }

}
