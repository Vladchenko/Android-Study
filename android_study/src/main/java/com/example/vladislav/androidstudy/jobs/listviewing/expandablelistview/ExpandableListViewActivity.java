package com.example.vladislav.androidstudy.jobs.listviewing.expandablelistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.vladislav.androidstudy.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ExpandableListViewActivity extends AppCompatActivity {

    private BaseExpandableListAdapter mExpandableAdapter;
    private ExpandableListView mExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view);

        mExpandableListView = (ExpandableListView) findViewById(R.id.expandable_list_view);

//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
//        mExpandableListView.addItemDecoration(decoration);

        String[] strings = new String[]{"1", "2", "3", "4", "5", "6"};
        HashMap<String, List<String>> expandable = new HashMap<String, List<String>>();
        expandable.put("group", Arrays.asList(strings));

        mExpandableAdapter = new DemoExpandableListAdapter(Collections.singletonList("group"),
                expandable, this);
        mExpandableListView.setAdapter(mExpandableAdapter);
    }
}
