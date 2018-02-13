package com.example.vladislav.androidstudy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.vladislav.androidstudy.R;

public class ListsViewingActivity extends AppCompatActivity {

    ListView listView;

    String[] names = { "Polaris", "Aldebaran", "Vega", "Ursa Majoris", "Sirius", "Sun",
            "Antares", "VY Canis Majoris", "Rigel", "Betelgeuse", "Alpha Centauri" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists_viewing);

//        ViewGroup footer = (ViewGroup)getLayoutInflater()
//                .inflate(R.layout.listview_footer,listView,false);

        // Strangely, list doesn't scroll
        listView = (ListView) findViewById(R.id.list);
        // Following line of code was here. But what does it do ?
//        listView.addFooterView(footer);
//        listView.addHeaderView(footer);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.my_list_item, names);

        // присваиваем адаптер списку
        listView.setAdapter(adapter);
    }
}
