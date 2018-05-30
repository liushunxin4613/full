package com.leo.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class DecoderUtil {

    public static String decode(String text) {
        if (!TextUtils.isEmpty(text)) {
            try {
                return URLDecoder.decode(text, "utf-8");
            } catch (UnsupportedEncodingException ignored) {
            }
        }
        return text;
    }

}
