package org.shine.thread;

public class Monitor extends Thread {
    private int frequency;
    private volatile Thread listener;

    public Monitor(int frequency) {
        this.frequency = frequency * 1000;
    }

    public void start() {
        listener = new Thread(this);
        listener.start();
    }

    public void run() {
        Thread thisListener = Thread.currentThread();
        while (listener == thisListener) {
            try {
                System.out.println(System.currentTimeMillis());
                Thread.sleep(frequency);
            } catch (InterruptedException e) {
                throw new RuntimeException("Interrupted", e);
            }
        }
    }

    public void stopMonitor() {
        Thread tmpListener = listener;
        listener = null;
        if (tmpListener != null) {
            tmpListener.interrupt();
        }
    }

    public static void main(String[] args) {
        Monitor m = new Monitor(60);
        m.start();
    }
}