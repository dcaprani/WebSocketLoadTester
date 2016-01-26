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
 *
 * @author dcaprani
 */
public class BotLoadClient {
        private static final AtomicLong sent = new AtomicLong(0);
    private static final AtomicLong received = new AtomicLong(0);
    private static final Set<ChatBot> members = new CopyOnWriteArraySet<ChatBot>();
    private final ChatClientEndpoint connection;
    private final String name;

    public BotLoadClient(ChatClientEndpoint connection, String name) {
        this.connection = connection;
        this.name = name;
    }
    
       
}
