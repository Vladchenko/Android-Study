package com.example.vladislav.androidstudy.banksdetails;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by vladislav on 16.03.17.
 * This class loads a data from a content provider that holds a banks details.
 */

public class BanksDetailsLoader extends CursorLoader {

    private Cursor mCursor;

    public BanksDetailsLoader(Context context) {
        super(context);
    }

    /**
     * в этом onStartLoading() проверяешь, есть ли у тебя старый результат(просто хранишь список
     * с твоими сущностями отделений банков и проверяешь на null). Если старый результат есть, то
     * вызываешь deliverResult(bankOfficeList). Иначе вызываешь forceLoad()
     */
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        // When on wishes to see the estimation marks right the way when they are given, one has to
        // remove this condition and put only forceLoad(); method.
        if (null != mCursor) {
            deliverResult(mCursor);
        } else {
            forceLoad();
        }
    }

    @Override
    public Cursor loadInBackground() {
        return getContext().getContentResolver().query(Uri.parse("content://com.example.vladislav.androidtest/BANKS"), null, null, null, null);
    }
}
