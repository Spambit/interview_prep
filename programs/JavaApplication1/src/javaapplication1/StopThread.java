/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author sambit
 */
public class StopThread {
  private static boolean stopRequested;
 
  private static void requestStop() {
    stopRequested = true;
  }
 
  private static boolean stopRequested() {
    return stopRequested;
  }
 
  public static void start() {
    Thread backgroundThread = new Thread(new Runnable() {
      public void run() {
        int i = 0;
        while (!stopRequested()){
            i++;
            System.out.println(i);
        }
      }
    });
    backgroundThread.start();
    try{
        TimeUnit.SECONDS.sleep(1);
    }catch(Exception n){}
    requestStop();
    System.out.println("stop requested.");
  }
}


public class MyFrameStateListener implements FrameListener {
    public void onCameraFrame(CameraWorkerHandler handler,CameraFrame frame){
        if(handler.shouldShutdown()){
            new Thread(new Runnable(){
                public void run(){
                    handler.shutdown();
                }
            }).start();
        }
    }
}
