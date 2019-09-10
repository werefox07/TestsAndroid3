package com.geekbrains.lesson2;


import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;

// горячие и холодные
public class RxJavaTest7 {

    private DisposableObserver<Long> getObserver1(){
        return new DisposableObserver<Long>() {
            @Override
            public void onNext(Long aLong) {
                System.out.println("observer1 onNext value = " + aLong);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("observer1 onCompleted");
            }
        };
    }

    private DisposableObserver<Long> getObserver2(){
        return new DisposableObserver<Long>() {
            @Override
            public void onNext(Long aLong) {
                System.out.println("observer2 onNext value = " + aLong);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("observer2 onCompleted");
            }
        };
    }

    @Test
    public void testColdObservable() throws InterruptedException {
        Observable<Long> observable = Observable
                .interval(1, TimeUnit.SECONDS)
                .take(6);

        Thread.sleep(2000);
        System.out.println("observer1 subscribe");
        observable.subscribe(getObserver1());

        Thread.sleep(4500);
        System.out.println("observer2 subscribe");
        observable.subscribe(getObserver2());

        Thread.sleep(8000);
    }

    @Test
    public void testHotObservable() throws InterruptedException {
        ConnectableObservable<Long> observable = Observable
                .interval(1, TimeUnit.SECONDS)
                .take(10)
                .publish();

        System.out.println("observable connect");
        observable.connect();

        Thread.sleep(2500);
        System.out.println("observer1 subscribe");
        observable.subscribe(getObserver1());

        Thread.sleep(4500);
        System.out.println("observer2 subscribe");
        observable.subscribe(getObserver2());

        Thread.sleep(8000);
    }

    // Оператор replay аналогично оператору publish создает ConnectableObservable.
    // Но вновь прибывшие подписчики будут получать элементы, которые они пропустили.
    @Test
    public void testReplay() throws InterruptedException {
        ConnectableObservable<Long> observable = Observable
                .interval(1, TimeUnit.SECONDS)
                .take(10)
                .replay();

        System.out.println("observable connect");
        observable.connect();

        Thread.sleep(2500);
        System.out.println("observer1 subscribe");
        observable.subscribe(getObserver1());

        Thread.sleep(4500);
        System.out.println("observer2 subscribe");
        observable.subscribe(getObserver2());

        Thread.sleep(8000);
    }

    // Оператор RefCount позволяет стартовать и останавливать подписку автоматически в зависимости от наличия
    // подписчиков
    @Test
    public void testRefCount() throws InterruptedException {
        Observable<Long> observable = Observable
                .interval(1, TimeUnit.SECONDS)
                .take(10)
                .publish()
                .refCount();

        Thread.sleep(1500);
        System.out.println("observer1 subscribe");
        Disposable di = observable.subscribeWith(getObserver1());

        Thread.sleep(2500);
        System.out.println("observer2 subscribe");
        Disposable di2 = observable.subscribeWith(getObserver2());

        Thread.sleep(4000);
        System.out.println("observer1 Disposable");
        di.dispose();

        Thread.sleep(2000);
        System.out.println("observer2 Disposable");
        di2.dispose();

        Thread.sleep(3000);

        System.out.println("observer1 subscribe");
        observable.subscribeWith(getObserver1());
        Thread.sleep(12000);
    }
}
