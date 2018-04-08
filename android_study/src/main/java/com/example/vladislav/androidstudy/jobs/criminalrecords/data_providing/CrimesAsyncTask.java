package com.example.vladislav.androidstudy.jobs.criminalrecords.data_providing;

import android.os.AsyncTask;
import android.view.View;

import com.example.vladislav.androidstudy.jobs.criminalrecords.Crime;
import com.example.vladislav.androidstudy.jobs.criminalrecords.DBHelper;
import com.example.vladislav.androidstudy.jobs.criminalrecords.ui.CriminalRecordListFragment;

import java.util.List;

/**
 * This class makes a downloading of a crimes from a database, while faking that it does so from a
 * net, by providing a delay while doing it.
 *
 * Problem with this asynctask - when app is rotated, it downloads the data again.
 * Solutions:
 *      1. Use retainInstanceState() in fragment - somehow it doesn't work
 *      2. Save data in onSavedInstanceState() and restore in onRestoreInstanceState() or in onCreate().
 *      But if a list is going to be large - is it good ?
 *      3. Use a global holder (such as in the Application object)
 *
 * PROBLEMS WITH ASYNCTASKS - http://bon-app-etit.blogspot.ru/2013/04/the-dark-side-of-asynctask.html
 *                      - http://blog.danlew.net/2014/06/21/the-hidden-pitfalls-of-asynctask/
 *
 * Created by Влад on 28.03.2018.
 */

public class CrimesAsyncTask extends AsyncTask<Void, Integer, List<Crime>> {

    private DBHelper mDBHelper;
    private CriminalRecordListFragment mRecordListFragment;

    public CrimesAsyncTask(CriminalRecordListFragment recordListFragment, DBHelper dBHelper) {
        this.mRecordListFragment = recordListFragment;
        mDBHelper = dBHelper;
    }

    @Override
    protected List<Crime> doInBackground(Void... params) {
        try {
            // It's like asynctask does some work here
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mDBHelper.getCrimeData(mDBHelper.getReadableDatabase());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mRecordListFragment.getProgressBar().setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(List<Crime> crimes) {
        super.onPostExecute(crimes);
//        mRecordListFragment.setCrimes(crimes);
        mRecordListFragment.getProgressBar().setVisibility(View.INVISIBLE);
        mRecordListFragment.setupRecyclerView(crimes);
    }
}
