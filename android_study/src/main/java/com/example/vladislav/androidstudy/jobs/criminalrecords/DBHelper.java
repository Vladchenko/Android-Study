package com.example.vladislav.androidstudy.jobs.criminalrecords;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.vladislav.androidstudy.jobs.sqlite.Person;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Влад on 11.03.2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();

    // Database name
    private String mDatabaseName;

    // Columns to create a table with
    private final String[] mColumns = makeColumnsNames();

    /**
     * Creates a database with a specific name
     *
     * @param context some context
     * @param databaseName database name
     */
    DBHelper(Context context, String databaseName) {
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
        String[] columns = new String[4];
        columns[0] = "Title";
        columns[1] = "Description";
        columns[2] = "Date";
        columns[3] = "Checked";
        return columns;
    }

    /**
     * Getting all data from a table
     *
     * @param db database to get data from
     * @return 2d {@link ArrayList} os {@link String}
     */
    public List<Crime> getCrimeData(SQLiteDatabase db) {

        Cursor cursor = db.query(mDatabaseName, mColumns, null, null, null, null, null);
        List<Crime> crimes = new ArrayList<Crime>();
        cursor.moveToFirst();
        Crime crime;
        while (!cursor.isAfterLast()) {
            crime = new Crime();
            crime.setTitle(cursor.getString(0));
            crime.setDescription(cursor.getString(1));
            crime.setDate(new Date(Date.parse(cursor.getString(2))));
            crime.setSolved(Boolean.parseBoolean(cursor.getString(3)));
            crimes.add(crime);
            cursor.moveToNext();
        }
        cursor.close();
        return crimes;
    }

    /**
     * Putting data to a database
     *
     * @param db database to put data to
     * @param columns columns of a table to put a data to
     * @param list lst of data to be put to a table
     */
    public void putDataToTable(SQLiteDatabase db, String[] columns,
                               List<Crime> list) {
        Log.i(TAG, "Putting data to a database");
        for (int i = 0; i < list.size(); i++) {
            db.execSQL(("INSERT INTO " + mDatabaseName
                    + "(" + columns[0] + ", " + columns[1] + ", " + columns[2] + ", " + columns[3]
                    + ") Values(" + "'" + "', "
                    + "'" + list.get(i).getTitle() + "', "
                    + "'" + list.get(i).getDescription() + "', "
                    + "'" + list.get(i).isSolved() + "', "
                    + "'" + list.get(i).getDate() + "') "));
        }
        Log.i(TAG, "Data has been put to a database");
    }

    /**
     * Putting a crime instance to a database
     *
     * @param db database to put data into
     * @param crime instance of a crime
     */
    public void putCrimeToTable(SQLiteDatabase db, Crime crime) {
        Log.i(TAG, "Putting Crime to a database");
            db.execSQL(("INSERT INTO " + mDatabaseName
                    + "(" + mColumns[0] + ", " + mColumns[1] + ", " + mColumns[2]
                    + ", " + mColumns[3] + ") Values(" + "'" + crime.getTitle() + "', "
                    + "'" + crime.getDescription() + "', "
                    + "'" + crime.isSolved() + "', "
                    + "'" + crime.getDate() + "') "));
        Log.i(TAG, "Crime has been put to a database");
    }

    /**
     * Creating table with a specific columns, unless already present
     *
     * @param db subject database
     */
    public void createTableWithColumns(SQLiteDatabase db) {
        Log.i(TAG, "Creating a database");
        String sqlRequest = "Create table if not exists " + mDatabaseName + "("
                + " id integer primary key autoincrement, ";
        for (int i=0; i < mColumns.length - 1; i++) {
            sqlRequest += mColumns[i] + " text, ";
        }
        sqlRequest += mColumns[mColumns.length - 1] + " text" + ");";
        Log.i(TAG, "Request is:" + sqlRequest);
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
