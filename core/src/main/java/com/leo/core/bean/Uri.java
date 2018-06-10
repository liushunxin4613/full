package com.leo.core.bean;

import com.leo.core.util.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Uri {

    private String start;
    private String content;
    private String end;

    public Uri(String text) {
        if (!TextUtils.isEmpty(text)) {
            Matcher m = Pattern.compile("[htps]+://[1-9\\.]+/").matcher(text);
            if (m.find() && !TextUtils.isEmpty(m.group())) {
                setStart(m.group());
                text = text.substring(text.indexOf(m.group()) + m.group().length());
                int last = text.lastIndexOf("/");
                if (last >= 0) {
                    setContent(text.substring(0, last + 1));
                    setEnd(text.substring(last + 1, text.length()));
                }
            }
        }
    }

    public boolean check() {
        return !TextUtils.isEmpty(getStart())
                && !TextUtils.isEmpty(getContent())
                && !TextUtils.isEmpty(getEnd());
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

}
