/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.stackoverflowwebsocket;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 *
 * @author dcaprani
 */
/**
 * ChatServer Client
 * 
 * @author Jiji_Sasidharan
 */
@ClientEndpoint
public class ChatClientEndpoint {
    Session userSession = null;
    private MessageHandler messageHandler;
    
    private WebSocketContainer container;
    private final String name;
    //JSONParser parser = new JSONParser();
    //private static final AtomicLong received = new AtomicLong(0);
    
 

    
    public ChatClientEndpoint(URI endpointURI, String username) {
        try {
            container = ContainerProvider
                    .getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.name = username;
    }

    public ChatClientEndpoint(URI endpointURI) {
        try {
            container = ContainerProvider
                    .getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.name = null;
    }
   
    /**
     * Callback hook for Connection open events.
     * 
     * @param userSession
     *            the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
        //members.add(this);
    }
    
    /**
     * Callback hook for Connection close events.
     * 
     * @param userSession
     *            the userSession which is getting closed.
     * @param reason
     *            the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        this.userSession = null;
    }
 
    /**
     * Callback hook for Message Events. This method will be invoked when a
     * client send a message.
     * 
     * @param message
     *            The text message
     */
    @OnMessage
    public void onMessage(String message) {
        //JsonReader jsr = Json.createReader(new StringReader(message));
        //JsonObject jso = jsr.readObject();
        if (this.messageHandler != null){
            this.messageHandler.handleMessage(message);            
        }
        //if(!jso.getString("type").equalsIgnoreCase("auth")){
            ChatBot.received.incrementAndGet();
            //OlderChatBot.received.incrementAndGet();
        //}
    }
    
    /*public AtomicLong getReceived(){
        return received;
    }*/
 
    /**
     * register message handler
     * 
     * @param message
     */
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }
 
    /**
     * Send a message.
     * 
     * @param user
     * @param message
     */
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }
 
    /**
     * Message handler.
     * 
     * @author Jiji_Sasidharan
     */
    public static interface MessageHandler {
        public void handleMessage(String message);
    }
    
    public void disconnect() throws IOException
    {
       this.userSession.close();
    }
    
    public void send(String message) throws IOException
    {
       JsonObject value = Json.createObjectBuilder()
               .add("name", name)
               .add("message",message)
               .add("type","chat")//added type to be compatible with ReactPHP
               .build();
       
        sendMessage(value.toString());
        //sent.incrementAndGet();
    }
}
