package org.shine.common.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClient {

    private static Logger logger = Logger.getLogger(SocketClient.class.getName());
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private static int TIMEOUT = 10000;

    public SocketClient(String ip, int port) {
        try {
            socket = new Socket(InetAddress.getByName(ip), port);
            socket.setSoTimeout(TIMEOUT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            System.err.println("connect error");
            logger.log(Level.SEVERE, "连接异常 " + ip + ":" + port + "{0}", e);
        }
    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "关闭异常 {0}", e);
        }
    }

    public String excute(String queryString) {
        String xml = null;
        try {
            byte[] qsbyte = queryString.getBytes("GBK");
            int len = qsbyte.length + 4;
            int head = 0xFFFFEEEE;
            //int cmdtype = 0x00000000;
            int cmdtype = 0x0000000;
            //int cmdtype = 0x0000006; // 表示融合
            //int cmdtype = 0x0000001; // 表示匹配
            //int addrsplit = 0x01;
            byte[] qbyte = ByteUtil.intToByte4uint32litt(head);
            qbyte = ByteUtil.MergeBytes(qbyte, ByteUtil.intToByte4uint32litt(len));
            qbyte = ByteUtil.MergeBytes(qbyte, ByteUtil.intToByte4uint32litt(cmdtype));
           // qbyte = ByteUtil.MergeBytes(qbyte, ByteUtil.intToByte4uint32litt(addrsplit));
            qbyte = ByteUtil.MergeBytes(qbyte, qsbyte);
            out.write(qbyte);
            out.flush();

            byte[] rheadb = new byte[4];
            in.read(rheadb);
            byte[] rlenb = new byte[4];
            in.read(rlenb);
            int rlen = ByteUtil.bytesToInt4uint32litte(rlenb);
            System.out.println(rlen);
            
            boolean b = true;
            byte[] rdata = new byte[0];
            int leng = 0;
            while (b) {
                byte[] temp = new byte[in.available()];
                int available = temp.length;
                if (available > 0) {
                    leng = leng + available;
                    in.read(temp);
                    rdata = ByteUtil.MergeBytes(rdata, temp);
                } else {
                    if (leng >= rlen) {
                        b = false;
                    }
                }
            }
            xml = new String(rdata, "GBK");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "{0}", ex);
        }
        return xml;
    }

        public String excute2(String queryString) {
        String xml = null;
        try {
            byte[] qsbyte = queryString.getBytes("GBK");
            int len = qsbyte.length + 4;
            int head = 0xFFFFEEEE;
            //int cmdtype = 0x00000000;
            //int cmdtype = 0x0000000;
            int cmdtype = 5;
            //int addrsplit = 0x01;
            byte[] qbyte = ByteUtil.intToByte4uint32litt(head);
            qbyte = ByteUtil.MergeBytes(qbyte, ByteUtil.intToByte4uint32litt(len));
            qbyte = ByteUtil.MergeBytes(qbyte, ByteUtil.intToByte4uint32litt(cmdtype));
            //qbyte = ByteUtil.MergeBytes(qbyte, ByteUtil.intToByte4uint32litt(addrsplit));
            qbyte = ByteUtil.MergeBytes(qbyte, qsbyte);
            out.write(qbyte);
            out.flush();

            byte[] rheadb = new byte[4];
            in.read(rheadb);
            byte[] rlenb = new byte[4];
            in.read(rlenb);
            int rlen = ByteUtil.bytesToInt4uint32litte(rlenb);

            boolean b = true;
            byte[] rdata = new byte[0];
            int leng = 0;
            while (b) {
                byte[] temp = new byte[in.available()];
                int available = temp.length;
                if (available > 0) {
                    leng = leng + available;
                    in.read(temp);
                    rdata = ByteUtil.MergeBytes(rdata, temp);
                } else {
                    if (leng >= rlen) {
                        b = false;
                    }
                }
            }
            xml = new String(rdata, "UTF-8");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "{0}", ex);
        }
        return xml;
    }
        
    public static String getSocketInfo(String engine_url, String query_string) {
        if (engine_url.startsWith("http://")) {
            engine_url = engine_url.substring(7);
        }
        logger.info("engine_url:" + engine_url);
        logger.info("query_string:" + query_string);
        String[] url_port = engine_url.split(":");
        SocketClient sc = new SocketClient(url_port[0], Integer.parseInt(url_port[1]));
        query_string = query_string.replace("=", ":").replace("&", "\n");
        try {
            query_string = java.net.URLDecoder.decode(query_string, "GBK");
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.SEVERE, "编码异常{0}", e);
        }
        // System.out.println(query_string + "\n");
        String xml = sc.excute(query_string);
        logger.log(Level.ALL, xml);
        sc.close();
        return xml;
    }
}
