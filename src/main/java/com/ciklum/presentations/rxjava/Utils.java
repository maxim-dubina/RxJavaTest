package com.ciklum.presentations.rxjava;

import rx.Observable;

/**
 * Created by ccc on 06.07.2016.
 */
public class Utils {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static String ONE = "one";
    public static String TWO = "two";
    public static String THREE = "three";
    public static String FOUR = "four";

    public static String[] ARRAY = {ONE, TWO, THREE, FOUR};

    public static void printHead(String head) {
        System.out.println(ANSI_RED + "-----------------" + head + "------------------" + ANSI_RESET);
    }

    public static void addObserver(Observable<String> observable) {
        observable.subscribe(
                onNext -> System.out.println("On Next: " + onNext),
                onError -> System.out.println("Error: " + onError),
                () -> System.out.println("Completed!")
        );
    }
}
