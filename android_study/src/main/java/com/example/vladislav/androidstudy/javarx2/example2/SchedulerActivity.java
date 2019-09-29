package com.example.vladislav.androidstudy.javarx2.example2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/** Demonstrates a long running operation of the main thread
 * during which a  progressbar is shown
 *
 */
public class SchedulerActivity extends AppCompatActivity {

    private Disposable subscription;
    private ProgressBar progressBar;
    private TextView messagearea;
    private View button;

    public static Intent newIntent(Context context) {
        return new Intent(context, SchedulerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureLayout();
        createObservable();
    }

    private void createObservable() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }

    private void configureLayout() {
        setContentView(R.layout.activity_scheduler);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        messagearea = (TextView) findViewById(R.id.messagearea);
        button  = findViewById(R.id.scheduleLongRunningOperation);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                progressBar.setVisibility(View.VISIBLE);
                Observable.fromCallable(callable).
                        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                        doOnSubscribe(disposable ->
                                {
                                    progressBar.setVisibility(View.VISIBLE);
                                    button.setEnabled(false);
                                    messagearea.setText(messagearea.getText().toString() +"\n" +"Progressbar set visible" );
                                }
                        ).
                        subscribe(getDisposableObserver());
            }
        });
    }

    Callable<String> callable = new Callable<String>() {
        @Override
        public String call() throws Exception {
            return doSomethingLong();
        }
    };

    public String doSomethingLong(){
        SystemClock.sleep(1000);
        return "Hello";
    }

    /**
     * Observer
     * Handles the stream of data:
     */
    private DisposableObserver<String> getDisposableObserver() {
        return new DisposableObserver<String>() {

            @Override
            public void onComplete() {
                messagearea.setText(messagearea.getText().toString() +"\n" +"OnComplete" );
                progressBar.setVisibility(View.INVISIBLE);
                button.setEnabled(true);
                messagearea.setText(messagearea.getText().toString() +"\n" +"Hidding Progressbar" );
            }

            @Override
            public void onError(Throwable e) {
                messagearea.setText(messagearea.getText().toString() +"\n" +"OnError" );
                progressBar.setVisibility(View.INVISIBLE);
                button.setEnabled(true);
                messagearea.setText(messagearea.getText().toString() +"\n" +"Hidding Progressbar" );
            }

            @Override
            public void onNext(String message) {
                messagearea.setText(messagearea.getText().toString() +"\n" +"onNext " + message );
            }
        };
    }
}
