package com.example.vladislav.androidstudy.jobs.listviewing.spacestars;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Fragment displaying a ListView (list os space stars)
 *      1. Displaying simple list
 *      2. Displaying a list with 2 view types for list items
 *      3. Displaying a list with 2 view types for list items and providing multi items selection
 */
public class ListViewStarsFragment extends Fragment {

    private ListView mListView;

    List<String> mStarsNames = new ArrayList<>(Arrays.asList("Polaris", "Aldebaran", "Vega",
            "Ursa Majoris", "Sirius", "Sun", "Antares", "VY Canis Majoris", "Rigel", "Betelgeuse",
            "Alpha Centauri"));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        setupList(view, getActivity());
//        setupListViewWith2ViewTypes(view, getActivity());
//        setupListViewWith2ViewTypesAndClickable(view, getActivity());
        // Inflate the layout for this fragment
        return view;
    }

    // Displaying a simple list
    private void setupList(View view, Activity activity) {

        mListView = (ListView) view.findViewById(R.id.list);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                R.layout.my_list_item_selector, mStarsNames);

        // присваиваем адаптер списку
        mListView.setAdapter(adapter);
    }

    // Making up a list with custom ArrayAdapter that has 2 different layout types for its list items
    // To change a look of a list item, one have to implement custom adapter.
    private void setupListViewWith2ViewTypes(View view, Activity activity) {

        // Strangely, list doesn't scroll
        mListView = (ListView) view.findViewById(R.id.list);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // создаем адаптер
        CustomArrayAdapter adapter = new CustomArrayAdapter(activity, mStarsNames);

        // присваиваем адаптер списку
        mListView.setAdapter(adapter);
    }

    // Making up a list with 2 different layout types for its list items and implementing clicking
    private void setupListViewWith2ViewTypesAndClickable(View view, Activity activity) {

        // Strangely, list doesn't scroll
        mListView = (ListView) view.findViewById(R.id.list);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        final Map<Integer, View> views = new HashMap<>();

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                R.layout.my_list_item_selector, mStarsNames);

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
