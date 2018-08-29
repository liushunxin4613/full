package com.ylink.fullgoal.db.table;

import com.leo.core.util.TextUtils;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ylink.fullgoal.db.core.AppDatabase;
import com.ylink.fullgoal.db.core.DBModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 存储jsonFile数据
 */
@Table(database = AppDatabase.class, name = "JsonFile_v1")
public class JsonFile extends DBModel {

    public final static String CORE_JSON = "config/core.json";
    public final static String REPORT_JSON = "config/report.json";

    public static void clear(String root) {
        if (TextUtils.check(root)) {
            SQLite.delete().from(JsonFile.class)
                    .where(JsonFile_Table.root.eq(root))
                    .execute();
        }
    }

    public static void sav(String root, String node, String value, String parent, String spectrum) {
        if (TextUtils.check(root)) {
            JsonFile jsonFile = new JsonFile();
            jsonFile.setRoot(root);
            jsonFile.setNode(node);
            jsonFile.setValue(value);
            jsonFile.setParent(parent);
            jsonFile.setSpectrum(spectrum);
            jsonFile.save();
        }
    }

    public static JsonFile query(String text){
        return query(CORE_JSON, text);
    }

    public static JsonFile query(String root, String text) {
        if (TextUtils.check(root, text)) {
            Map<String, String> map = parse(text);
            if (TextUtils.check(map)) {
                return SQLite.select().from(JsonFile.class)
                        .where(ops(JsonFile.class, m -> m
                                .put("node", map.get("node"))
                                .put("parent", map.get("parent"))
                                .put("spectrum", map.get("spectrum"))
                        )).querySingle();
            }
        }
        return null;
    }

    public static List<JsonFile> queryList(String text){
        return queryList(CORE_JSON, text);
    }

    public static List<JsonFile> queryList(String root, String text) {
        if (TextUtils.check(root, text)) {
            Map<String, String> map = parse(text);
            if (TextUtils.check(map)) {
                return SQLite.select().from(JsonFile.class)
                        .where(ops(JsonFile.class, m -> m
                                .put("node", map.get("node"))
                                .put("parent", map.get("parent"))
                                .put("spectrum", map.get("spectrum"))
                        )).queryList();
            }
        }
        return null;
    }

    private static Map<String, String> parse(String text) {
        if (!TextUtils.isEmpty(text) && !text.contains("..")) {
            if (text.endsWith(".")) {
                text = text.substring(0, text.length() - 1);
            }
            if (text.startsWith(".")) {
                text = text.substring(1, text.length());
            }
            Map<String, String> map = new HashMap<>();
            text = lastIndexOf(text, map, "node");
            text = lastIndexOf(text, map, "parent");
            if (text != null) {
                if (text.endsWith(".")) {
                    text = text.substring(0, text.length() - 1);
                }
                map.put("spectrum", text);
            }
            return map;
        }
        return null;
    }

    private static String lastIndexOf(String text, Map<String, String> map,
                                      String name) {
        if (TextUtils.check(text, name) && map != null) {
            int lastIndexOf = text.lastIndexOf(".");
            if (lastIndexOf > -1) {
                map.put(name, text.substring(lastIndexOf + 1, text.length()));
                return text.substring(0, lastIndexOf);
            } else {
                map.put(name, text);
            }
        }
        return null;
    }

    @PrimaryKey(autoincrement = true)
    private transient long id;
    @Column
    private String node;//名称,即当前节点和数据
    @Column
    private String root;//名称,即根节点
    @Column
    private String value;//类型,[],{},string
    @Column
    private String parent;//父系名称
    @Column
    private String spectrum;//谱系,以.连接

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getSpectrum() {
        return spectrum;
    }

    public void setSpectrum(String spectrum) {
        this.spectrum = spectrum;
    }

}