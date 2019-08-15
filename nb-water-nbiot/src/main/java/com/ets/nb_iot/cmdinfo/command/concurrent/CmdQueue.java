package com.ets.nb_iot.cmdinfo.command.concurrent;

import java.util.concurrent.DelayQueue;

/**
 * @author 姚轶文
 * @create 2018- 12-11 10:09
 */
public class CmdQueue {


    private static volatile DelayQueue<CmdDelay> delayQueue = new DelayQueue<CmdDelay>();

    public static DelayQueue<CmdDelay> getDelayQueue() {
        return delayQueue;
    }

    public static void setDelayQueue(DelayQueue<CmdDelay> delayQueue) {
        CmdQueue.delayQueue = delayQueue;
    }
}
