package com.example.vladislav.androidstudy.jobs.criminalrecords.data_providing;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

import com.example.vladislav.androidstudy.jobs.criminalrecords.DBHelper;

import java.util.concurrent.TimeUnit;

/**
 * CursorLoader to download crimes from a database and return them to CriminalRecordListFragment.
 *
 * https://stackoverflow.com/questions/28710633/difference-between-cursorloader-and-asynctaskloader
 *
 * Created by Влад on 04.04.2018.
 */

public class CrimesCursorLoader extends CursorLoader {

    private DBHelper mDBHelper;

    public CrimesCursorLoader(Context context) {
        super(context);
    }

    public CrimesCursorLoader(Context context, DBHelper dbHelper) {
        super(context);
        mDBHelper = dbHelper;
    }

    public CrimesCursorLoader(Context context, Uri uri, String[] projection, String selection,
                              String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = mDBHelper.getCrimesCursor(mDBHelper.getReadableDatabase());
        try {
            // Making a delay, to make a look that loader loads the data from a net.
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cursor;
    }

//    @Override
//    public void onContentChanged() {
//        super.onContentChanged();
//    }
}
