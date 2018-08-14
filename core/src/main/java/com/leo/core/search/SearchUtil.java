package com.leo.core.search;

import com.github.promeg.pinyinhelper.Pinyin;
import com.leo.core.util.TextUtils;

import java.util.regex.Pattern;

/**
 * 搜素工具
 */
public class SearchUtil {

    /**
     * 获取搜索数据
     * @param obj 对象
     * @return 搜索数据
     */
    public static String getSearch(Object obj) {
        return getIterableSearch(SearchFieldUtil.getFieldSet(obj));
    }

    /**
     * 获取搜索数据
     * @param iterables iterables
     * @return 搜索数据
     */
    public static String getIterableSearch(Iterable<String> iterables) {
        if(iterables != null){
            StringBuilder builder = new StringBuilder();
            for (String text : iterables) {
                if (!TextUtils.isEmpty(text)) {
                    if (builder.length() > 0) {
                        builder.append("┇");
                    }
                    builder.append(getSearch(text));
                }
            }
            return builder.toString();
        }
        return null;
    }

    /**
     * 获取搜索数据
     * @param args args
     * @return 搜索数据
     */
    public static String getArgsSearch(String[] args) {
        if(!TextUtils.isEmpty(args)){
            StringBuilder builder = new StringBuilder();
            for (String text : args) {
                if (!TextUtils.isEmpty(text)) {
                    if (builder.length() > 0) {
                        builder.append("┇");
                    }
                    builder.append(getSearch(text));
                }
            }
            return builder.toString();
        }
        return null;
    }

    /**
     * 获取搜索数据
     * @param text text
     * @return 搜索数据
     */
    public static String getSearch(String text) {
        if (!TextUtils.isEmpty(text)) {
            String str = text;
            StringBuilder a = new StringBuilder();
            StringBuilder b = new StringBuilder();
            for (char c : text.toCharArray()) {
                String s = Pinyin.toPinyin(c).toLowerCase();
                a.append(s);
                b.append(s.charAt(0));
            }
            String matcher = a.toString();
            if (!text.equals(matcher)) {
                str += "┇" + matcher;
            }
            matcher = b.toString();
            if (!text.equals(matcher)) {
                str += "┇" + matcher;
            }
            return str;
        }
        return null;
    }

    /**
     * 搜索String数据
     * @param text text
     * @param search search
     * @return 是否搜索成功
     */
    public static boolean searchString(String text, String search){
        return PPSearch(text, search);
    }

    /**
     * 搜索数据
     */
    public static boolean search(Object obj, String search) {
        return search(SearchFieldUtil.getFieldSet(obj), search);
    }

    /**
     * 搜索数据
     */
    public static boolean search(Iterable<String> iterables, String search) {
        if (TextUtils.check(iterables, search)) {
            for (String text : iterables) {
                if (search(text, search)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 搜索
     */
    public static boolean search(String text, String search) {
        return PPSearch(text, search) || JPSearch(text, search)
                || QPSearch(text, search);
    }

    /**
     * 普拼
     */
    private static boolean PPSearch(String text, String search) {
        return TextUtils.check(text, search) && Pattern
                .compile(search, Pattern.CASE_INSENSITIVE).matcher(text).find();
    }

    /**
     * 简拼
     */
    private static boolean JPSearch(String text, String search) {
        return TextUtils.check(text, search) && search.length() < 6
                && Pattern.compile(search, Pattern.CASE_INSENSITIVE)
                .matcher(FirstLetterUtil.getFirstLetter(text)).find();
    }

    /**
     * 全拼
     */
    private static boolean QPSearch(String text, String search) {
        return TextUtils.check(text, search) && Pattern
                .compile(search, Pattern.CASE_INSENSITIVE)
                .matcher(CharacterParserFactory.getInstance().getSelling(text))
                .find();
    }

}