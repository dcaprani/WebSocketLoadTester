/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.stackoverflowwebsocket;

import java.time.LocalDateTime;

/**
 *
 * @author dcaprani
 */
public class TestResult {
    private String srvrName;
    private LocalDateTime date;
    private int clients;
    private int clientsCnctd;
    private double cnxPcnt;
    private long cnxTime;
    private long msgsTx;
    private long msgsRx;
    private double rxPcnt;
    private long txRxTime;

    public TestResult() {
    }
    
    

    public TestResult(String srvrName, LocalDateTime date, int clients, int clientsCnctd,long cnxTime, long msgsTx, long msgsRx, long txRxTime) {
        this.srvrName = srvrName;
        this.date = date;
        this.clients = clients;
        this.clientsCnctd = clientsCnctd;
        this.cnxPcnt = clientsCnctd/clients;
        this.cnxTime = cnxTime;
        this.msgsTx = msgsTx;
        this.msgsRx = msgsRx;
        this.rxPcnt = msgsRx/(clients*msgsTx);
        this.txRxTime = txRxTime;
    }
    

    public String getSrvrName() {
        return srvrName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getClients() {
        return clients;
    }

    public int getClientsCnctd() {
        return clientsCnctd;
    }

    public double getCnxPcnt() {
        return cnxPcnt;
    }

    public long getCnxTime() {
        return cnxTime;
    }

    public long getMsgsTx() {
        return msgsTx;
    }

    public long getMsgsRx() {
        return msgsRx;
    }

    public double getRxPcnt() {
        return rxPcnt;
    }

    public long getTxRxTime() {
        return txRxTime;
    }
    
    public String printResult(){
        return srvrName + "\t" + date + "\t" + clients + "\t" + 
                clientsCnctd + "\t" + cnxPcnt + "\t" + 
                cnxTime + "\t" + msgsTx + "\t" + msgsRx + "\t" + 
                rxPcnt + "\t" + txRxTime;
    }
    
}
