package net.javaguides.springboot.websocket.controller;

import net.javaguides.springboot.websocket.model.Greeting;
import net.javaguides.springboot.websocket.model.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @Autowired
    RestTemplate restTemplate;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Pushing message on topic - " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }


    @PostMapping(path = "/refresh", consumes = "application/json")
    public ResponseEntity callRefresh(@RequestBody Object member){
        System.out.print("********* Call WS refresh");
        //final String url = "http://localhost:8380/actuator/refresh";
        final String url = "http://ec2-15-207-85-19.ap-south-1.compute.amazonaws.com:8380/actuator/refresh";

        restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<String>("", headers);
        //String personResultAsJsonStr = restTemplate.postForObject(url, request, String.class);

        restTemplate.postForObject(url, request, String.class);
        return new ResponseEntity(HttpStatus.OK);

    }
}
