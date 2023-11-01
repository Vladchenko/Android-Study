package com.example.vladislav.androidstudy.jobs.banksdetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.vladislav.androidstudy.R;

import java.util.List;

/**
 * Created by vladislav on 06.02.17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder2> {

    private List<BankDetails> mList;

    public RecyclerViewAdapter() {
    }

    @Override
    public ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bank_office_item, parent, false);
        ViewHolder2 viewHolder2 = new ViewHolder2(v);
        return viewHolder2;
    }

    @Override
    public void onBindViewHolder(ViewHolder2 holder, int position) {
        BankDetails bankDetails = mList.get(position);
        holder.addressTextView.setText(bankDetails.getAddress());
        holder.distanceTextView.setText(bankDetails.getDistance());
        holder.extraOfficeTextView.setText(bankDetails.getName());
        if (bankDetails.getEstimationMark() > -1) {
            holder.estimationTextView.setText("Оценка "
                    + Integer.toString(bankDetails.getEstimationMark()));
        } else {
            holder.estimationTextView.setText("");
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        } else {
            return 0;
        }
    }

    public void update(List list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        TextView addressTextView;
        TextView extraOfficeTextView;
        TextView distanceTextView;
        TextView estimationTextView;
        // No phone number in recycler layout
//        TextView telephoneNTextView;

        public ViewHolder2(View itemView) {
            super(itemView);
            addressTextView =  itemView.findViewById(R.id.address_text_view);
            extraOfficeTextView =  itemView.findViewById(R.id.extra_office_text_view);
            distanceTextView =  itemView.findViewById(R.id.distance_text_view);
            estimationTextView =  itemView.findViewById(R.id.estimation_mark_text_view);
//            telephoneNTextView = itemView.findViewById(R.id.telephoneN_text_view);
            // No need to pass an image, it's already present in an activity.
        }

    }

}