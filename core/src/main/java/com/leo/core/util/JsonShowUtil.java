package com.leo.core.util;

public class JsonShowUtil {

    public static String getShowJson(String json) {
        if (!TextUtils.isEmpty(json)
                && ((json.startsWith("{") && json.endsWith("}"))
                || (json.startsWith("[") && json.endsWith("]")))) {
            int tab = 0;
            Boolean is = null;
            Boolean ts = null;
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < json.length(); i++) {
                char c = json.charAt(i);
                switch (c) {
                    case ':':
                        if(!(ts != null && ts)){
                            ts = false;
                        }
                        break;
                    case '\\':
                        is = false;
                        break;
                    case '"':
                        if (is != null) {
                            if (!is) {
                                is = true;
                            } else {
                                is = null;
                            }
                        }
                        if (ts != null) {
                            if (!ts) {
                                ts = true;
                            } else {
                                ts = null;
                            }
                        }
                        break;
                    default:
                        if (is != null && !is) {
                            is = null;
                        }
                        if (ts != null && !ts) {
                            ts = null;
                        }
                        break;
                }
                if ((is != null && is) || (ts != null && ts)) {
                    buffer.append(c);
                } else {
                    switch (c) {
                        case '[':
                        case '{':
                            tab(buffer, ++tab, c, 2);
                            break;
                        case '}':
                        case ']':
                            tab(buffer, --tab, c, 1);
                            break;
                        case ',':
                            tab(buffer, tab, c, 2);
                            break;
                        case ':':
                            buffer.append(": ");
                            break;
                        default:
                            tab(buffer, 0, c, 0);
                            break;
                    }
                }
            }
            String show = buffer.toString();
            show = show.replaceAll("\\\\/", "/");
            show = show.replaceAll(": //", "://");
            show = show.replaceAll("\"\\{\\s+", "\"{");
            show = show.replaceAll("\\s+\\}\"", "}\"");
            return show;
        }
        return json;
    }

    private static void tab(StringBuffer buffer, int tab, char c, int ln) {
        String sn = "\n";
        String st = "  ";
        switch (ln) {
            case 1:
                buffer.append(sn);
                for (int j = 0; j < tab; j++) {
                    buffer.append(st);
                }
                buffer.append(c);
                break;
            case 2:
                buffer.append(c).append(sn);
                for (int j = 0; j < tab; j++) {
                    buffer.append(st);
                }
                break;
            default:
                buffer.append(c);
                break;
        }
    }

}
