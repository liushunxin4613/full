package com.leo.core.search;

import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;

import java.util.regex.Pattern;

/**
 * 搜素工具
 */
public class SearchUtil {

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