package com.ciklum.presentations.rxjava.operators;

import rx.Observable;

import static com.ciklum.presentations.rxjava.Utils.*;

/**
 * Created by ccc on 06.07.2016.
 */
public class FilteringOperators {
    /**
     *
     Debounce — only emit an item from an Observable if a particular timespan has passed without it emitting another item
     Distinct — suppress duplicate items emitted by an Observable
     ElementAt — emit only item n emitted by an Observable
     Filter — emit only those items from an Observable that pass a predicate test
     First — emit only the first item, or the first item that meets a condition, from an Observable
     IgnoreElements — do not emit any items from an Observable but mirror its termination notification
     Last — emit only the last item emitted by an Observable
     Sample — emit the most recent item emitted by an Observable within periodic time intervals
     Skip — suppress the first n items emitted by an Observable
     SkipLast — suppress the last n items emitted by an Observable
     Take — emit only the first n items emitted by an Observable
     TakeLast — emit only the last n items emitted by an Observable
     */

    public static void main(String[] args) {
        printHead("Filter");
        Observable<String> obs = Observable.from(ARRAY);
        /**
         * Incorrect!
         * obs.filter(e -> !e.equals(THREE));
         * obs.subscribe(..);
         *
         * Correct!
         * obs = obs.filter(e -> !e.equals(THREE));
         * obs.subscribe(..);
         */
        obs.filter(e -> !e.equals(THREE)).subscribe(System.out::println);

        printHead("Skip");
        obs.skip(2).subscribe(System.out::println);

        printHead("SkipLast");
        obs.skipLast(3).subscribe(System.out::println);

        printHead("Take, Skip & SkipLast");
        obs.take(3).skip(1).skipLast(1).subscribe(System.out::println);
    }
}
