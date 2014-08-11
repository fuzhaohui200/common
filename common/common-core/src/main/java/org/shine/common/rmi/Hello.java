package org.shine.common.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 扩展了UnicastRemoteObject类，并实现远程接口 HelloInterface
 */
public class Hello extends UnicastRemoteObject implements HelloInterface {
    /*
     * 属性名称及作用描述：
     */
    private static final long serialVersionUID = 5738838020356449933L;
    private String message;

    /**
     * 必须定义构造方法，即使是默认构造方法，也必须把它明确地写出来，因为它必须抛出出RemoteException异常
     */
    public Hello(String msg) throws RemoteException {
        message = msg;
    }

    /**
     * 远程接口方法的实现
     */
    public String say() throws RemoteException {
        System.out.println("Called by HelloClient");
        return message;
    }
}
