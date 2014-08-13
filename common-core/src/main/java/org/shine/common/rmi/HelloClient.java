package org.shine.common.rmi;

import java.rmi.Naming;

public class HelloClient {
    /**
     * 查找远程对象并调用远程方法
     */
    public static void main(String[] argv) {
        try {
            // HelloInterface hello = (HelloInterface) Naming.lookup("Hello");

            // 如果要从另一台启动了RMI注册服务的机器上查找hello实例
            HelloInterface hello = (HelloInterface) Naming
                    .lookup("//localhost:1099/Hello");

            // 调用远程方法
            System.out.println(hello.say());
        } catch (Exception e) {
            System.out.println("HelloClient exception: " + e);
        }
    }
}
