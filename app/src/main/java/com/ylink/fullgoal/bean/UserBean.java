package com.ylink.fullgoal.bean;

import com.leo.core.util.TextUtils;

import java.util.Map;

import static com.ylink.fullgoal.config.Config.CASTGC;

/**
 * 用户管理
 */
public class UserBean {

    private String name;
    private String cookie;
    private String userId;
    private String username;
    private String cookieStr;
    private String portalPac;

    public UserBean(String name, String cookie, String userId, String username, String cookieStr,
                    String portalPac) {
        this.name = name;
        this.cookie = cookie;
        this.userId = userId;
        this.username = username;
        this.cookieStr = cookieStr;
        this.portalPac = portalPac;
    }

    public String getCastgc(){
        if (!TextUtils.isEmpty(cookieStr)) {
            Map<String, String> map = TextUtils.parse(cookieStr);
            if (!TextUtils.isEmpty(map)) {
                return map.get(CASTGC);
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCookieStr() {
        return cookieStr;
    }

    public void setCookieStr(String cookieStr) {
        this.cookieStr = cookieStr;
    }

    public String getPortalPac() {
        return portalPac;
    }

    public void setPortalPac(String portalPac) {
        this.portalPac = portalPac;
    }

}