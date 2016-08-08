package com.ciklum.presentations.rxjava.operators;

import com.ciklum.presentations.rxjava.Utils;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import static com.ciklum.presentations.rxjava.Utils.ARRAY;
import static com.ciklum.presentations.rxjava.Utils.printHead;

/**
 * Created by ccc on 06.07.2016.
 */
public class CombiningOperators {
    /**
     *
     And/Then/When — combine sets of items emitted by two or more Observables by means of Pattern and Plan intermediaries
     CombineLatest — when an item is emitted by either of two Observables, combine the latest item emitted by each Observable via a specified function and emit items based on the results of this function
     Join — combine items emitted by two Observables whenever an item from one Observable is emitted during a time window defined according to an item emitted by the other Observable
     Merge — combine multiple Observables into one by merging their emissions
     StartWith — emit a specified sequence of items before beginning to emit the items from the source Observable
     Switch — convert an Observable that emits Observables into a single Observable that emits the items emitted by the most-recently-emitted of those Observables
     Zip — combine the emissions of multiple Observables together via a specified function and emit single items for each combination based on the results of this function
     * @param args
     */
    public static void main(String[] args) {
        printHead("Zip");
        Observable.zip(Observable.range(1, 10), Observable.from(ARRAY), (integer, string) -> integer + " " + string).subscribe(System.out::println);

        printHead("ZipWith");
        Observable.range(1, 10).zipWith(Observable.from(ARRAY), (i, s) -> i + " " + s).subscribe(System.out::println);

        printHead("Merge/MergeWith");
        Observable.range(1, 5).mergeWith(Observable.range(100, 5)).subscribe(System.out::println);

        printHead("CombineLatest");
        Subject sub1 = PublishSubject.create();
        Subject sub2 = PublishSubject.create();

        Observable.combineLatest(sub1, sub2, (o, o2) -> o + " " + o2).subscribe(System.out::println);

        sub1.onNext(1);
//        sub1.onNext(3); <-- Warning!
        sub2.onNext("A");

        sub1.onNext(2);

        sub2.onNext("B");
        sub2.onNext("B");
        sub2.onNext("C");

        sub1.onNext(4);
    }
}
