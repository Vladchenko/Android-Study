package com.example.vladislav.androidstudy.jobs.listviewing;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.sqlite.Person;

import java.util.List;

/**
 * Adapter that provides two types of layouts for items
 * <p>
 * Created by Влад on 19.02.2018.
 */

public class CustomPeopleArrayAdapter extends BaseAdapter {

    private List<Person> mData;
    private LayoutInflater mInflater;

    public CustomPeopleArrayAdapter(Activity activity, List<Person> data) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = data;
    }

    public void addItem(final Person item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Person getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("getView " + position + " " + convertView);
        PersonViewHolder personViewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.person_list_view_item, null);
            personViewHolder = new PersonViewHolder();
            personViewHolder.name_text_view = (TextView) convertView.findViewById(R.id.name_text_view);
            personViewHolder.lastname_text_view = (TextView) convertView.findViewById(R.id.lastname_text_view);
            personViewHolder.number_text_view = (TextView) convertView.findViewById(R.id.person_number_text_view);
            personViewHolder.email_text_view = (TextView) convertView.findViewById(R.id.person_email_text_view);
            convertView.setTag(personViewHolder);
        } else {
            personViewHolder = (PersonViewHolder) convertView.getTag();
        }
        personViewHolder.name_text_view.setText(mData.get(position).getName());
        personViewHolder.lastname_text_view.setText(mData.get(position).getLastName());
        personViewHolder.number_text_view.setText(mData.get(position).getCellPhoneNumber());
        personViewHolder.email_text_view.setText(mData.get(position).getEmail());
        return convertView;
    }

    class PersonViewHolder {
        public TextView name_text_view;
        public TextView lastname_text_view;
        public TextView number_text_view;
        public TextView email_text_view;
    }
}
