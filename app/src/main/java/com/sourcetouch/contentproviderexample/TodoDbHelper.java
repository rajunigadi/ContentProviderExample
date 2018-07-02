package com.sourcetouch.contentproviderexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rajashekhar Vanahalli on 29/06/18.
 */
public class TodoDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "todos.db";

    private static final int DATABASE_VERSION = 1;

    public TodoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TODO_TABLE =
                "CREATE TABLE " + TodoContract.TodoEntry.TABLE_NAME + " (" +
                        TodoContract.TodoEntry._ID                + " INTEGER PRIMARY KEY AUTOINCREMENT, "   +
                        TodoContract.TodoEntry.COLUMN_TASK              + " TEXT NOT NULL,"                        +
                        TodoContract.TodoEntry.COLUMN_DATE              + " INTEGER NOT NULL);";
        db.execSQL(SQL_CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TodoContract.TodoEntry.TABLE_NAME);
        onCreate(db);
    }
}
