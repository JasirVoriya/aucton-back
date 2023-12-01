package cn.voriya.framework.security.token;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

/**
 * SignWithUtil
 *
 */
public class SecretKeyUtil {
    public static SecretKey generalKey() {
        //自定义
        byte[] encodedKey = Decoders.BASE64.decode("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=");
        return Keys.hmacShaKeyFor(encodedKey);
    }

    public static SecretKey generalKeyByDecoders() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ="));

    }
}
