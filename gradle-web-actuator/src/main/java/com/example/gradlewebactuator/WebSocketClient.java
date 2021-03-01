package com.example.gradlewebactuator;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class WebSocketClient {

    @GetMapping("/push")
    public void pushMessage(){
        String URL = "ws://localhost:8080/hello";

        org.springframework.web.socket.client.WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        stompClient.connect(URL, sessionHandler);

        //new Scanner(System.in).nextLine();
    }

    //public static void main(String arg[]){
    @GetMapping("/push2")
    public void push2(String val){

        try{

            org.springframework.web.socket.client.WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
            List<Transport> transports = new ArrayList<>(1);
            transports.add(new WebSocketTransport(simpleWebSocketClient));
            SockJsClient sockJsClient = new SockJsClient(transports);

            WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
            stompClient.setMessageConverter(new MappingJackson2MessageConverter());

            //String url = "ws://localhost:8080/app/hello";
            //String url = "ws://localhost:8080/";
            String url = "http://localhost:8080/ws";
            StompSessionHandler sessionHandler = new MyStompSessionHandler();
            StompSession session = null;
            try {
                session = stompClient.connect(url, sessionHandler).get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //ClientMessage msg = new ClientMessage(userId, line);
            HelloMessage message = new HelloMessage(val);
            Thread.sleep(3000);
            session.send("/app/hello", message);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
