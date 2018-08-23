package com.ylink.fullgoal.db.migration;

import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;
import com.ylink.fullgoal.db.core.AppDatabase;
import com.ylink.fullgoal.db.table.Files;

@Migration(version = 8, database = AppDatabase.class)
public class FilesMigration extends AlterTableMigration<Files> {

    public FilesMigration(Class<Files> table) {
        super(table);
    }

    @Override
    public void onPreMigrate() {
        super.onPreMigrate();
        addColumn(SQLiteType.TEXT, "fileName");
        addColumn(SQLiteType.TEXT, "fileDir");
        addColumn(SQLiteType.TEXT, "fileType");
    }

}