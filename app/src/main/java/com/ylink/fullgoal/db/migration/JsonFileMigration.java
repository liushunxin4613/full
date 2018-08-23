package com.ylink.fullgoal.db.migration;

import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;
import com.ylink.fullgoal.db.core.AppDatabase;
import com.ylink.fullgoal.db.table.JsonFile;

@Migration(version = 10, database = AppDatabase.class)
public class JsonFileMigration extends AlterTableMigration<JsonFile> {

    public JsonFileMigration(Class<JsonFile> table) {
        super(table);
    }

    @Override
    public void onPreMigrate() {
        super.onPreMigrate();
        addColumn(SQLiteType.TEXT, "value");
    }

}
