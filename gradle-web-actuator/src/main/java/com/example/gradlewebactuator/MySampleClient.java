package com.example.gradlewebactuator;


import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.concurrent.ExecutionException;

public class MySampleClient extends TextWebSocketHandler {

    private WebSocketSession clientSession;

    public MySampleClient () throws ExecutionException, InterruptedException {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        //wss://echo.websocket.org
        //ws://localhost:8080
        this.clientSession = webSocketClient.doHandshake(this,
                new WebSocketHttpHeaders(), URI.create("ws://localhost:8080/")).get();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println(message.getPayload());
    }

    public WebSocketSession getClientSession(){
        return this.clientSession;
    }

    //@SneakyThrows
    public static void main(String[] args)  {
        try {
            MySampleClient sampleClient =  new MySampleClient();
            sampleClient.getClientSession().sendMessage(new TextMessage("Hello!"));
            //Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
