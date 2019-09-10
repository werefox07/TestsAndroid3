package com.geekbrains.lesson2;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;

//Здесь будут рассмотрены операторы комбинирования
public class RxJavaTest4 {

    // Оператор CombineLatest объединяет результат двух и более Observable. Он будет выполнятся пока не выполнится последний Observable
    // В момент когда приходят данные из любого Observable он берет последние данные у другого, далее применяет фунцкию
    // и результат отправляет дальше.
    @Test
    public void testCombineLatest() throws InterruptedException {
        Observable.combineLatest(
                Observable.intervalRange(1,5 ,0, 500, TimeUnit.MILLISECONDS),
                Observable.intervalRange(6,5 ,0, 700, TimeUnit.MILLISECONDS),
                new BiFunction<Long, Long, String>() {
                    @Override
                    public String apply(Long integer, Long integer2) throws Exception {
                        return "Observable1: " + integer + "; Observable2: " + integer2;
                    }
                }
        )
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String result) {
                        System.out.println("Result: " + result);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("All Observable emitted!");
                    }
                });

        Thread.sleep(5000);
    }

    // Оператор Concat комбинирует два оператора, т.е. сначала выполняется первый затев второи и т.д. Он соблюдает очередность
    @Test
    public void testConcat() throws InterruptedException {
        Observable.concat(
                Observable.intervalRange(1,5 ,0, 500, TimeUnit.MILLISECONDS),
                Observable.intervalRange(6,5 ,0, 700, TimeUnit.MILLISECONDS)
        )
                .subscribe(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long result) {
                        System.out.println("Result: " + result);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("All long emitted!");
                    }
                });

        Thread.sleep(5000);
    }

    // Оператор Merge комбинирует два оператора, т.е. выполняет сразу все операторы. В отличии от Concat он не соблюдает очередность
    @Test
    public void testMerge() throws InterruptedException {
        Observable.merge(
                Observable.intervalRange(1,5 ,0, 500, TimeUnit.MILLISECONDS),
                Observable.intervalRange(6,5 ,0, 700, TimeUnit.MILLISECONDS)
        )
                .subscribe(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long result) {
                        System.out.println("Result: " + result);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("All long emitted!");
                    }
                });

        Thread.sleep(5000);
    }

    // Оператор Zip можно сказать противоположность CombineLatest
    // Он так же объединяет результат двух и более Observable но он ждет выполнение всех Observable и уже тогда
    // испускает результат функции. И завершиться тогда когда завершиться первый Observable.
    @Test
    public void testZip() throws InterruptedException {
        Observable.zip(
                Observable.intervalRange(1, 5, 0, 500, TimeUnit.MILLISECONDS),
                Observable.intervalRange(6, 7, 0, 1, TimeUnit.SECONDS),
                new BiFunction<Long, Long, String>() {
                    @Override
                    public String apply(Long aLong, Long aLong2) throws Exception {
                        return "Observable1: " + aLong + "; Observable2: " + aLong2;
                    }
                }
        )
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String result) {
                        System.out.println("Result: " + result);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("All long emitted!");
                    }
                });

        Thread.sleep(5000);
    }
}
