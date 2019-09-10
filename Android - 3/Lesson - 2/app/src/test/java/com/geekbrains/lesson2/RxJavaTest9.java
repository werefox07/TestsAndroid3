package com.geekbrains.lesson2;

import org.junit.Test;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;
import io.reactivex.subjects.UnicastSubject;

// Subject
public class RxJavaTest9 {

    final DisposableObserver<Long> observer1 = new DisposableObserver<Long>() {

        @Override
        public void onComplete() {
            System.out.println("observer1 onCompleted");
        }

        @Override
        public void onError(Throwable e) {}

        @Override
        public void onNext(Long aLong) {
            System.out.println("observer1 onNext value = " + aLong);
        }
    };

    final DisposableObserver<Long> observer2 = new DisposableObserver<Long>() {

        @Override
        public void onComplete() {
            System.out.println("observer2 onCompleted");
        }

        @Override
        public void onError(Throwable e) {}

        @Override
        public void onNext(Long aLong) {
            System.out.println("observer2 onNext value = " + aLong);
        }
    };

    // Самый обычный Subject, без каких-либо опций. Принимает данные и отдает их всем текущим подписчикам.
    @Test
    public void testPublishSubject() throws InterruptedException {
        final Observable<Long> observable = Observable
                .interval(1, TimeUnit.SECONDS)
                .take(20);

        final PublishSubject<Long> subject = PublishSubject.create();

        System.out.println("subject subscribe");
        observable.subscribe(subject);

        Thread.sleep(2500);

        System.out.println("observer1 subscribe");
        subject.subscribe(observer1);

        Thread.sleep(4500);
        System.out.println("observer2 subscribe");
        subject.subscribe(observer2);

        Thread.sleep(6500);
        subject.onNext(100L);

        Thread.sleep(4000);
    }

    // Похож на PublishSubject, но имеет буфер, который хранит данные и передает их подписчикам в момент подписки.
    @Test
    public void testReplaySubject() throws InterruptedException {
        final Observable<Long> observable = Observable
                .interval(1, TimeUnit.SECONDS)
                .take(20);

        final ReplaySubject<Long> subject = ReplaySubject.create();

        System.out.println("subject subscribe");
        observable.subscribe(subject);

        Thread.sleep(2500);

        System.out.println("observer1 subscribe");
        subject.subscribe(observer1);

        Thread.sleep(4500);
        System.out.println("observer2 subscribe");
        subject.subscribe(observer2);

        Thread.sleep(6500);
        subject.onNext(100L);

        Thread.sleep(4000);
    }

    // Это ReplySubject с размером буфера = 1 и возможностью указать начальный элемент.
    // Соответственно, в момент подписки Observer сразу получит от Subject его последний элемент.
    // А если Subject еще сам ничего не получал, то он передаст в Observer дефолтное значение.
    @Test
    public void testBehaviorSubject() throws InterruptedException {
        final Observable<Long> observable = Observable
                .interval(1, TimeUnit.SECONDS)
                .take(20);

        final BehaviorSubject<Long> subject = BehaviorSubject.createDefault(-1L);

        System.out.println("subject subscribe");
        observable.subscribe(subject);

        Thread.sleep(2500);

        System.out.println("observer1 subscribe");
        subject.subscribe(observer1);

        Thread.sleep(4500);
        System.out.println("observer2 subscribe");
        subject.subscribe(observer2);

        Thread.sleep(6500);
        subject.onNext(100L);

        Thread.sleep(4000);
    }

    // Выдает только последнее значение и только в момент, когда последовательность завершена.
    @Test
    public void testAsyncSubject() throws InterruptedException {
        final Observable<Long> observable = Observable
                .interval(1, TimeUnit.SECONDS)
                .take(5);

        final AsyncSubject<Long> subject = AsyncSubject.create();

        System.out.println("subject subscribe");
        observable.subscribe(subject);

        Thread.sleep(2500);

        System.out.println("observer1 subscribe");
        subject.subscribe(observer1);

        Thread.sleep(4500);
        System.out.println("observer2 subscribe");
        subject.subscribe(observer2);

        Thread.sleep(3000);
    }

    // Subject, на который можно подписать лишь одного получателя.
    // И даже после того как этот один получатель отписался, никто больше не сможет подписаться.
    @Test
    public void testUnicastSubject() throws InterruptedException {
        final Observable<Long> observable = Observable
                .interval(1, TimeUnit.SECONDS)
                .take(5);

        final UnicastSubject<Long> subject = UnicastSubject.create();

        System.out.println("subject subscribe");
        observable.subscribe(subject);

        Thread.sleep(2500);

        System.out.println("observer1 subscribe");
        Disposable d = subject.subscribeWith(observer1);

        System.out.println("observer1 unsubscribe");
        Thread.sleep(3500);
        d.dispose();

        System.out.println("observer2 subscribe");
        subject.subscribe(observer2);

        Thread.sleep(5000);
        System.out.println("finish");
    }


    @Test
    public void testNotSerializedSubject() throws InterruptedException {
        final PublishSubject subject = PublishSubject.create();

        final Consumer<Long> consumer = new Consumer<Long>() {
            private long sum = 0;

            @Override
            public void accept(Long aLong) throws Exception {
                sum += aLong;
            }

            @NonNull
            @Override
            public String toString() {
                return "sum = " + sum;
            }
        };
        System.out.println("subject subscribed");
        subject.subscribe(consumer);

        Thread.sleep(2000);

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    subject.onNext(1L);
                }
                System.out.println("first thread done");
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    subject.onNext(1L);
                }
                System.out.println("second thread done");
            }
        }.start();

        Thread.sleep(2000);
        System.out.println("finish consumer: " + consumer.toString());
    }

    // SerializedSubject, сам со своей стороны сделает вызов метода onNext потокобезопасным.
    @Test
    public void testSerializedSubject() throws InterruptedException {
        final Subject subject = PublishSubject.create().toSerialized();

        final Consumer<Long> consumer = new Consumer<Long>() {
            private long sum = 0;

            @Override
            public void accept(Long aLong) throws Exception {
                sum += aLong;
            }

            @NonNull
            @Override
            public String toString() {
                return "sum = " + sum;
            }
        };
        System.out.println("subject subscribed");
        subject.subscribe(consumer);

        Thread.sleep(2000);

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    subject.onNext(1L);
                }
                System.out.println("first thread done");
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    subject.onNext(1L);
                }
                System.out.println("second thread done");
            }
        }.start();

        Thread.sleep(2000);
        System.out.println("finish consumer: " + consumer.toString());
    }
}
