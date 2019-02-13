package com.modishou;

/**
 * @Author: modishou
 * @Date: 2019/2/12 11:46
 */
public class MyThread extends Thread {

    @Override
    public void run() {

        System.out.println("线程 " + Thread.currentThread().getName() + " 在帮我干活");
    }
}
