package com.leo.core.config;

/**
 * 正则表达式通配符
 */
public class PatternConfig {

    /**
     * java选取type最简名称
     */
    public final static String JAVA_TYPE_SIMPLE = "[ \\.a-zA-z<]*\\.|>";

    public static String getPattern(String text, String pattern){
        if(text != null && pattern != null){
            return text.replaceAll(pattern, "");
        }
        return null;
    }

}
