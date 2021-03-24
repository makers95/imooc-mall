package com.imooc.mall.util;

import com.imooc.mall.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description MD5工具
 * @Param $
 * @return $
 **/
public class MD5Utils {
    public static String getMD5Str(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        return  Base64.encodeBase64String(md5.digest((strValue+Constant.salt).getBytes(StandardCharsets.UTF_8)));

    }

    public static void main(String[] args)  {
        String md5Str = null;
        try {
            md5Str = getMD5Str("1234");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(md5Str);
    }
}
