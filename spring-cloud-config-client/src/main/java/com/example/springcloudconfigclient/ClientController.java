package com.example.springcloudconfigclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("app")
@RefreshScope
public class ClientController {


    @Value("${my.greeting}")
    private String val;

    @GetMapping("/greeting")
    public String getValues(){
        return val;
    }

}
