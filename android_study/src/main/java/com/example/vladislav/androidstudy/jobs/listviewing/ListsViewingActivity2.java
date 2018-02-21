package com.example.vladislav.androidstudy.jobs.listviewing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.vladislav.androidstudy.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This activity demonstrates operating an ArrayAdapter
 */
public class ListsViewingActivity2 extends AppCompatActivity {

    ListView mListView;

    List<String> names = new ArrayList<>(Arrays.asList("Polaris", "Aldebaran", "Vega",
            "Ursa Majoris", "Sirius", "Sun", "Antares", "VY Canis Majoris", "Rigel", "Betelgeuse",
            "Alpha Centauri"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists_viewing);
//        setupListViewWith2ViewTypes();
        setupListViewWith2ViewTypesAndClickable();
    }

    // Making up a list with custom ArrayAdapter that has 2 different layout types for its list items
    private void setupListViewWith2ViewTypes() {

        // Strangely, list doesn't scroll
        mListView = (ListView) findViewById(R.id.list);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // создаем адаптер
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, names);

        // присваиваем адаптер списку
        mListView.setAdapter(adapter);
    }

    // Making up a list with 2 different layout types for its list items and implemeting clicking
    private void setupListViewWith2ViewTypesAndClickable() {

        // Strangely, list doesn't scroll
        mListView = (ListView) findViewById(R.id.list);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        final Map<Integer, View> views = new HashMap<>();

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_list_item_selector, names);

        // присваиваем адаптер списку
        mListView.setAdapter(adapter);
        // This click listener makes a selection of only 1 item
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                view.setSelected(true);
//            }
//        });
        // This click listener makes a selection of a several items
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!views.containsKey(position)) {
                    views.put(position, view);

                } else {
                    views.get(position).setSelected(false);
                    views.remove(position);

                    mListView.invalidate();
                }
                for (int pos:views.keySet()) {
                    views.get(pos).setSelected(true);
                }
            }
        });
    }
}
