package org.shine.common.utils;

import java.util.logging.Logger;

public class ByteUtil {

    private static Logger log = Logger.getLogger(ByteUtil.class.getName());

    public static void printByte(byte b, boolean f) {
        for (int i = 7; i >= 0; i--) {
            int shiftleft = (b >> i) & 0x01;
            log.info(String.valueOf(shiftleft));
        }
        if (f) {
            log.info("\n");
        }
    }

    public static void printInt(int b) {
        for (int i = 31; i >= 0; i--) {
            int shiftleft = (b >> i) & 0x01;
            log.info(String.valueOf(shiftleft));
        }
        log.info("\n");
    }

    /**
     *
     * @param b
     * @param firstNumber
     * @param NumberLength
     * @return
     */
    public static String byteToString(byte b[], int firstNumber, int NumberLength, String encode) {
        try {
            return new String(b, firstNumber, NumberLength, encode);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static byte[] intToByte(int i) {
        byte[] abyte0 = new byte[2];
        abyte0[1] = (byte) (0xff & i);
        abyte0[0] = (byte) ((0xff00 & i) >> 8);
        return abyte0;
    }

    // bit big-endian
    public static int bytesToInt(byte[] bytes) {
        int addr = bytes[1] & 0xFF;
        addr |= ((bytes[0] << 8) & 0xFF00);
        return addr;
    }

    // bit big-endian
    public static byte[] intToByte4uint32(int i) {
        byte[] abyte0 = new byte[4];
        abyte0[3] = (byte) (0xff & i);
        abyte0[2] = (byte) ((0xff00 & i) >> 8);
        abyte0[1] = (byte) ((0xff0000 & i) >> 16);
        abyte0[0] = (byte) ((0xff000000 & i) >> 24);
        return abyte0;
    }

    public static byte[] intToByte4uint32litt(int i) {
        byte[] abyte0 = new byte[4];
        abyte0[0] = (byte) (0xff & i);
        abyte0[1] = (byte) ((0xff00 & i) >> 8);
        abyte0[2] = (byte) ((0xff0000 & i) >> 16);
        abyte0[3] = (byte) ((0xff000000 & i) >> 24);
        return abyte0;
    }

    // bit big-endian
    public static int bytesToInt4uint32(byte[] bytes) {
        int addr = bytes[3] & 0xFF;
        addr |= ((bytes[2] << 8) & 0xFF00);
        addr |= ((bytes[1] << 16) & 0xFF0000);
        addr |= ((bytes[0] << 24) & 0xFF000000);
        return addr;
    }

    public static int bytesToInt4uint32litte(byte[] bytes) {
        int addr = bytes[0] & 0xFF;
        addr |= ((bytes[1] << 8) & 0xFF00);
        addr |= ((bytes[2] << 16) & 0xFF0000);
        addr |= ((bytes[3] << 24) & 0xFF000000);
        return addr;
    }

    public static byte[] getNewByte(byte[] old, int start, int len) {
        byte[] b = new byte[len];
        //int k = 1;
        for (int i = 0; i < b.length; i++) {
            b[i] = old[start + i];
            // System.out.println(k);
            //k++;
        }
        return b;
    }

    public static byte[] MergeBytes(byte[] pByteA, byte[] pByteB) {
        int aCount = pByteA.length;
        int bCount = pByteB.length;
        // System.out.println(aCount+","+bCount);
        byte[] b = new byte[aCount + bCount];
        for (int i = 0; i < aCount; i++) {
            b[i] = pByteA[i];
        }
        for (int i = 0; i < bCount; i++) {
            b[aCount + i] = pByteB[i];
        }
        // System.out.println(b.length);
        return b;
    }

    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
}
