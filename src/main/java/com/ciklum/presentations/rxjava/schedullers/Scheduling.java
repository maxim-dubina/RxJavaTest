package com.ciklum.presentations.rxjava.schedullers;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import static com.ciklum.presentations.rxjava.Utils.*;
import static rx.Observable.create;
import static rx.Observable.from;
import static rx.Observable.just;

/**
 * Created by ccc on 07.07.2016.
 */
public class Scheduling {

    public static void main(String[] args) throws InterruptedException {
        printHead("Without scheduling");
        from(ARRAY).
                subscribe(str -> System.out.println("Message: " + str + ". Thread: " + Thread.currentThread().getName()));

        printHead("SubscribeOn");
        from(ARRAY).
                subscribeOn(Schedulers.io()).
                subscribe(str -> System.out.println("Message: " + str + ". Thread: " + Thread.currentThread().getName()));

        Thread.sleep(100);
        printHead("SubscribeOn Extended");
        Observable<String> obs = just(ONE);
        obs = obs.doOnNext(str -> System.out.println("Message: " + str + ". Thread: " + Thread.currentThread().getName())).
                subscribeOn(Schedulers.computation()).
                doOnNext(str -> System.out.println("Message: " + str + ". Thread: " + Thread.currentThread().getName()));

        obs.subscribe(str -> System.out.println("Message: " + str + ". Thread: " + Thread.currentThread().getName()));
        Thread.sleep(100);

        printHead("ObserveOn");
        obs = just(ONE, TWO);
        obs = obs.doOnNext(str -> System.out.println("Message: " + str + ". Thread: " + Thread.currentThread().getName())).
                observeOn(Schedulers.newThread());

        obs.subscribe(str -> System.out.println("Message: " + str + ". Thread: " + Thread.currentThread().getName()));
        Thread.sleep(100);

        printHead("ObserveOn Extended");
        obs = just(ONE, TWO);
        obs = obs.doOnNext(str -> System.out.println("obs1 Message: " + str + ". Thread: " + Thread.currentThread().getName())).
                observeOn(Schedulers.newThread()).
                doOnNext(str -> System.out.println("obs2 Message: " + str + ". Thread: " + Thread.currentThread().getName()));
                //observeOn(Schedulers.io());
        obs.subscribe(str -> System.out.println("subs1 Message: " + str + ". Thread: " + Thread.currentThread().getName()));
        Thread.sleep(100);

        System.out.println();

        obs.observeOn(Schedulers.io()).subscribe(str -> System.out.println("subs2 Message: " + str + ". Thread: " + Thread.currentThread().getName()));
        Thread.sleep(100);

        printHead("ObserveOn & SubscribeOn");

        Observable obs2 = create(subs -> {
            System.out.println("Inside Observable. Thread: " + Thread.currentThread().getName());
            subs.onNext(ONE);
            subs.onNext(TWO);
        }).subscribeOn(Schedulers.io()).doOnNext(str -> System.out.println("Outside Observable. Thread: " + Thread.currentThread().getName()));
        obs2.observeOn(Schedulers.computation()).subscribe(str -> System.out.println("Message: " + str + ". Thread: " + Thread.currentThread().getName()));

        Thread.sleep(100);
        System.out.println();

        obs2.observeOn(Schedulers.computation()).subscribe(str -> System.out.println("Message: " + str + ". Thread: " + Thread.currentThread().getName()));

        //obs2.observeOn(Schedulers.computation()).doOnNext(str -> System.out.println("Outside Observable. Thread: " + Thread.currentThread().getName());

        Thread.sleep(100);
    }


}
