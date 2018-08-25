package com.ylink.fullgoal.db.table;

import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ylink.fullgoal.db.core.AppDatabase;
import com.ylink.fullgoal.db.core.DBModel;

import java.util.Date;
import java.util.List;

@Table(database = AppDatabase.class)
public class Times extends DBModel {

    public synchronized static void sav(String type, String tag) {
        if (TextUtils.check(type, tag)) {
            Date date = new Date();
            List<Times> data = SQLite.select().from(Times.class)
                    .where(ops(Times.class, m -> m
                            .put("type", type)
                    ))
                    .orderBy(Times_Table.date, true)
                    .queryList();
            if (TextUtils.isEmpty(data)) {
                Times times = new Times();
                times.setType(type);
                times.setTag(tag);
                times.setNo(1);
                times.setDate(date);
                times.save();
            } else {
                Times end = data.get(data.size() - 1);
                if (end != null) {
                    Times times = new Times();
                    times.setType(type);
                    times.setTag(tag);
                    times.setNo(end.getNo() + 1);
                    times.setDate(date);
                    times.save();
                    String time = TextUtils.timeToString(date.getTime()
                            - end.getDate().getTime());
                    LogUtil.ii("Times", String.format("%s => %s, 耗时%s", type, tag, time));
                }
            }
        }
    }

    @PrimaryKey(autoincrement = true)
    private transient long id;
    @Column
    private String type;//类型
    @Column
    private String tag;//标记
    @Column
    private Date date;//时间
    @Column
    private int no;//序号

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

}