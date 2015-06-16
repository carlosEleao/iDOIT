package br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Carlos Leao (carloseduardogu@gmail.com) on 29/04/2015.
 */
public class IDoItSqlHelper extends SQLiteOpenHelper {

    public static final String TABLE_TODO_ITEM = "todo_item";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TODO_ITEM_DESCRIPTION = "tood_item";
    public static final String COLUMN_CREATION_DATE = "creation_date";
    public static final String COLUMN_TODO_DATE = "todo_date";
    public static final String COLUMN_COMPLETE_DATE = "complete_date";
    public static final String COLUMN_IS_ARCHIVATED = "archived";

    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TODO_TABLE = "CREATE TABLE "
            + TABLE_TODO_ITEM + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TODO_ITEM_DESCRIPTION + " text not null, "
            + COLUMN_CREATION_DATE + " int, "
            + COLUMN_TODO_DATE + " int, "
            + COLUMN_COMPLETE_DATE + " int, "
            + COLUMN_IS_ARCHIVATED + " int DEFAULT 0 " + ");";

    private static final String UPGRADE_TODO_TABLE_V1_V2 =
            "ALTER TABLE " + TABLE_TODO_ITEM  + " "+ COLUMN_CREATION_DATE + " int; "
                    + "ALTER TABLE " + TABLE_TODO_ITEM  + " "+ COLUMN_TODO_DATE + " int; "
                    + "ALTER TABLE " + TABLE_TODO_ITEM  + " "+ COLUMN_IS_ARCHIVATED + " int DEFAULT 0; "
                    + "ALTER TABLE " + TABLE_TODO_ITEM  + " "+ COLUMN_CREATION_DATE + " int; ";

    public IDoItSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(IDoItSqlHelper.class.getName(), "Up version " + oldVersion + " to " + newVersion);
        if(oldVersion == 1 && newVersion == 2) {
            db.execSQL(UPGRADE_TODO_TABLE_V1_V2);
        }

        onCreate(db);
    }

}
