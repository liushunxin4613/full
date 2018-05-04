package com.leo.core.util;

import android.annotation.SuppressLint;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MD5Util {

//    private static final String densityKey = "It*s_My-EncryKey>741";
    private static final String densityKey = "1234567";
    public static String encode(String string) throws Exception {
        byte[] hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
    /**
     *  MD5的Token
     * @return
     *          <p>model+action+时间戳+key做md5加密传递到服务器99-k ymd</p>
     */
    @CheckResult
    private static String getMD5OfToken(String model, String action) {
        if (TextUtils.isEmpty(model) || TextUtils.isEmpty(action)) {
            throw new IllegalArgumentException("paramter exception");
        }
        StringBuilder md5String = new StringBuilder();
        md5String.append(model);
        md5String.append(action);
        @SuppressLint("SimpleDateFormat")
        String timeTemplater = DateUtil.getFormatString(new Date(System.currentTimeMillis()), "yyMMdd");
        md5String.append(timeTemplater);
        md5String.append(densityKey);
        try {
            return MD5(md5String.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回一个带有 token 的 MAP
     */
    @CheckResult
    public static Map<String ,String> getDefaultMD5Params(@NonNull String model, @NonNull String action){
        Map<String, String> params = new HashMap<>();
        try {
            String token = getMD5OfToken(model, action);
            if (TextUtils.isEmpty(token)) return  null;
            params.put("api_token", token);
            return params;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String MD5(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return new String(encodeHex(messageDigest.digest()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String MMD5(String text) {
        if (!TextUtils.isNull(text)){
            return MD5(MD5(text));
        }
        return null;
    }

    private static char[] encodeHex(final byte[] data) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return out;
    }

}
