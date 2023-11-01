package com.example.vladislav.androidstudy.jobs.listviewing.expandablelistview;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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

        mExpandableListView = findViewById(R.id.expandable_list_view);

        String[] strings = new String[]{"1", "2", "3", "4", "5", "6", "1", "2", "3", "4", "5", "6",
                "1", "2", "3", "4", "5", "6", "1", "2", "3", "4", "5", "6", "1", "2", "3", "4", "5",
                "6", "1", "2", "3", "4", "5", "6"};
        /**
         * These will stand for a child view types
         */
        DemoExpandableList2ViewsAdapter.ChildViews[] childTypes =
                new DemoExpandableList2ViewsAdapter.ChildViews[]
                        {DemoExpandableList2ViewsAdapter.ChildViews.TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT,
                                DemoExpandableList2ViewsAdapter.ChildViews.IMAGE_AND_TEXT};
        HashMap<String, List<String>> expandable = new HashMap<String, List<String>>();
        expandable.put("group", Arrays.asList(strings));

        mExpandableAdapter = new DemoExpandableList2ViewsAdapter(Collections.singletonList("group"),
                expandable, childTypes,this);
        mExpandableListView.setAdapter(mExpandableAdapter);
    }
}
