package com.geekbrains.lesson2;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;


// Операторы разбитые по категориям http://reactivex.io/documentation/operators.html#categorized
// Примеры http://androidahead.com/2018/04/06/rxjava-for-android-100-examples-pack/
// Здесь будут рассмотрены операторы создания
public class RxJavaTest1 {

    // Когда кто-нибудь подпишется на наш Observable, соответствующий экземпляр Subscriber будет передан в функцию create.
    // По мере выполнения кода, значения будут переданы подписчику.
    // Следует обратить внимание, что нужно самостоятельно вызывать метод onCompleted чтобы просигнализировать окончание последовательности.
    @Test
    public void testCreate() {
        final Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                System.out.println("Is subscribe");

                for (Integer i : numbers) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(i);
                        if (i.equals(numbers[numbers.length - 1])) emitter.onComplete();
                    }
                }
            }
        })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("Number: " + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("All numbers emitted!");
                    }
                });

    }

    //Обратите внимание как второй подписчик, подписавшись на секунду позже, получил такое же время.
    //Это происходит потому что значение времени было вычислено лишь единожды: когда выполнение доходит до метода just.
    @Test
    public void testJust() throws InterruptedException {
        Observable<Long> time = Observable.just(System.currentTimeMillis());
        time.subscribe(System.out::println);
        Thread.sleep(1000);
        time.subscribe(System.out::println);
    }

    @Test
    public void testUnsubscribe() throws InterruptedException {
        Disposable di = Observable.interval(500, TimeUnit.MILLISECONDS)
                .doOnDispose(() -> System.out.println("onDispose:"))
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("onNext: " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete:");
                    }
                });
        Thread.sleep(3000);
        di.dispose();
        Thread.sleep(3000);
    }

    //defer принимает функцию, которая возвращает Observable и будет выполнена для каждого нового подписчика.
    //решает проблему just
    @Test
    public void testDefer() {
        Observable<Long> time = Observable.defer(() -> Observable.just(System.currentTimeMillis()));
        time.subscribe(System.out::println);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        time.subscribe(System.out::println);
    }

    //Оператор который будет эмитить поочередно данные из массива
    @Test
    public void testFromArray() {
        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

        Observable.fromArray(numbers)
                .subscribe(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("Number: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("All numbers emitted!");
                    }
                });
    }

    //Оператор который будет эмитить поочередно данные из указанного диапазона
    @Test
    public void testRange() {
        Observable.range(1, 20)
                .subscribe(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("Number: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("All numbers emitted!");
                    }
                });
    }

    //по аналогии у нас есть операторы fromFuture(), fromIterable(), fromPublisher()
    @Test
    public void testFromCallable() {
        Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Create from Callable";
            }
        })
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String string) {
                        System.out.println(string);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    //Эта функция создаст бесконечную последовательность значений, отделенных заданным интервалом времени.
    //Последовательность не завершится до тех пор пока мы не отпишемся.
    @Test
    public void testInterval() throws InterruptedException {
        Observable.interval(500, TimeUnit.MILLISECONDS)
                .subscribe(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("Number: " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });

        Thread.sleep(4000);
    }

    //Эта функция создаст Observable который через заданный интервал времени выдасть 0L
    @Test
    public void testTimer() throws InterruptedException {
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribe(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("Number: " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });

        Thread.sleep(4000);
    }

    // Оператор generate использует функцию для генерации данных
    @Test
    public void testGenerate() {
        Observable.generate(new Consumer<Emitter<String>>() {
            private int count = 0;

            @Override
            public void accept(Emitter<String> emitter) {
                if (count > 5) {
                    emitter.onComplete();
                }
                count++;
                emitter.onNext("generate " + count);
            }
        })
                .subscribe(
                        System.out::println,
                        System.out::println,
                        () -> System.out.println("All emitted!"));
    }
}