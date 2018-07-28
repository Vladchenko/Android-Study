package com.example.vladislav.androidstudy.javarx2.example1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxJava2Example1Activity extends AppCompatActivity {

    private static final String TAG = RxJava2Example1Activity.class.toString();
    private Observable<String> mObservable;
    private Observer mObserver;
    private TextView mTextView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java2_example1);

        mTextView = (TextView)findViewById(R.id.rxjava2_example1_text_view);
        mButton = (Button)findViewById(R.id.rxjava2_example1_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                setupObservable();
//                setupObserver();
//                mObservable.subscribe(mObserver);

                setupBoth();

            }
        });
    }

    void setupObservable() {
//        mObservable = Observable.just(null);    // NPE
//        mObservable = Observable.just("just");
//        mObservable = Observable.just("just", "just2"); // just2 appears
//        mObservable = Observable.fromArray("fromArray", "fromArray2");
//        mObservable = Observable.fromIterable(Arrays.asList("fromIterable", "fromIterable2"));

//        mObservable = Observable.fromCallable(() -> {
//            Thread.sleep(2000);
//            return "fromCallable";
//        });

//        mObservable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
//                observableEmitter.onNext("ObservableOnSubscribe");
//                observableEmitter.onNext("ObservableOnSubscribe2");
//                observableEmitter.onComplete();
//            }
//        });

        // The defer() operator does not create the Observable until the observer subscribes,
        // and create a fresh Observable for each observer.
        mObservable = Observable.defer(() -> Observable.just("defer", "defer2"));
    }

    void setupObserver() {
            mObserver = new Observer() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    Log.i(TAG, "onSubscribe fired: " + disposable.toString());
                }

                @Override
                public void onNext(Object o) {
                    Log.i(TAG, "onNext fired: " + o.toString());
                    mTextView.setText(o.toString());

                }

                @Override
                public void onError(Throwable throwable) {
                    Log.i(TAG, "onError fired: " + throwable.getMessage());
                }

                @Override
                public void onComplete() {
                    Log.i(TAG, "onComplete fired");
                }
            };
    }

    void setupBoth() {
        Observable<Integer> observable = Observable.range(0, 3);
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable disposable) {
                Log.i(TAG, "onSubscribe fired: " + disposable.toString());
            }

            @Override
            public void onNext(Object o) {
                Log.i(TAG, "onNext fired: " + o.toString());
                mTextView.setText(o.toString());

            }

            @Override
            public void onError(Throwable throwable) {
                Log.i(TAG, "onError fired: " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete fired");
            }
        };
        observable.subscribe(observer);
    }

}
