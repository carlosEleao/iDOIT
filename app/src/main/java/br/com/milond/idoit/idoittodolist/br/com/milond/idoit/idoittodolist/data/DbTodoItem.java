package br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
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
            IDoItSqlHelper.COLUMN_TODO_DATE,
            IDoItSqlHelper.COLUMN_COMPLETE_DATE,
            IDoItSqlHelper.COLUMN_IS_ARCHIVATED};

    public DbTodoItem(Context context) {
        dbHelper = new IDoItSqlHelper(context);
    }

    public synchronized void open() {
        try {
            database = dbHelper.getWritableDatabase();
        } catch (Exception e) {

        }
    }

    public void close() {
        dbHelper.close();
    }

    public TodoItem createTodoItem(TodoItem todoItem) {
        ContentValues values = new ContentValues();

        values.put(IDoItSqlHelper.COLUMN_TODO_ITEM_DESCRIPTION, todoItem.getDescription());
        values.put(IDoItSqlHelper.COLUMN_CREATION_DATE, todoItem.getCreationDate().getTime());

        if(todoItem.getTodoDate() != null) {
            values.put(IDoItSqlHelper.COLUMN_TODO_DATE, todoItem.getTodoDate().getTime());
        }

        values.put(IDoItSqlHelper.COLUMN_IS_ARCHIVATED, todoItem.getComplete() ? 1 : 0);

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

    public void updateTodoItem(TodoItem todoItem) {
        ContentValues values = new ContentValues();

        values.put(IDoItSqlHelper.COLUMN_TODO_ITEM_DESCRIPTION, todoItem.getDescription());
        values.put(IDoItSqlHelper.COLUMN_CREATION_DATE, todoItem.getCreationDate().getTime());
        values.put(IDoItSqlHelper.COLUMN_IS_ARCHIVATED, todoItem.getComplete() ? 1 : 0);
        values.put(IDoItSqlHelper.COLUMN_TODO_DATE, todoItem.getTodoDate().getTime());
        values.put(IDoItSqlHelper.COLUMN_COMPLETE_DATE, todoItem.getCompleteDate().getTime());

        int rowsUpdated = database.update(IDoItSqlHelper.TABLE_TODO_ITEM, values, IDoItSqlHelper.COLUMN_ID + " = " + String.valueOf(todoItem.getId()), null);
    }

    public List<TodoItem> getAllTodoItens() {
        List<TodoItem> todoItems = new ArrayList<TodoItem>();

        Cursor cursor = database.query(IDoItSqlHelper.TABLE_TODO_ITEM,
                allColumns,  IDoItSqlHelper.COLUMN_IS_ARCHIVATED + " = 0 OR " + IDoItSqlHelper.COLUMN_IS_ARCHIVATED + " is null", null, null, null, IDoItSqlHelper.COLUMN_ID + " desc");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TodoItem todoItem = cursorToItem(cursor);
            todoItems.add(todoItem);
            cursor.moveToNext();
        }

        cursor.close();
        return todoItems;
    }

    public List<TodoItem> getAllCompleteItens() {
        List<TodoItem> todoItems = new ArrayList<TodoItem>();

        Cursor cursor = database.query(IDoItSqlHelper.TABLE_TODO_ITEM,
                allColumns, IDoItSqlHelper.COLUMN_IS_ARCHIVATED + " = 1", null, null, null, IDoItSqlHelper.COLUMN_COMPLETE_DATE + " desc");

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
        todoItem.setCreationDate(new Date(cursor.getLong(2)));
        todoItem.setTodoDate(new Date(cursor.getLong(3)));
        todoItem.setCompleteDate(new Date(cursor.getLong(4)));
        todoItem.setComplete(cursor.getInt(5) == 1);

        return todoItem;
    }



}
