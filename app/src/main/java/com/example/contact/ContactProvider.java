package com.example.contact;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ContactProvider extends ContentProvider {
    public static final int Contact_DIR = 0;
    public static final int Contact_ITEM = 1;
    private ContactHelper dbHelper;
    private static UriMatcher uriMatcher;
    public static final String AUTHORITY = "com.example.contact.provider";

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "contact", Contact_DIR);
        uriMatcher.addURI(AUTHORITY, "contact/#", Contact_ITEM);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new ContactHelper(getContext(), "Contact.db", null, 1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        //查询数据
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case Contact_DIR:
                cursor = db.query("Contact", projection, null,null, null,
                        null, sortOrder);
                break;
            case Contact_ITEM:
                String contactId= uri.getPathSegments().get(1);
                cursor = db.query("Contact", projection, "id=?",
                        new String[]{contactId}, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        //添加数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case Contact_DIR:
            case Contact_ITEM:
                long newContactId = db.insert("Contact", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/contact/" + newContactId);
                break;
            default:
                break;
        }
        return uriReturn;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)) {
            case Contact_DIR:
                updatedRows = db.update("Contact", values, selection, selectionArgs);
                break;
            case Contact_ITEM:
                String contactId = uri.getPathSegments().get(1);
                updatedRows = db.update("Contact", values, "id=?", new String[]{contactId});
                break;
            default:
                break;
        }
        return updatedRows;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)) {
            case Contact_DIR:
                deletedRows = db.delete("Contact", selection, selectionArgs);
                break;
            case Contact_ITEM:
                String contactId = uri.getPathSegments().get(1);
                deletedRows = db.delete("Contact", "id=?", new String[]{contactId});
                break;
            default:
                break;
        }
        return deletedRows;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (uriMatcher.match(uri)) {
            case Contact_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.contact.provider.contact";
            case Contact_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.contact.provider.contact";
        }
        return null;
    }
}