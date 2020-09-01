package com.example.vladislav.androidstudy.jobs.listviewing;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.sqlite.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment displaying a ListView
 *      1. Displaying simple list (setupList)
 *      2. Displaying a list with 2 view types for list items (setupListViewWith2ViewTypes())
 *      3. Displaying a list with 2 view types for list items and providing multi items selection
 *      (setupListViewWith2ViewTypesAndClickable())
 */
public class ListViewPeopleFragment extends Fragment {

    private static final String TAG = ListViewPeopleFragment.class.getSimpleName();

    private ListView mListView;
    private List<Person> mPeopleData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        mPeopleData = makeUpPeopleStubList();
        setupList(view, getActivity());
//        setupListViewWith2ViewTypes(view, getActivity());
        return view;
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

    // Displaying a simple list
    private void setupList(View view, Activity activity) {

        mListView = (ListView) view.findViewById(R.id.list);

        // создаем адаптер
        CustomPeopleArrayAdapter adapter = new CustomPeopleArrayAdapter(activity, mPeopleData);

        // присваиваем адаптер списку
        mListView.setAdapter(adapter);
    }

}
