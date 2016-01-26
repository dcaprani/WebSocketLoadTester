/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.stackoverflowwebsocket;

import java.io.StringReader;
import java.net.URI;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author dcaprani
 */
/**
 * ChatBot
 * @author Jiji_Sasidharan (original)
 * @modifier Derek Caprani 03/01/2015
 * 
 */
public class OlderChatBot {
    
    private static final AtomicLong sent = new AtomicLong(0);
    public static final AtomicLong received = new AtomicLong(0);
    private static final Set<ChatClientEndpoint> members = new CopyOnWriteArraySet<ChatClientEndpoint>();
    private final ChatClientEndpoint connection;
    //private final String name;
    private  int port;
    private  int clients;
    private  static int messages;
    private  int idleTime;
    private  String host;
    private static long end;
    private static long start;
    private static long conxTime;
    private static long rxTime;
    private static int connections;
    
// Constructor
    
    public OlderChatBot(String username, String host, int port)
    throws Exception
    {
        this.connection = new ChatClientEndpoint(new URI("ws://"+host+":"+port+"/"));
        //this.name = username;
    }
    
    public OlderChatBot(int clients, int messages, int idleTime)
    throws Exception
    {
        this.connection = null;
        this.clients = clients;
        this.messages = messages;
        this.idleTime = idleTime;
    }

    public long getSent() {
        return sent.get();
    }

    public long getReceived() {
        return received.get();
    }

    public static Set<ChatClientEndpoint> getMembers() {
        return members;
    }

    public int getPort() {
        return port;
    }

    public int getClients() {
        return clients;
    }

    public int getMessages() {
        return messages;
    }

    public int getIdleTime() {
        return idleTime;
    }

    public String getHost() {
        return host;
    }
    
    public int getMembersSize(){
        return connections;
    }
    
    public long getConxTime(){
        return conxTime;
    }
    
    public long getRXTime(){
        return rxTime;
    }
    
    public void resetValues(){
        clients = 0;
        end = 0;
        start = 0;
        rxTime = 0;
        received.set(0);
        sent.set(0);
    }
          
    
    public static void main(String... arg) throws Exception {
//        ChatLoadClient code
//        String host=ChatBot.host;
//        int port=ChatBot.port;0
//        int clients=ChatBot.clients;
//        int mesgs=ChatBot.messages;
//        int idleTime = ChatBot.idleTime;
        String host=arg.length>0?arg[0]:"localhost";
        int port=arg.length>1?Integer.parseInt(arg[1]):8081;
        int clients=arg.length>2?Integer.parseInt(arg[2]):1000;
        int messages =arg.length>3?Integer.parseInt(arg[3]):400;
        int idleTime = 30000;
        
        // Create client serially
        start=System.currentTimeMillis();
        ChatClientEndpoint[] chat = new ChatClientEndpoint[clients];
        for (int i=0;i<chat.length;i++){
            chat[i]=new ChatClientEndpoint(new URI("ws://"+host+":"+port+"/"), "user " +i);
            members.add(chat[i]);
            connections++;
        }
        while(members.size()<clients)
        {
            if (System.currentTimeMillis()>(start+idleTime))
                break;
            Thread.sleep(10);
        }
        end=System.currentTimeMillis();
        conxTime = end - start;
        System.err.printf("Opened %d of %d connections to %s:%d in %dms\n",members.size(),clients,host,port,conxTime);
        
        // Send messages
        Random random = new Random();
        start = System.currentTimeMillis();
        //received.set(0);
        for (int i=0;i<messages;i++)
        {
            ChatClientEndpoint c = chat[random.nextInt(chat.length)];
            String msg = "Hello random "+random.nextLong();
            //c.send(msg);
            c.send(msg);
            sent.incrementAndGet();
        }

        long last=0;
        long progress=start;
        while(received.get()<(clients*messages))
        {
             
            if (System.currentTimeMillis()>(progress+idleTime))
                break;
            if (received.get()!=last)
            {
                progress=System.currentTimeMillis();
                last=received.get();
            }
            Thread.sleep(50);
        }     
        
        end=System.currentTimeMillis();
        rxTime = end - start;
        System.err.printf("Sent/Received %d/%d messages in %dms: %dmsg/s\n",sent.get(),received.get(),rxTime,(received.get()*1000)/rxTime);
        
        // Close all connections
        
        start=System.currentTimeMillis();
        for (int i=0;i<chat.length;i++){
            //chat[i].disconnect();
            chat[i].userSession.close();
            members.remove(chat[i]);
        }
        
        while(members.size()>0)
        {
            if (System.currentTimeMillis()>(start+idleTime))
                break;
            Thread.sleep(10);
        }
        end=System.currentTimeMillis();
        
        System.err.printf("Closed %d connections to %s:%d in %dms\n",clients,host,port,(end-start));
     
        /**********************************/

        /*while (true) {
            clientEndPoint.sendMessage(getMessage("Hi There!!"));
            Thread.sleep(30000);
        }*/
    }
 
    /**
     * Create a json representation.
     * 
     * @param message
     * @return
     */
    private static String getMessage(String message) {
        return Json.createObjectBuilder()
                       .add("name", "bot")
                       .add("message", message)
                   .build()
                   .toString();
    }
}
