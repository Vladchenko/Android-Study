package com.example.vladislav.androidstudy.jobs.listviewing.expandablelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

import java.util.List;
import java.util.Map;

public class DemoExpandableListAdapter extends BaseExpandableListAdapter {

    private List<String> mGroupDataset;
    private Map<String, List<String>> mChildrenDataset;
    private Context mContext = null;

    public DemoExpandableListAdapter(List<String> groupDataset,
            Map<String, List<String>> childrenDataset,
                                     Context context) {
        mGroupDataset = groupDataset;
        mChildrenDataset = childrenDataset;
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        return mGroupDataset.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mChildrenDataset.get(mGroupDataset.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return mGroupDataset.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mChildrenDataset.get(mGroupDataset.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .from(mContext).inflate(R.layout.expandable_list_group_view, null);
        }
//        view.setBackground(mContext.getResources().getDrawable(R.drawable.customborder));
        TextView groupTextView = view.findViewById(R.id.group_text_view);
        groupTextView.setText(mGroupDataset.get(i));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .from(mContext).inflate(R.layout.expandable_list_child_view, null);
        }
        // Setting a custom border
//        view.setBackground(mContext.getResources().getDrawable(R.drawable.customborder));
        TextView childTextView = view.findViewById(R.id.group_text_view);
        childTextView.setText(mChildrenDataset.get(mGroupDataset.get(i)).get(i1));
        return view;
    }

    // Making this method to return true, makes a childs to have a dividers also.
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
