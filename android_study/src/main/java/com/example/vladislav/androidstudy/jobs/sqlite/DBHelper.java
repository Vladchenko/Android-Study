package com.example.vladislav.androidstudy.jobs.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Operating with a database
 *
 * Created by Влад on 17.02.2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();

    // Database name
    private String mDatabaseName;

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

    DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,
                    DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + mDatabaseName);
        // Create tables again
        onCreate(db);
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
                               List<Person> list) {
        Log.i(TAG, "Putting stub data to a database");
        for (int i = 0; i < list.size(); i++) {
            db.execSQL(("INSERT or replace INTO " + mDatabaseName
                    + "(" + columns[0] + ", " + columns[1]
                    + ", " + columns[2] + ", " + columns[3]
                    + ", " + columns[4] + ") Values("
                    + "'" + list.get(i).getName() + "', "
                    + "'" + list.get(i).getLastName() + "', "
                    + "'" + list.get(i).getCellPhoneNumber() + "', "
                    + "'" + list.get(i).getEmail() + "', "
                    + "'" + list.get(i).getAddress() + "') "));
        }
        Log.i(TAG, "Data has been put to a database");
    }

    /**
     * Retrieving a name of a database
     *
     * @return
     */
    public String getDatabaseName() {
        return mDatabaseName;
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

    /**
     * Dropping a table
     */
    public void dropTable(SQLiteDatabase db) {
        Log.i(TAG, "Dropping a database");
        db.execSQL(("Drop table if exists " + mDatabaseName));
        Log.i(TAG, "Database dropped");
    }

}
