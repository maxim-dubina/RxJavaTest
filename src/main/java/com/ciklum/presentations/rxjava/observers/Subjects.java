package com.ciklum.presentations.rxjava.observers;

import rx.Observable;
import rx.subjects.*;

import static com.ciklum.presentations.rxjava.Utils.*;

/**
 * Created by ccc on 06.07.2016.
 */
public class Subjects {
    /**
     * AsyncSubject - Subject that publishes only the last item observed to each Observer that has subscribed, when the source Observable completes.
     * BehaviorSubject - Subject that emits the most recent item it has observed and all subsequent observed items to each subscribed Observer.
     * PublishSubject - Subject that, once an Observer has subscribed, emits all subsequently observed items to the subscriber.
     * ReplaySubject - Subject that buffers all items it observes and replays them to any Observer that subscribes.
     * @param args
     */
    public static void main(String[] args) {
        printHead("PublishSubject");
        Subject<String, String> subj = PublishSubject.create();
        subj.onNext(ONE);     // <-- we skip all messages before subscribers
        subj.onNext(TWO);     // <-- and this
        addObserver(subj);
        subj.onNext(THREE);
        subj.onNext(FOUR);

        printHead("BehaviorSubject(..)");
        subj = BehaviorSubject.create();
        subj.onNext(ONE);     // <-- we skip some messages before subscribers
        subj.onNext(TWO);     // <-- but not this
        addObserver(subj);
        subj.onNext(THREE);
        subj.onNext(FOUR);

        printHead("ReplaySubject");
        subj = ReplaySubject.create();
        subj.subscribe(onNext -> System.out.println("First: " + onNext) );
        subj.onNext(ONE);
        subj.onNext(TWO);
        System.out.println("-----------Add new observer---------");
        subj.subscribe(onNext -> System.out.println("Second: " + onNext));
        subj.onNext(THREE);
        subj.onNext(FOUR);

        printHead("AsyncSubject");
        subj = AsyncSubject.create();
        addObserver(subj);
        subj.onNext(ONE);
        subj.onNext(TWO);
        subj.onNext(THREE);
        subj.onNext(FOUR);
        subj.onCompleted();
    }
}
