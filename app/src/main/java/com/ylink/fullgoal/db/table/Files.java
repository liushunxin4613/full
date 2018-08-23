package com.ylink.fullgoal.db.table;

import com.leo.core.util.TextUtils;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ylink.fullgoal.db.core.AppDatabase;
import com.ylink.fullgoal.db.core.DBModel;

import java.util.Date;
import java.util.List;

/**
 * 文件处理
 */
@Table(database = AppDatabase.class, name = "Files_v3")
public class Files extends DBModel {

    private final static transient String _HEADER = "://";
    public final static transient String FILE_TYPE_FILE = "file";
    public final static transient String FILE_TYPE_ASSETS = "assets";
    public final static transient String FILE_TYPE_FILE_HEADER = FILE_TYPE_FILE + _HEADER;
    public final static transient String FILE_TYPE_ASSETS_HEADER = FILE_TYPE_ASSETS + _HEADER;

    public static void sav(String fileName, String fileDir, String fileType, String hashCode) {
        if (TextUtils.check(fileName, fileType, hashCode)) {
            Files files = SQLite.select().from(Files.class)
                    .where(ops(Files.class, m
                            -> m.put("fileName", fileName)
                            .put("fileDir", fileDir)
                            .put("fileType", fileType)))
                    .querySingle();
            if (files == null) {
                files = new Files();
                files.setFileName(fileName);
                files.setFileDir(fileDir);
                files.setFileType(fileType);
                files.setHashCode(hashCode);
                files.setDate(new Date());
                files.save();
            } else if (!TextUtils.equals(hashCode, files.getHashCode())){
                files.setHashCode(hashCode);
                files.setDate(new Date());
                files.update();
            }
        }
    }

    public static boolean check(String name, String hashCode) {
        if (TextUtils.check(name, hashCode)) {
            List<Files> data = SQLite.select().from(Files.class)
                    .where(ops(Files.class, m -> m
                            .put("fileName", name)
                            .put("hashCode", hashCode)
                    )).queryList();
            return TextUtils.check(data);
        }
        return false;
    }

    @PrimaryKey(autoincrement = true)
    private transient long id;
    @Column
    private String fileName;//文件名称
    @Column
    private String fileDir;//文件类型
    @Column
    private String fileRoot;//文件根节点
    @Column
    private String fileType;//文件类型
    @Column
    private String hashCode;//hash值md5转换而来
    @Column
    private Date date;//插入时间

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileRoot() {
        return fileRoot;
    }

    public void setFileRoot(String fileRoot) {
        this.fileRoot = fileRoot;
    }

}