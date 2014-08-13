/**
 * Copyright (c) 2012-2013 AutoNavi. All rights reserved.  
 * common_project  2013-8-10 
 *
 * 相关描述： 
 *
 */
package org.shine.common.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * 相关描述：
 * <p/>
 * 文件名：FileUtil.java
 * 作者： Fu Zhaohui
 * 完成时间：2013-8-10 下午8:14:38
 * 维护人员：Fu Zhaohui
 * 维护时间：2013-8-10 下午8:14:38
 * 维护原因：
 * 当前版本： v1.0
 */
public class FileUtil {

    /**
     * @param args
     */
    public static char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static boolean fileMD5Validate(String fileName, String md5Validate) {

        return false;
    }

    public static String getHash(String fileName, String hashType)
            throws Exception {
        InputStream fis;
        fis = new FileInputStream(fileName);
        byte[] buffer = new byte[1024];
        MessageDigest md5 = MessageDigest.getInstance(hashType);
        int numRead = 0;
        while ((numRead = fis.read(buffer)) > 0) {
            md5.update(buffer, 0, numRead);
        }
        fis.close();
        return toHexString(md5.digest());
    }

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0x0f]);
        }
        return sb.toString();
    }
}
