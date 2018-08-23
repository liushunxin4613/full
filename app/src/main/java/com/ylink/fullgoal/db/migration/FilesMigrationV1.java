package com.ylink.fullgoal.db.migration;

import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;
import com.ylink.fullgoal.db.core.AppDatabase;
import com.ylink.fullgoal.db.table.Files;

@Migration(version = 11, database = AppDatabase.class)
public class FilesMigrationV1 extends AlterTableMigration<Files> {

    public FilesMigrationV1(Class<Files> table) {
        super(table);
    }

    @Override
    public void onPreMigrate() {
        super.onPreMigrate();
        addColumn(SQLiteType.TEXT, "fileRoot");
    }

}