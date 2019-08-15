package com.ets.nb_iot.cmdinfo.command.callback;

import java.util.concurrent.DelayQueue;


public class CallBackQueue {


    private static volatile DelayQueue<CallBackDelay> callBackQueue = new DelayQueue<CallBackDelay>();

    public static DelayQueue<CallBackDelay> getCallBackQueue() {
        return callBackQueue;
    }

    /*
    public static void setCallBackQueue(DelayQueue<CallBackDelay> callBackQueue) {
        CallBackQueue.callBackQueue = callBackQueue;
    }
    */

}
