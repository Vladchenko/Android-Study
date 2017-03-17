package com.example.vladislav.androidstudy.banksdetails;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;

import com.example.vladislav.androidstudy.R;

import java.util.ArrayList;
import java.util.List;

public class BanksDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    //    BanksDetailsLoader banksDetailsLoader = new BanksDetailsLoader(this, );
//    private BankOfficeCallbacks mListener;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private List<BankDetails> mBanksDetailsList;
    private View mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);
        mRootView = findViewById(R.id.activity_bank_details);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler);
        getLoaderManager().initLoader(0, null, this);
        mAdapter = new RecyclerViewAdapter();
//        LinearLayout mRootView = (LinearLayout)findViewById(R.layout.activity_bank_details);
        mRecyclerView = (RecyclerView) this.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    // In fact, this method should not belong to this class. It has to be some separate utility class.
    public static BankDetails cursorToBankDetails(Cursor cursor) {
        BankDetails bankDetails = new BankDetails();
        bankDetails.setmAddress(cursor.getString(1));
        bankDetails.setmDistance(cursor.getString(2));
        bankDetails.setmName(cursor.getString(3));
        bankDetails.setmEstimationMark(Integer.parseInt(cursor.getString(4)));
        bankDetails.setmPhoneNumber(cursor.getString(5));
        return bankDetails;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new BanksDetailsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Cursor cursor = (Cursor) data;
        mBanksDetailsList = new ArrayList<>(cursor.getCount());
        cursor.moveToFirst();
        int i = 0;
        while (i != cursor.getCount()) {
            mBanksDetailsList.add(cursorToBankDetails(cursor));
            cursor.moveToNext();
            i++;
        }

        mAdapter.update(mBanksDetailsList);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}