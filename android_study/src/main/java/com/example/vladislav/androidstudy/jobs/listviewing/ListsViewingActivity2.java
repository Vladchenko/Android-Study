package com.example.vladislav.androidstudy.jobs.listviewing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.vladislav.androidstudy.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListsViewingActivity2 extends AppCompatActivity {

    ListView listView;

    List<String> names = new ArrayList<>(Arrays.asList("Polaris", "Aldebaran", "Vega",
            "Ursa Majoris", "Sirius", "Sun", "Antares", "VY Canis Majoris", "Rigel", "Betelgeuse",
            "Alpha Centauri"));

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
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, names);

        // присваиваем адаптер списку
        listView.setAdapter(adapter);
    }
}
