package com.example.vladislav.androidstudy.jobs.criminalrecords.data_providing;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.vladislav.androidstudy.jobs.criminalrecords.DBHelper;

import static com.example.vladislav.androidstudy.jobs.criminalrecords.ui.CriminalRecordsActivity.DATABASE_NAME;

/**
 * Created by Влад on 04.04.2018.
 */

public class CrimesContentProvider extends ContentProvider {

    private DBHelper mDBHelper;

    @Override
    public boolean onCreate() {
        mDBHelper = DBHelper.getInstance(getContext(), DATABASE_NAME);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = mDBHelper.getCrimesCursor(mDBHelper.getReadableDatabase());
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
        getContext().getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }
}
