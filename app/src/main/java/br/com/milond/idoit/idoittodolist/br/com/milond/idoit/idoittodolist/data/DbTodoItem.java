package br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.security.spec.ECField;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos Leao (carloseduardogu@gmail.com) on 29/04/2015.
 */
public class DbTodoItem {

    // Database fields
    private SQLiteDatabase database;
    private IDoItSqlHelper dbHelper;
    private String[] allColumns = {IDoItSqlHelper.COLUMN_ID,
            IDoItSqlHelper.COLUMN_TODO_ITEM_DESCRIPTION,
            IDoItSqlHelper.COLUMN_CREATION_DATE,
            IDoItSqlHelper.COLUMN_DOTO_DATE,
            IDoItSqlHelper.COLUMN_COMPLETE_DATE,
            IDoItSqlHelper.COLUMN_IS_ARCHIVATED};

    public DbTodoItem(Context context) {
        dbHelper = new IDoItSqlHelper(context);
    }

    public synchronized void open(){
        try {
            database = dbHelper.getWritableDatabase();
        } catch (Exception e) {

        }
    }

    public void close() {
        dbHelper.close();
    }

    public TodoItem createTodoItem(String todoItem) {
        ContentValues values = new ContentValues();

        values.put(IDoItSqlHelper.COLUMN_TODO_ITEM_DESCRIPTION, todoItem);

        long insertId = database.insert(IDoItSqlHelper.TABLE_TODO_ITEM, null,
                values);

        Cursor cursor = database.query(IDoItSqlHelper.TABLE_TODO_ITEM,
                allColumns, IDoItSqlHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        TodoItem newTodoItem = cursorToItem(cursor);
        cursor.close();
        return newTodoItem;
    }

    public void deleteTodoItem(TodoItem todoItem) {
        long id = todoItem.getId();

        database.delete(IDoItSqlHelper.TABLE_TODO_ITEM, IDoItSqlHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<TodoItem> getAllTodoItens() {
        List<TodoItem> todoItems = new ArrayList<TodoItem>();

        Cursor cursor = database.query(IDoItSqlHelper.TABLE_TODO_ITEM,
                allColumns, null, null, null, null, IDoItSqlHelper.COLUMN_ID + " desc");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TodoItem todoItem = cursorToItem(cursor);
            todoItems.add(todoItem);
            cursor.moveToNext();
        }

        cursor.close();
        return todoItems;
    }

    private TodoItem cursorToItem(Cursor cursor) {
        TodoItem todoItem = new TodoItem();
        todoItem.setId(cursor.getLong(0));
        todoItem.setDescription(cursor.getString(1));
        return todoItem;
    }


}
