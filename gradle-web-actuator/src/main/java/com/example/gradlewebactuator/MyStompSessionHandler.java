package com.example.gradlewebactuator;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import java.lang.reflect.Type;

public class MyStompSessionHandler implements StompSessionHandler {

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/topic/greetings", this);
        //session.send("/app/chat", getSampleMessage());
        session.send("/app/topic", "Test");
    }
    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        HelloMessage msg = (HelloMessage) payload;
        //System.out.print("Received : " + msg.getName()+ " from : " + msg.getFrom());
        System.out.print("Received : " + msg.getName() );
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {

    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {

    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return null;
    }


}
