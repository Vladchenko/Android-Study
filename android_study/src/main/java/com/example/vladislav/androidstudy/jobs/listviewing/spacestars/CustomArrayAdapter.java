package com.example.vladislav.androidstudy.jobs.listviewing.spacestars;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

import java.util.List;

/**
 * Adapter that provides two types of layouts for items
 *
 * Created by Влад on 19.02.2018.
 */

public class CustomArrayAdapter extends BaseAdapter {

        private List<String> mData;
        private LayoutInflater mInflater;

        public CustomArrayAdapter(Activity activity, List<String> data) {
            mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mData = data;
        }

        public void addItem(final String item) {
            mData.add(item);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            System.out.println("getView " + position + " " + convertView);
            ViewHolder holder = null;
            if (convertView == null) {
                if (position % 2 == 0) {
                    convertView = mInflater.inflate(R.layout.my_list_item, null);
                } else {
                    convertView = mInflater.inflate(R.layout.my_list_item2, null);
                }
                holder = new ViewHolder();
                holder.textView = convertView.findViewById(R.id.textView1);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.textView.setText(mData.get(position));
            return convertView;
        }
}
