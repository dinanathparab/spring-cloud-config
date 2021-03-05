package com.example.gradlewebactuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

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

    boolean refreshWS = false;

    @Autowired
    WebSocketClient wsClient;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void fireRefreshEvent() {
        eventPublisher.publishEvent(new RefreshEvent(this, "RefreshEvent", "Refreshing scope"));
    }

    @EventListener(RefreshScopeRefreshedEvent.class)
    public void onRefresh(RefreshScopeRefreshedEvent event) {
        System.out.print("********** in onRefresh Val = " + val);
        refreshWS = true;
        wsClient.push2(val);
    }



    @PostConstruct
    public void postConstruct(){
        System.out.print("********* in postConstruct Val = " + val);
        if( refreshWS ){
            System.out.print("********* Call WS refresh");
        }
    }

}
