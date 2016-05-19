/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.stackoverflowwebsocket;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This class  is used to create the required number of clients for the load testing operation
 * @author dcaprani
 */
public class BotLoadClient {
    //Declare data members
    private static final AtomicLong sent = new AtomicLong(0);// used to increment the number of messages sent
    private static final AtomicLong received = new AtomicLong(0);// used to increment the number of messages recieved
    private static final Set<ChatBot> members = new CopyOnWriteArraySet<ChatBot>();//declare collection of ChatBots named members
    private final ChatClientEndpoint connection; // declare instance of ChatClientEndpoint
    private final String name;

    public BotLoadClient(ChatClientEndpoint connection, String name) {
        this.connection = connection;
        this.name = name;
    }
    
       
}
