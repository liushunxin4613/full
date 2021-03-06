package com.ylink.fullgoal.db.core;

import android.support.annotation.NonNull;

import com.leo.core.util.ObjectUtil;
import com.leo.core.util.RunUtil;
import com.leo.core.util.TextUtils;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.ylink.fullgoal.db.table.Files;
import com.ylink.fullgoal.db.table.JsonFile;
import com.ylink.fullgoal.db.table.Request;
import com.ylink.fullgoal.db.table.Times;
import com.ylink.fullgoal.db.table.Text;
import com.ylink.fullgoal.db.migration.FilesMigration;

import java.util.ArrayList;
import java.util.List;

/**
 * #1:
 * 创建User表{@link User}
 * <p>
 * #2:
 * 1>创建基表{@link DBModel}
 * 2>创建Request表{@link Request}
 * 3>所有被注解参数若为private修饰则必须配齐get/set方法
 * 4>主键最好自行设置,不用继承
 * <p>
 * #5:
 * 废弃基表{@link DBModel}
 * <p>
 * #6:
 * 1>创建Files表{@link Files},做文件存储和处理
 * 2>创建JsonFile表{@link JsonFile},做json文件处理
 * <p>
 * #7:
 * 1>修改Files表{@link Files},删除filePath,增加fileName、fileDir、fileType;未成功;
 * <p>
 * #8:
 * 1>创建FilesMigration表{@link FilesMigration},使7>生效
 * <p>
 * #9:
 * 1>创建JsonFile表{@link JsonFile},使生效
 * <p>
 * #12:
 * 1>创建Times表{@link Times},使生效
 * #13:
 * 1>创建Text表{@link Text},使生效
 */
@Database(version = 13)
public class AppDatabase {

    private final static List<Class> DELETE_CLASS_ARGS = new ArrayList<>();

    //对外方法

    public static void init(){
        initDelete();
    }

    public static void lazy() {
        initAdd();//初始化配置数据
        initDelete();//初始化删除
    }

    private static void initAdd() {
        RunUtil.execute(JsonFile.queryList("AppDatabase.CLERA_CLASS_ARGS.className"), item -> {
            Class clz = ObjectUtil.getClass(item.getValue());
            if (clz != null) {
                DELETE_CLASS_ARGS.add(clz);
            }
        });
    }

    public static void query(@NonNull Class<? extends BaseModel> table) {
        SQLite.select().from(table).async().queryListResultCallback((transaction, data)
                -> TextUtils.lookDataJsonString(String.format("AppDatabase %s", table.getName()),
                data)).execute();
    }

    //私有化

    private static void initDelete() {
        RunUtil.execute(DELETE_CLASS_ARGS, item -> {
            if (item != null && BaseModel.class.isAssignableFrom(item)) {
                SQLite.delete(item).execute();//清理数据表
            }
        });
    }

}