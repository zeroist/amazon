package com.cydeo.utilities;

public class BrowserUtil {

    public static void waitFor(int second){
        try {
            Thread.sleep(second*1000);
        } catch (InterruptedException e) {
           e.printStackTrace();
        }
    }
}
