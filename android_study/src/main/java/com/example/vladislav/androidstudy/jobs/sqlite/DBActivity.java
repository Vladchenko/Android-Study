package com.example.vladislav.androidstudy.jobs.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

import java.util.ArrayList;
import java.util.List;

public class DBActivity extends AppCompatActivity {

    // Database name to operate with
    private static final String DB_NAME = "PeopleDataBase";
    private static final String TAG = DBActivity.class.getSimpleName();
    // Columns to create a table with
    private final String[] mColumns = makeColumnsNames();

    private TableLayout mPeopleTable;
    private DBHelper mDbHelper;
    private List<ArrayList<String>> mTableData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        mPeopleTable = (TableLayout) findViewById(R.id.people_table);
        // Creating a DBHelper with a database name provided. This name cannot be changed
        // throughout the operations with this database.
        mDbHelper = new DBHelper(this, DB_NAME);
        // Dropping data base, for it could not add the same data over and over again
        // TODO Make another solution to this
        mDbHelper.dropTable(mDbHelper.getWritableDatabase());
        mDbHelper.createTableWithColumns(mDbHelper.getWritableDatabase(), mColumns);
        mDbHelper.putDataToTable(mDbHelper.getWritableDatabase(), mColumns,
                makeUpPeopleStubList());
        mTableData = mDbHelper.getTableData(mDbHelper.getWritableDatabase(), mColumns);
        putRowsToTable(mPeopleTable);
    }

    // Making up a list of a columns
    private String[] makeColumnsNames() {
        String[] columns = new String[5];
        columns[0] = "Name";
        columns[1] = "LastName";
        columns[2] = "CellphoneNumber";
        columns[3] = "Email";
        columns[4] = "Address";
        return columns;
    }

    // Creating a stub list of a people
    private List<Person> makeUpPeopleStubList() {
        Log.i(TAG, "Making a data stub list");
        List<Person> list = new ArrayList<>();
        list.add(new Person("Vlad", "Yanchenko", "9048625912", "vladchenko@yandex.ru",
                "Innopolis, Sportivnaya 120"));
        list.add(new Person("Lilya", "Akhmentshina", "9147397212", "lilchenko@mail.ru",
                "Innopolis, Sportivnaya 120"));
        list.add(new Person("Rezeda", "Batkovna", "9628113929", "rezedets@gmail.com",
                "Nab. Chelny, sh. Usmanova"));
        Log.i(TAG, "Data stub list is made");
        return list;
    }

    // Putting rows to a table layout
    private void putRowsToTable(TableLayout table) {
        TableRow tableRow = null;
        for (List<String> row : mTableData) {
            tableRow = new TableRow(this);
            for (int i = 0; i < row.size(); i++) {
                TextView textView = new TextView(this);
                textView.setPadding(8, 8, 8, 8);
                textView.setText(row.get(i));
                tableRow.addView(textView);
            }
            table.addView(tableRow);
        }
    }
}
