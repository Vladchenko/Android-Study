package com.example.vladislav.androidstudy.jobs.sqlite;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SQLiteFragment extends Fragment {

    // Database name to operate with
    private static final String DB_NAME = "PeopleDataBase";
    private static final String TAG = SQLiteFragment.class.getSimpleName();
    // Columns to create a table with
    private final String[] mColumns = makeColumnsNames();

    private TableLayout mPeopleTable;
    private DBHelper mDbHelper;
    private List<ArrayList<String>> mTableData;

    public SQLiteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPeopleTable = (TableLayout) getActivity().findViewById(R.id.people_table);
        // Creating a DBHelper with a database name provided. This name cannot be changed
        // throughout the operations with this database.
        mDbHelper = new DBHelper(getActivity(), DB_NAME);
        // Dropping data base, for it could not add the same data over and over again
        // TODO Make another solution to this
        mDbHelper.dropTable(mDbHelper.getWritableDatabase());
        mDbHelper.createTableWithColumns(mDbHelper.getWritableDatabase(), mColumns);
        mDbHelper.putDataToTable(mDbHelper.getWritableDatabase(), mColumns,
                makeUpPeopleStubList());
        mTableData = mDbHelper.getTableData(mDbHelper.getWritableDatabase(), mColumns);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sqlite, container, false);
        mPeopleTable = (TableLayout)view.findViewById(R.id.people_table);
        putRowsToTable(mPeopleTable);
        return view;
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
        Log.i(TAG, "Making a stub List<Person>");
        List<Person> list = new ArrayList<>();
        list.add(new Person("Vlad", "Yanchenko", "9048625912", "vladchenko@yandex.ru",
                "Innopolis, Sportivnaya 120"));
        list.add(new Person("Lilya", "Akhmentshina", "9147397212", "lilchenko@mail.ru",
                "Innopolis, Sportivnaya 120"));
        list.add(new Person("Rezeda", "Batkovna", "9628113929", "rezedets@gmail.com",
                "Nab. Chelny, sh. Usmanova"));
        Log.i(TAG, "Stub List<Person> is made");
        return list;
    }

    // Putting rows to a table layout
    private void putRowsToTable(TableLayout table) {
        TableRow tableRow = null;
        for (List<String> row : mTableData) {
            tableRow = new TableRow(getActivity());
            for (int i = 0; i < row.size(); i++) {
                TextView textView = new TextView(getActivity());
                textView.setPadding(8, 8, 8, 8);
                textView.setText(row.get(i));
                tableRow.addView(textView);
            }
            table.addView(tableRow);
        }
    }

}
