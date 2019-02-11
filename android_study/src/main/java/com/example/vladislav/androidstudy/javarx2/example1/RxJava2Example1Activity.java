package com.example.vladislav.androidstudy.javarx2.example1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

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

        mTextView = (TextView) findViewById(R.id.rxjava2_example1_text_view);
        mButton = (Button) findViewById(R.id.rxjava2_example1_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                emptyExample();
//                justExample();
//                justExample_2();
//                justExample_3();
//                justExample_4();
//                justExample_5();
//                justExample_6();
//                intervalDemo();
//                timerDemo();
//                mapDemo();
//                flatmapDemo();
//                filterDemo();
//                intervalRangeDemo();
//                concatMapDemo();
//                nullDemo();
//                someExample();
//                runObservable();
//                runFlowable();
//                combinationOfSeveralOps();
//                fromArrayExample();
//                fromIterableExample();
//                fromCallableExample();
                deferExample();
            }
        });
    }

    private void setupObserver() {
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

    void setupObservable() {

//        mObservable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
//                observableEmitter.onNext("ObservableOnSubscribe");
//                observableEmitter.onNext("ObservableOnSubscribe2");
//                observableEmitter.onComplete();
//            }
//        });

    }

    void runObservable() {
        setupObservable();
        setupObserver();
        mObservable.subscribe(mObserver);
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

    // Doesn't do anything. Maybe one has to add a schedulers
    void runFlowable() {
        setupObservable();
        PublishProcessor<String> pp = PublishProcessor.create();
        Flowable<String> flowable = mObservable
                .toFlowable(BackpressureStrategy.BUFFER);
        flowable.subscribe(pp);
    }

    private void nullDemo() {
        Observable.fromCallable(() -> null)
                .subscribe(System.out::println, Throwable::printStackTrace);
    }

    private void nullDemo2() {
        Observable.just(1)
                .map(v -> null)
                .subscribe(System.out::println, Throwable::printStackTrace);
    }

    /**
     * This is done on a UI thread. Emits only one event.
     */
    private void justExample() {
        Observable<String> helloWorld = Observable.just("Hello world");
        helloWorld.subscribe(line -> mTextView.setText(line));

        // The shorter case of the same doing
//        Observable.just("Hello world")
//                .subscribe(line -> mTextView.setText(line));

        // For multiline code in subscriber
//        Observable.just("Hello world")
//                .subscribe(line -> {
//                    System.out.println(line);
//                    mTextView.setText(line);
//                });
    }

    /**
     * Emits one event and puts it to some textview
     */
    private void justExample_2() {
        Observable.just("one")
                .subscribe(line -> mTextView.setText(line));
    }

    /**
     * Using observable with v, e, () lambda arguments
     */
    private void justExample_3() {
        Observable.just("one", "two", "three", "four", "five")
                .subscribe(v -> System.out.println("Received: " + v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed"));
    }

    /**
     * Demonstrating doOn... methods.
     * <p>
     * Although there is an exception among the emissions, it doesn't come out as an error.
     */
    private void justExample_4() {
        Observable.just("one", "two", new Exception("Oops"), "four", "five")
                .doOnNext(v -> System.out.println("doOnNext: " + v))
                .doOnError(v -> System.out.println("doOnError: " + v))
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("Complete");
                    }
                })
                .subscribe(v -> System.out.println("Received: " + v + "\n"));
    }

    private void justExample_5() {
        Consumer<Integer> onNext = i -> System.out.println("RECEIVED: " + i);
        Consumer<Throwable> onError = Throwable::printStackTrace;
        Action onComplete = () -> System.out.println("Done!");

        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        source.map(String::length)
                .filter(i -> i >= 5)
                .subscribe(onNext,
                        onError,
                        onComplete);
    }

    /**
     * This is done on a worker thread and fetches the result on a UI thread.
     */
    private void justExample_6() {
        Observable.just("Hello world 2")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(line -> mTextView.setText(line));
    }

    /**
     * empty() emits nothing
     */
    private void emptyExample() {
        Observable.empty()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(x -> System.out.println("x = " + x));
    }

    private void mapDemo() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> -1)
                .map(x -> x * 3)  // Multiplies each value in 3
