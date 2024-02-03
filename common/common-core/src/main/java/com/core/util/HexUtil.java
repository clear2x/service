package com.core.util;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.util.Arrays;

/**
 * @author yuangy
 * @create 2020-06-05 9:48
 */
public class HexUtil {

    public static String byteArrToHexStr(byte[] arr) {
        return new HexBinaryAdapter().marshal(arr).toUpperCase();
    }

    public static byte[] hexStrToByteArr(String hexStr) {
        return new HexBinaryAdapter().unmarshal(hexStr);
    }

    public static void main(String[] args) {
        byte[] arr = {-80, 11, 12, 15};
        System.out.println(byteArrToHexStr(arr));
        byte[] unmarshal = hexStrToByteArr(byteArrToHexStr(arr));
        System.out.println(Arrays.toString(unmarshal));
    }

}
