package com.xwt.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 *@Author feri
 *@Date Created in 2019/3/19 14:30
 */
public class PassUtil {
    //base64编码 编码
    public static String base64Enc(String msg){
        BASE64Encoder encoder=new BASE64Encoder();
        return encoder.encode(msg.getBytes());
    }
    //base64编码 解码
    public static String base64Dec(String msg){
        BASE64Decoder decoder=new BASE64Decoder();
        try {
            return new String(decoder.decodeBuffer(msg));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    //base64编码 编码
    public static String base64Enc(String msg,String encoding){
       Base64.Encoder encoder= Base64.getEncoder();
        try {
            return encoder.encodeToString(msg.getBytes(encoding));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    //base64编码 解码
    public static String base64Dec(String msg,String  encoding){
       Base64.Decoder decoder=Base64.getDecoder();
        try {
            return new String(decoder.decode(msg),encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
