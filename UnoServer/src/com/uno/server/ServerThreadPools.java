/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.server;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author
 */
public class ServerThreadPools {
    
    private Executor responseExecutor = new ThreadPoolExecutor(10, 300, 100, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    
    private static ServerThreadPools instance = null;
    private ServerThreadPools() {
        
    }
    
    public synchronized static ServerThreadPools getInstance() {
        if (instance == null)
            instance = new ServerThreadPools();
        
        return instance;
    }
    
    public Executor getResponseExecutor() {
        return responseExecutor;
    }
    
}
