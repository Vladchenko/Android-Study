package com.example.vladislav.androidstudy.kotlin.study.coursera.multithreadingandnetworkinteractioncourse.week1;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Yanchenko Vladislav
 * @since 16.03.2021
 */
class CounterCallable implements Callable {

    private int peopleNumberOnFloor;
    private int peopleCounter;

    public CounterCallable(int peopleNumberOnFloor) {
        this.peopleNumberOnFloor = peopleNumberOnFloor;
    }

    @Override
    public Object call() throws Exception {
        synchronized(this) {
            peopleCounter += peopleNumberOnFloor;
            return peopleCounter;
        }
    }
}
