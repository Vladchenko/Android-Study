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
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

import static java.lang.Thread.sleep;

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

//                nullDemo2();
//                emptyExample();
//                justExample();
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
//                someExample();
//                runObservable();
//                runFlowable();
//                combinationOfSeveralOps();
//                fromArrayExample();
//                fromIterableExample();
//                fromCallableExample();
//                deferExample();

//                operatorsExample();

//                singleExample2();
//                singleExample3();

//                maybeExample2();

//                completableExample();
//                completableExample2();

//                observableExample();
            }
        });
    }

    //region Observable
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


    // Doesn't do anything. Maybe one has to add a schedulers
    void runFlowable() {
        setupObservable();
        PublishProcessor<String> pp = PublishProcessor.create();
        Flowable<String> flowable = mObservable
                .toFlowable(BackpressureStrategy.BUFFER);
        flowable.subscribe(pp);
    }

    // Throws NullPointerException: Callable returned null
    private void nullDemo() {
        Observable.fromCallable(() -> null)
                .subscribe(System.out::println, Throwable::printStackTrace);
    }

    // Throws NullPointerException: The mapper function returned a null value.
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

        // The shorter representation of the same doing
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
                        System.out.println("Completed");
                    }
                })
                .subscribe(v -> System.out.println("Received: " + v + "\n"));
    }

    /**
     * Example using Consumers and Action
     */
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
            sleep(2000);
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

    /**
     * Showing some operators operating
     */
    private void operatorsExample() {
        // Skipping one emission
        Observable.just("defer", "defer2")
                .skip(1)
//                .skipLast(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result -> System.out.println(result));

        // Prints numbers from 0 to 3
        Observable.range(0, 3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result -> System.out.println(result));

        // Prints "Alpha", "Beta", "Gamma"
        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .take(3)
                .subscribe(s -> System.out.println("take RECEIVED: " + s));

        //  Emits numbers every 300 milliseconds, but take() emissions for only 2 seconds
        // Ignores .just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .interval(400, TimeUnit.MILLISECONDS)
                .take(3, TimeUnit.SECONDS)
                .subscribe(s -> System.out.println("interval take example RECEIVED: " + s));

        Observable.range(1,100)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .takeWhile(i -> i < 5)  // filter does the same, but
                // filter will remove all items from the stream that do not satisfy the condition.
                // takeWhile will abort the stream on the first occurrence of an item which does not satisfy the condition.
                .subscribe(i -> System.out.println("takeWhile example RECEIVED: " + i));
    }
    //endregion Observable

    //region Single
    /**
     * The Single must have one emission, and you should prefer it if you only have one emission to
     * provide. This means that instead of using Observable.just("Alpha"), you should try to use
     * Single.just("Alpha") instead
     */
    private void singleExample() {
        Single.just("Alpha")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> System.out.println(result));
    }

    private void singleExample2() {
        Single.just("singleExample")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(System.out::println,
                        Throwable::printStackTrace);    // These are lambdas for onSuccess and onError
    }

    /**
     * This example doesn't operate the way it should
     */
    private void singleExample3() {
        Observable<String> source = Observable.just("", "Alpha", "Beta", "Gamma");
        source.first("Replaced") //returns Maybe
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(System.out::println);
        //  ... if you instead want a filtering operator, you may have better luck with Take(1) or
        // ElementAt(0).    http://reactivex.io/documentation/operators/first.html
    }
    //endregion Single

    //region Maybe
    /**
     * If there are 0 or 1 emissions, you will want to use Maybe
     */
    private void maybeExample() {
        Maybe.just("maybeExample")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> System.out.println(result));
    }

    /**
     * Observable.firstElement() emits first or no element, if it is absent, thus emitting Maybe<T>
     */
    private void maybeExample2() {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma");
        source.firstElement() //returns Maybe
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // Having onSuccess(), onError(), onComplete() lambdas provided
                .subscribe(System.out::println,
                        Throwable::printStackTrace,
                        () -> System.out.println("Done!"));
//                .subscribe(System.out::println);
    }

    /**
     * Maybe that emits no emissions.
     */
    private void maybeExample3() {
        Maybe.empty()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> System.out.println(result));
    }
    //endregion Maybe

    //region Completable
    private void completableExample() {
        Completable.complete();
        System.out.println("Completable.complete() finished");
    }

    private void completableExample2() {
        Completable.fromRunnable(
                () -> runProcess())
                .subscribe(() -> System.out.println("Done!")
                );
    }

    public static void runProcess() {
        //run process here
    }
    //endregion Completable

    //region Disposable
    private void observableExample() {
        Observable<Long> seconds = Observable.interval(1, TimeUnit.SECONDS);
        Disposable disposable = seconds.subscribe(l -> System.out.println("Received: " + l));
        //sleep 5 seconds
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //dispose and stop emissions
        disposable.dispose();
        //sleep 5 seconds to prove there are no more emissions
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * Here, we let Observable.interval() run for five seconds with an Observer, but we save
         * the Disposable returned from the subscribe() method. Then we call the Disposable's
         * dispose() method to stop the process and free any resources that were being used. Then,
         * we sleep for another five seconds just to prove that no more emissions are happening.
         */
    }
    //endregion Disposable
}