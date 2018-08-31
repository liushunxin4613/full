package com.ylink.fullgoal.db.table;

import com.leo.core.util.MD5Util;
import com.leo.core.util.TextUtils;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ylink.fullgoal.db.core.AppDatabase;
import com.ylink.fullgoal.db.core.DBModel;

import java.util.Date;

@Table(database = AppDatabase.class)
public class Text extends DBModel {

    public final static String TYPE_VIEW_CONFIG = "viewConfig";
    private final static String TYPE_VIEW_JSON = "viewJson";

    public static void savViewJson(String path, String hashCode, String text) {
        sav(path, TYPE_VIEW_JSON, hashCode, text);
    }

    public static void sav(String path, String type, String hashCode, String text) {
        if (TextUtils.check(path, type, text) && path.contains(".")
                && !path.endsWith(".")) {
            SQLite.delete().from(Text.class)
                    .where(ops(Text.class, m -> m.put("path", path)))
                    .execute();
            if(TextUtils.isEmpty(hashCode)){
                hashCode = MD5Util.getStringMD5(text);
            }
            int index = path.lastIndexOf(".");
            Text t = new Text();
            t.setName(path.substring(0, index));
            t.setSuffix(path.substring(index + 1));
            t.setPath(path);
            t.setType(type);
            t.setHashCode(hashCode);
            t.setText(text);
            t.setDate(new Date());
            t.save();
        }
    }

    public static String queryViewJson(String name) {
        return queryJson(name, TYPE_VIEW_JSON);
    }

    public static String queryJson(String name, String type) {
        if (TextUtils.check(name, type)) {
            Text text = SQLite.select().from(Text.class)
                    .where(ops(Text.class, m -> m.put("type", type)
                            .put("path", String.format("%s.json", name))))
                    .querySingle();
            return text == null ? null : text.getText();
        }
        return null;
    }

    public static String hashcode(String path) {
        if (TextUtils.check(path)) {
            Text t = SQLite.select().from(Text.class)
                    .where(ops(Text.class, m -> m.put("path", path)))
                    .querySingle();
            return t == null || TextUtils.isEmpty(t.getText()) ? null : t.getHashCode();
        }
        return null;
    }

    @PrimaryKey(autoincrement = true)
    private transient long id;
    @Column
    private String name;
    @Column
    private String suffix;
    @Column
    private String path;
    @Column
    private String type;
    @Column
    private String hashCode;
    @Column
    private String text;
    @Column
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

}