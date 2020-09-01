package com.example.vladislav.androidstudy.jobs.criminalrecords.data_providing;

import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;

import com.example.vladislav.androidstudy.jobs.criminalrecords.Crime;
import com.example.vladislav.androidstudy.jobs.criminalrecords.DBHelper;
import com.example.vladislav.androidstudy.jobs.criminalrecords.ParcelableCrimesList;
import com.example.vladislav.androidstudy.jobs.currency.beans.CurrenciesContainer;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CrimesRxLoader {

//    private Context mContext;
    private DBHelper mDBHelper;

    public CrimesRxLoader(DBHelper dbHelper) {
//        mContext = context;
        mDBHelper = dbHelper;
    }

//    public ParcelableCrimesList downloadCrimes() {
//        Uri contentUri = Uri.parse("content://com.example.vladislav.androidstudy.jobs." +
//                "criminalrecords.data_providing.CrimesContentProvider/crimes");
////        return new CursorLoader(mContext, contentUri, null, null, null, null);
//        return Observable.fromIterable(mDBHelper.getCrimeData(mDBHelper.getReadableDatabase()))
////                .flatMapIterable(line -> line)
//                .toList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//
////                .toCompletable()
//                .subscribe(line -> {return line;});
//    }

    private Observable<Crime> downloadCrimes() {
        return Observable.fromIterable(mDBHelper.getCrimeData(mDBHelper.getReadableDatabase()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public List<Crime> getCrimes() {
        return downloadCrimes().toList().blockingGet();
    }
}
