package com.example.vladislav.androidstudy.jobs.criminalrecords;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class that provides a means to access database.
 *
 * Created by Влад on 11.03.2018.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();

    // Database name
    private String mDatabaseName;

    // Columns to create a table with
    private final String[] mColumns = makeColumnsNames();

    private static DBHelper mInstance = null;

    public static DBHelper getInstance(Context context, String databaseName) {
        if (mInstance == null) {
            mInstance = new DBHelper(context, databaseName);
        }
        return mInstance;
    }

    /**
     * Creates a database with a specific name
     *
     * @param context      some context
     * @param databaseName database name
     */
    private DBHelper(Context context, String databaseName) {
        super(context, databaseName, null, 1);
        mDatabaseName = databaseName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Making up a list of a columns
    private String[] makeColumnsNames() {
        String[] columns = new String[5];
        columns[0] = "Id";
        columns[1] = "Title";
        columns[2] = "Description";
        columns[3] = "Date";
        columns[4] = "Checked";
        return columns;
    }

    /**
     * Getting a cursor for all the crimes
     *
     * @param db
     * @return
     */
    public Cursor getCrimesCursor(SQLiteDatabase db) {
        return db.query(mDatabaseName, mColumns, null, null, null, null, null);
    }

    /**
     * Getting all data from a table
     *
     * @param db database to get data from
     * @return 2d {@link ArrayList} os {@link String}
     */
    public ParcelableCrimesList getCrimeData(SQLiteDatabase db) {
        Cursor cursor = getCrimesCursor(db);
        return getCrimeData(cursor);
    }

    public ParcelableCrimesList getCrimeData(Cursor cursor) {
        ParcelableCrimesList crimes = new ParcelableCrimesList();
        cursor.moveToFirst();
        Crime crime;
        while (!cursor.isAfterLast()) {
            crime = getCrimeFromCursor(cursor);
            crimes.add(crime);
            cursor.moveToNext();
        }
//        cursor.close();
        return crimes;
    }

    public Crime getCrimeById(SQLiteDatabase db, String id) {
        Crime crime = null;
        Cursor cursor = db.query(mDatabaseName, mColumns, mColumns[0] + "=" + id,
                null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            crime = getCrimeFromCursor(cursor);
        }
        cursor.close();
        return crime;
    }

    public int removeCrimeById(SQLiteDatabase db, String id) {
        return db.delete(mDatabaseName, mColumns[0] + "=" + id, null);
    }

    /**
     * Putting a crime instance to a database
     *
     * @param db    database to put data into
     * @param crime instance of a crime
     */
    public void putCrimeToTable(SQLiteDatabase db, Crime crime) {
        Log.i(TAG, "Checking if a Crime is already present in a database");
        Cursor cursor = db.query(mDatabaseName, new String[]{mColumns[0]},
                mColumns[0] + "=" + crime.getId(), null, null, null, null);
        if (cursor.getCount() > 0) {
            Log.i(TAG, "Such crime exists");
            // Means there is such entry present in a database and we should quit this method
            return;
        }
        Log.i(TAG, "Putting Crime to a database");
        db.execSQL(("INSERT INTO " + mDatabaseName
                + "(" + mColumns[0] + ", " + mColumns[1] + ", " + mColumns[2]
                + ", " + mColumns[3] + ", " + mColumns[4] + ") Values(" + "'"
                + crime.initId() + "', '" + crime.getTitle() + "', " + "'"
                + crime.getDescription() + "', " + "'" + crime.getDate() + "', "
                + "'" + crime.isSolved() + "') "));
        Log.i(TAG, "Crime has been put to a database");
    }

    private Crime getCrimeFromCursor(Cursor cursor) {
        Crime crime = new Crime();
        crime.setId(cursor.getString(0));
        crime.setTitle(cursor.getString(1));
        crime.setDescription(cursor.getString(2));
        crime.setDate(new Date(Date.parse(cursor.getString(3))));
        crime.setSolved(Boolean.parseBoolean(cursor.getString(4)));
        return crime;
    }

    /**
     * Creating table with a specific columns, unless already present
     *
     * @param db subject database
     */
    public void createTableWithColumns(SQLiteDatabase db) {
        Log.i(TAG, "Creating a database");
        String sqlRequest = "Create table if not exists " + mDatabaseName + "( ";
        //+ "id integer primary key autoincrement, ";
        for (int i = 0; i < mColumns.length - 1; i++) {
            sqlRequest += mColumns[i] + " text, ";
        }
        sqlRequest += mColumns[mColumns.length - 1] + " text" + ");";
        Log.i(TAG, "Request is: " + sqlRequest);
        db.execSQL(sqlRequest);
        Log.i(TAG, "Database created");
    }

    /**
     * Dropping a table, if needed
     */
    public void dropTable(SQLiteDatabase db) {
        Log.i(TAG, "Dropping a database");
        db.execSQL(("Drop table if exists " + mDatabaseName));
        Log.i(TAG, "Database dropped");
    }

}
