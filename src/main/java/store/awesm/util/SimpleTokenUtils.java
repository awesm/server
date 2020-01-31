package store.awesm.util;

import org.apache.tomcat.util.codec.binary.Base64;

import java.util.Random;

/**
 *
 * @author zhishangli
 * @time 11:54 2020-01-24
 */
public class SimpleTokenUtils {

    /**
     * 生成Token
     * Token : MTU4MDY4NDk5ODc4MQ==
     */
    public static String makeToken(){
        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
        return Base64.encodeBase64String(token.getBytes());
    }

}
