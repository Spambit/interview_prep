/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author sambit
 */

class CameraFrame {
    public void remove(){}
}

public class ThreadDemo {
    private boolean mIsRunning = false;
    private Thread mThread;
    private ArrayList<CameraFrame> mCameraFrames = new ArrayList();
    
    public static void test(){
        ThreadDemo demo = new ThreadDemo();
        System.out.println("Thread started.");
        demo.start();
        try{
            TimeUnit.SECONDS.sleep(1);
            demo.mIsRunning = false; 
            System.out.println("Stop requested.");
        }catch(Exception n){}
        
    }
    
    public void start() {
        mIsRunning = true;
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                runInternal();
            }
        });
        mThread.start();
    }

    private void runInternal() {
        do {
            //CameraFrame frame = mCameraFrames.remove(0);
            
            System.out.println("Run internal : "+System.nanoTime());
            //if (frame != null) {
                //listener.onCameraFrame(frame);
                //frame.release();
            //}
        } while (mIsRunning);
        System.out.println("Thread stopped.");
    }
}
