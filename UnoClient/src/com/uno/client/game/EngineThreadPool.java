/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.client.game;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author 
 */
public class EngineThreadPool {
    
    private Executor executor = new ThreadPoolExecutor(10, 30, 100, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    
    private static EngineThreadPool instance = null;
    private EngineThreadPool() {
        
    }
    
    public synchronized static EngineThreadPool getInstance() {
        if (instance == null)
            instance = new EngineThreadPool();
        
        return instance;
    }
    
    public Executor getExecutor() {
        return executor;
    }
    
}
