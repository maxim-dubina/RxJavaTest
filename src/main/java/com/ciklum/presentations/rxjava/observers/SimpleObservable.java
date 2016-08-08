package com.ciklum.presentations.rxjava.observers;

import org.apache.commons.lang3.RandomStringUtils;
import rx.Observable;

import java.util.concurrent.CompletableFuture;

import static com.ciklum.presentations.rxjava.Utils.addObserver;
import static com.ciklum.presentations.rxjava.Utils.printHead;

import static com.ciklum.presentations.rxjava.Utils.*;

/**
 * Created by ccc on 06.07.2016.
 */
public class SimpleObservable {
    /**
     *
     Create — create an Observable from scratch by calling observer methods programmatically
     Defer — do not create the Observable until the observer subscribes, and create a fresh Observable for each observer
     Empty/Never/Throw — create Observables that have very precise and limited behavior
     From — convert some other object or data structure into an Observable
     Interval — create an Observable that emits a sequence of integers spaced by a particular time interval
     Just — convert an object or a set of objects into an Observable that emits that or those objects
     Range — create an Observable that emits a range of sequential integers
     Repeat — create an Observable that emits a particular item or sequence of items repeatedly
     Start — create an Observable that emits the return value of a function
     Timer — create an Observable that emits a single item after a given delay

     * @param args
     */
    public static void main(String[] args) {
        printHead("Empty"); //<- Will call only OnCompleted
        addObserver(Observable.empty());

        printHead("Never");
        addObserver(Observable.never()); //<- Will call nothing
        System.out.println("");

        printHead("Throw"); //<- Will call onError only
        addObserver(Observable.error(new Exception()));

        printHead("Just");
        addObserver(Observable.just(ONE, TWO));

        printHead("Defer vs Just");
        printHead("Just");
        Observable<String> obs = Observable.just(RandomStringUtils.random(5, 'a', 'b', 'c'));
        addObserver(obs);
        addObserver(obs);

        printHead("Defer");
        obs = Observable.defer(() -> Observable.just(RandomStringUtils.random(5, 'a', 'b', 'c')));
        addObserver(obs);
        addObserver(obs);

        printHead("Range");
        addObserver(Observable.range(100, 5).map(Object::toString));

        printHead("Create");
        addObserver(Observable.create(o -> {
            o.onNext("Changed! ");
            o.onCompleted();
        }));

        printHead("FromS");
        printHead("From Array");
        addObserver(Observable.from(ARRAY));

        printHead("From Future");
        addObserver(Observable.from(CompletableFuture.completedFuture("Future")));

    }
}