//                .map(number -> (new Random()).nextInt())
                .subscribe(y -> System.out.println(y)); // Here an argument has to be different to
        // a previous one, say y
        // mTextView.setText throws exception saying there is no value for y
    }

    /**
     * Main difference between Map and FlatMap that FlatMap returns an observable itself, so it is
     * used to map over asynchronous operations.
     */
    private void flatmapDemo() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(s -> {
                    return Observable.just(s)
                            .delay(new Random().nextInt(1000), TimeUnit.MILLISECONDS);
                })
                .subscribe(y -> System.out.println(y));
    }

    private void filterDemo() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(x -> x * 3)  // Multiplies each value in 3
                .filter(y -> y < 15 && y > 6)    // Processes only a values less than 15 and bigger than 6
                .subscribe(z -> System.out.println(z));
    }

    /**
     * Prints sequence of integers in increasing manner each second.
     */
    private void intervalDemo() {
        Observable<Long> integers = Observable.interval(1, TimeUnit.SECONDS);
        integers
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(z -> System.out.println(z));
    }

    /**
     * Fires just once. IDK why.
     */
    private void timerDemo() {
        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(i -> System.out.println(i));
    }

    private void intervalRangeDemo() {
        Observable.intervalRange(0, 30, 500, 500, TimeUnit.MILLISECONDS,
                Schedulers.newThread())
                .map(x -> x * 3)  // Multiplies each value in 3
                .blockingSubscribe(z -> System.out.println(z)); // This is done on a main thread
        // Since intervalRange works in a separate thread, it finishes its job before main thread
        // is able to print values. This is why, we need to use blockingSubscribe();
    }

    /**
     * ConcatMap works almost the same as flatMap, but preserves the order of items. But concatMap
     * has one big flaw: it waits for each observable to finish all the work until next one is
     * processed.
     */
    private void concatMapDemo() {
        Random random = new Random();
        Observable.intervalRange(0, 30, 500, 500, TimeUnit.MILLISECONDS,
                Schedulers.io())
                .concatMap(x -> Observable.just(random.nextInt(1000))
                        .delay(x, TimeUnit.MILLISECONDS))  // Multiplies each value in 3
                .blockingSubscribe(y -> System.out.println(y));
    }

    private void someExample() {
        Observable.just("long", "longer", "longest")
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(c -> System.out.print("processing item on thread " + Thread.currentThread().getName() + ", "))
                .subscribeOn(Schedulers.io())
                .map(String::length)
                .subscribe(length -> System.out.println("item length " + length));
    }

    /**
     * 1. Emitting 5 objects
     * 2. Getting a first char from each emission and pass further on
     * 3. Getting only an emissions whose values are bigger than 112 (i.e. bigger than "p")
     * 4. Count the number of these emissions.
     * 5. Print them out.
     */
    private void combinationOfSeveralOps() {
        char ch = 112;
        Observable.just("one", "two", "three", "four", "five")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(x -> (int) x.charAt(0))
                .filter(y -> y > ch)
                .count()
                .subscribe(z -> System.out.println("The number of emissions bigger than 112 (letter \"p\") = " + z));
    }

    void fromArrayExample() {
        mObservable = Observable.fromArray("fromArray", "fromArray2");
        mObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(x -> System.out.println("Value = " + x));
    }

    /**
     * Demonstrates fromIterable() performing. Returns true if any of an emissions has "Gamma".
     */
    private void fromIterableExample() {
        List<String> items = Arrays.asList("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        Observable.fromIterable(items)
                .contains("Gamma")
                .subscribe(result -> System.out.println(result));
    }

    private void fromCallableExample() {
        Observable.fromCallable(() -> {
            Thread.sleep(2000);
            return "fromCallable fired";
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result -> System.out.println(result));
    }

    private void deferExample() {
//         The defer() operator does not create the Observable until the observer subscribes,
//         and create a fresh Observable for each observer.
        mObservable.defer(() -> Observable.just("defer", "defer2"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result -> System.out.println(result));
    }

}
