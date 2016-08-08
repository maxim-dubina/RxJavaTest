package com.ciklum.presentations.rxjava.backpressure;

import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subjects.UnicastSubject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.ciklum.presentations.rxjava.Utils.printHead;

/**
 * Created by ccc on 07.07.2016.
 */
public class BackPressure {
    public static void main(String[] args) throws IOException, InterruptedException {
        printHead("Backpressure");
        System.in.read();

        Observable subj = Observable.interval(0, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.computation());
        Subscription subs = subj.observeOn(Schedulers.io()).subscribe(System.out::println);

        System.in.read();
        subs.unsubscribe();
        printHead("Buffer");
        System.in.read();

        subj = Observable.interval(10, TimeUnit.MILLISECONDS).buffer(50).subscribeOn(Schedulers.computation());
        subs = subj.observeOn(Schedulers.io()).subscribe(s -> System.out.println(s));

        System.in.read();
        subs.unsubscribe();
        printHead("Window");
        System.in.read();

        Observable.interval(10, TimeUnit.MILLISECONDS).
                window(50).
                subscribeOn(Schedulers.computation()).
                doOnNext(e -> System.out.println("")).
                observeOn(Schedulers.io()).subscribe(e -> e.subscribe(s -> System.out.print(s + " ")));

        System.in.read();
    }
}
