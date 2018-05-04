package com.leo.core.api;

import com.leo.core.iapi.IMD5Api;
import com.leo.core.util.TextUtils;

import java.security.MessageDigest;

public class MD5Api implements IMD5Api {

    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    @Override
    public String md5(String text) {
        if(!TextUtils.isEmpty(text)){
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(text.getBytes());
                return new String(encodeHex(messageDigest.digest()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public String mmd5(String text) {
        return md5(md5(text));
    }

    private char[] encodeHex(final byte[] data) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return out;
    }

}
