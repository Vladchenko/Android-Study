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
        String[] columns = new String[5];
        columns[0] = "Id";
        columns[1] = "Title";
        columns[2] = "Description";
        columns[3] = "Checked";
        return columns;
    }

    /**
     * Getting all data from a table
     *
     * @param db database to get data from
     * @param columns columns of table to be present in a retrieved dataset
     * @return 2d {@link ArrayList} os {@link String}
     */
    public List<ArrayList<String>> getTableData(SQLiteDatabase db, String[] columns) {

        Cursor cursor = db.query(mDatabaseName, columns, null, null, null, null, null);
        List<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            tableData.add(new ArrayList<String>());
            // Pass through all the columns in one row and put them in a list
            for (int j = 0; j < cursor.getColumnCount(); j++) {
                tableData.get(tableData.size() - 1).add("");
                tableData.get(tableData.size() - 1).set(j, cursor.getString(j));
            }
            cursor.moveToNext();
        }
        cursor.close();
        return tableData;
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
                    + "(" + columns[0] + ", " + columns[1]
                    + ", " + columns[2] + ", " + columns[3]
                    + ") Values(" + "'" + list.get(i).getId() + "', "
                    + "'" + list.get(i).getTitle() + "', "
                    + "'" + list.get(i).getDescription() + "', "
                    + "'" + list.get(i).isSolved() + "') "));
        }
        Log.i(TAG, "Data has been put to a database");
    }

    /**
     * Putting a crime instance to a database
     *
     * @param db database to put data into
     * @param columns columns of a database to write data into
     * @param crime instance of a crime
     */
    public void putCrimeToTable(SQLiteDatabase db, Crime crime) {
        Log.i(TAG, "Putting Crime to a database");
            db.execSQL(("INSERT INTO " + mDatabaseName
                    + "(" + mColumns[0] + ", " + mColumns[1]
                    + ", " + mColumns[2] + ", " + mColumns[3]
                    + ") Values(" + "'" + crime.getId() + "', "
                    + "'" + crime.getTitle() + "', "
                    + "'" + crime.getDescription() + "', "
                    + "'" + crime.isSolved() + "') "));
        Log.i(TAG, "Crime has been put to a database");
    }

    /**
     * Creating table with a specific columns, unless already present
     *
     * @param db subject database
     * @param columns columns that a table to be made of. All columns have type "text"
     */
    public void createTableWithColumns(SQLiteDatabase db, String[] columns) {
        Log.i(TAG, "Creating a database");
        String sqlRequest = "Create table if not exists " + mDatabaseName + "("
                + " id integer primary key autoincrement, ";
        for (int i=0; i < columns.length - 1; i++) {
            sqlRequest += columns[i] + " text, ";
        }
        sqlRequest += columns[columns.length - 1] + " text" + ");";
        Log.i(TAG, "Request is:" + sqlRequest);
        db.execSQL(sqlRequest);
        Log.i(TAG, "Database created");
    }

}
