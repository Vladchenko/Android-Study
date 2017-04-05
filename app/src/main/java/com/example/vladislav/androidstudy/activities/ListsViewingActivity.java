package com.example.vladislav.androidstudy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        listView = (ListView) findViewById(R.id.list);
        listView.addFooterView(new View(this.getBaseContext()));

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1,
                R.layout.my_list_item,
                names);

        // присваиваем адаптер списку
        listView.setAdapter(adapter);
    }
}
