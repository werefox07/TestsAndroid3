package com.geekbrains.lesson2;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

// Здесь будут рассмотрены условные операторы
public class RxJavaTest6 {


    // Этот оператор определяет, соответствуют ли все элементы, испускаемые Observable некоторым критериям.
    @Test
    public void testAll() {
        Observable.just(0, 1, 2, 3, 4, 0, 6, 0)
                .all(item -> item > 0)
                .subscribe((Boolean b, Throwable t) -> {
                    System.out.println(b);
                });
    }

    // Когда вы передаете несколько исходных Observables в amb(),
    // он будет проходить через выбросы и уведомления ровно одной из этих Observables:
    // первой, которая отправляет уведомление в Amb, либо отправляя элемент,
    // либо отправляя уведомление onError или onCompleted,
    // Amb будет игнорировать и сбрасывать выбросы и уведомления всех других источников Observables.
    // Итог: кто первый начнет испускать данные того и тапки, другие будут игнорироваться.
    @Test
    public void testAmb() throws InterruptedException {
        Observable<Integer> observable1 = Observable.timer(2, TimeUnit.SECONDS)
                .flatMap((Function<Long, ObservableSource<Integer>>) aLong -> Observable.just(10, 20, 30, 40, 50));

        Observable<Integer> observable2 = Observable.timer(1, TimeUnit.SECONDS)
                .flatMap((Function<Long, ObservableSource<Integer>>) aLong -> Observable.just(100, 200, 300, 400, 500));

        Observable.ambArray(observable1, observable2)
                .subscribe(System.out::println);

        Thread.sleep(4000);
    }

    @Test
    public void testContain() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .contains(10)
                .subscribe(System.out::println);
    }

    // для проверки запускаем несколько раз
    @Test
    public void testDefaultInEmpty() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                int num = (int) (Math.random() * 10);
                if (num % 2 == 0) {
                    emitter.onNext(num);
                }
                emitter.onComplete();
            }
        })
                .defaultIfEmpty(-10)
                .subscribe(System.out::println);
    }

    // Этот оператор определяет, испускают ли два Observable одну и ту же последовательность элементов.
    @Test
    public void testSequenceEqual() {
        Observable<Integer> observable1 = Observable
                .just(1, 2, 3, 4, 5, 6);

        Observable<Integer> observable2 = Observable
                .just(1, 2, 3, 4, 5, 6);

        Observable.sequenceEqual(observable1, observable2)
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        System.out.println("Are the two sequences equal: " + aBoolean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    // Этот оператор сбрасывает данные, испускаемые от Observable, пока второй Observable не испустит данные.
    // т.е. пока observable2 не начнет выдавать данные observable1 будет сбрасывать свои данные.
    // есть противоположный оператор takeUntil() который наоборот будет сбрасывать данные у observable1 пока observable2
    // не начнет испускаеть данные.
    @Test
    public void testSkipUntil() {
        Observable<Integer> observable1 = Observable
                .create(emitter -> {
                    for(int i=0; i<= 6; i++) {
                        Thread.sleep(1000);
                        emitter.onNext(i);
                    }
                    emitter.onComplete();
                });

        Observable<Integer> observable2 = Observable
                .timer(3, TimeUnit.SECONDS)
                .flatMap(__ -> Observable.just(11, 12, 13, 14, 15, 16));

        observable1.skipUntil(observable2)
                .subscribe(System.out::println);
    }

    // Этот оператор будет отбрасывать элементы, испускаемые Observable, пока указанное условие не станет ложным.
    // Есть противоположный оператор takeWhile() который будет отбрасывать элементы когда условаие станет ложным.
    @Test
    public void testSkipWhile() {
        Observable
                .create(emitter -> {
                    for(int i=0; i<= 6; i++) {
                        Thread.sleep(1000);
                        emitter.onNext(i);
                    }
                    emitter.onComplete();
                })
                .skipWhile(new Predicate<Object>() {
                    @Override
                    public boolean test(Object o) throws Exception {
                        return (((Integer)o < 2));
                    }
                })
                .subscribe(System.out::println);
    }
}
