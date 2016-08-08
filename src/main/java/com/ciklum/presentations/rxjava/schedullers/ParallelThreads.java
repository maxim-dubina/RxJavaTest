package com.ciklum.presentations.rxjava.schedullers;

import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.util.Random;

import static com.ciklum.presentations.rxjava.Utils.ARRAY;
import static com.ciklum.presentations.rxjava.Utils.printHead;
import static java.lang.Thread.sleep;

/**
 * Created by ccc on 07.07.2016.
 */
public class ParallelThreads {

    public static void main(String[] args) throws InterruptedException, IOException {
        printHead("No threads");
        ParallelThreads threads = new ParallelThreads();

        printHead("Four threads");
        Observable.Transformer<String, String> newComputationThreads = obs -> obs.subscribeOn(Schedulers.newThread()).observeOn(Schedulers.computation());
        Observable.Transformer<String, String> ioThread = obs -> obs.subscribeOn(Schedulers.io());
        threads.template(newComputationThreads, ioThread, ioThread, ioThread);

        sleep(20000);
    }

    private void template(Observable.Transformer<String, String> transformer,
                          Observable.Transformer<String, String> firstObsTransformer,
                          Observable.Transformer<String, String> secondObsTransformer,
                          Observable.Transformer<String, String> thirdObsTransformer) throws InterruptedException {
        Observable<String> obs = Observable.combineLatest(createObservable("Observable1 ", firstObsTransformer),
                createObservable("Observable2 ", secondObsTransformer),
                createObservable("Observable3", thirdObsTransformer),
                (s, s2, s3) -> {
                    System.out.println("Main Observable. Thread: " + Thread.currentThread().getName());
                    return s + s2 + s3;
                })
                .doOnNext(s ->  System.out.println("Before transform. Thread: " + Thread.currentThread().getName()))
                .compose(transformer)
                .doOnNext(s -> System.out.println("After transform. Thread: " + Thread.currentThread().getName()));


        obs.subscribe(str -> System.out.println("Message: " + str + ". Thread: " + Thread.currentThread().getName()));
        sleep(1500);
    }

    private Observable<String> createObservable(final String name, Observable.Transformer<String, String> transformer) throws InterruptedException {
        Observable<String> result = Observable.create(subscriber -> {
            System.out.println("Inside: " + name + " Thread: " + Thread.currentThread().getName());
            try {
                sleep(new Random().nextInt(15000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " on thread: " + Thread.currentThread().getName() + " completed!");
            subscriber.onNext(name);
            subscriber.onCompleted();
        });

        if (transformer != null) {
            return result.compose(transformer);
        }
        return result;
    }

}
