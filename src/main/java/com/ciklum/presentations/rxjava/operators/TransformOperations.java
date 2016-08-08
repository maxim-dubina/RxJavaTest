package com.ciklum.presentations.rxjava.operators;

import org.apache.commons.lang3.StringUtils;
import rx.Observable;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.ciklum.presentations.rxjava.Utils.*;

/**
 * Created by ccc on 06.07.2016.
 */
public class TransformOperations {
    /**
     *
     Buffer — periodically gather items from an Observable into bundles and emit these bundles rather than emitting the items one at a time
     +FlatMap — transform the items emitted by an Observable into Observables, then flatten the emissions from those into a single Observable
     +GroupBy — divide an Observable into a set of Observables that each emit a different group of items from the original Observable, organized by key
     +Map — transform the items emitted by an Observable by applying a function to each item
     +Scan — apply a function to each item emitted by an Observable, sequentially, and emit each successive value
     +Reduce — apply a function to each item emitted by an Observable, sequentially, and emit the final value
     Window — periodically subdivide items from an Observable into Observable windows and emit these windows rather than emitting the items one at a time
     */

    public static void main(String[] args) {
        printHead("Map");
        Observable<String> obs = Observable.from(ARRAY);

        obs.map(i -> "new " + i).subscribe(System.out::println);

        printHead("FlatMap vs Map");
        Observable<String> obs2 = Observable.just(ONE, TWO);
        Observable<String> map = obs.map(a -> "new " + a);
        obs2.flatMap(i-> map, (o, o2) -> StringUtils.upperCase(o) + " " + o2).subscribe(System.out::println);

        printHead("GroupBy");
        Observable.range(1, 10).
                groupBy(v -> v % 3).
                forEach(o -> o.subscribe(e -> System.out.println("The set number: " + o.getKey() + ". Contains element: " + e)));

        printHead("Scan");
        Observable.range(1, 10).
                scan((first, second) -> {
                    System.out.print("Was: " + second + " ");
                    return first + second;
                }).subscribe(e -> System.out.println("Now: " + e));

        printHead("Reduce");
        Observable.range(1, 10).reduce((first, second) -> first + second).subscribe(System.out::println);

    }
}
