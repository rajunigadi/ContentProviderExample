package com.sourcetouch.contentproviderexample;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Rajashekhar Vanahalli on 29/06/18.
 */

public class TodoProvider extends ContentProvider {

    private TodoDbHelper mOpenHelper;

    public static final int CODE_TODO = 100;
    public static final int CODE_TODO_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    @Override
    public boolean onCreate() {
        mOpenHelper = new TodoDbHelper(getContext());
        return mOpenHelper != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case CODE_TODO_WITH_ID: {
                String _ID = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{_ID};

                cursor = mOpenHelper.getReadableDatabase().query(
                        TodoContract.TodoEntry.TABLE_NAME,
                        projection,
                        TodoContract.TodoEntry._ID + " = ? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);

                break;
            }
            case CODE_TODO: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        TodoContract.TodoEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case CODE_TODO:
                long _id = db.insert(TodoContract.TodoEntry.TABLE_NAME, null, values);
                if (_id != -1) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return TodoContract.TodoEntry.buildTodoUriWithId(_id);

            default:
                return null;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case CODE_TODO:
                int _id = db.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs);
                if (_id != -1) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return _id;
            default:
                return -1;
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case CODE_TODO:
                int _id = db.update(TodoContract.TodoEntry.TABLE_NAME, values, selection, selectionArgs);
                if (_id != -1) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return _id;
            default:
                return -1;
        }
    }

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TodoContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, TodoContract.TodoEntry.TABLE_NAME, CODE_TODO);
        matcher.addURI(authority, TodoContract.TodoEntry.TABLE_NAME + "/#", CODE_TODO_WITH_ID);

        return matcher;
    }
}
