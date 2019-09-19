package com.geekbrains.lesson2;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;
import io.reactivex.subjects.Subject;

// Здесь будут рассмотрены вспомогательные операторы
public class RxJavaTest5 {

    // Делает задержку перед выполнением Observable
    @Test
    public void testDelay() throws InterruptedException {
        Observable.just("A", "B", "C", "D", "E", "F")
                .delay(2, TimeUnit.SECONDS)
                .subscribe(
                        System.out::println,
                        System.out::println,
                        () -> System.out.println("All emitted!"));

        Thread.sleep(7000);
    }

    // Операторы Do регистрируют действия для выполнения различных событий жизненного цикла Observable.
    // Оператор doOnNext() изменяет источник Observable, чтобы он вызывал действие при вызове onNext().
    // Оператор doOnCompleted() регистрирует действие, чтобы оно вызывало действие при вызове onComplete().
    // Оператор doOnEach() изменяет источник Observable таким образом, чтобы он уведомлял Observer для каждого элемента
    // и устанавливал обратный вызов, который будет вызываться каждый раз при отправке элемента.
    // Оператор doOnSubscribe() регистрирует действие, которое вызывается всякий раз,
    // когда Observer подписывается на результирующую Observable.
    @Test
    public void testDo() {
        Observable.just("A", "B", "C", "D", "E", "F")
                .doOnNext((s) -> System.out.println("onNext: " + s))
                .doOnComplete(() -> System.out.println("onComplete"))
                .doOnSubscribe((d) -> System.out.println("onSubscribe"))
                .doOnEach(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("doOnEach onNext: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("doOnEach All emitted!");
                    }
                })
                .subscribe(
                        System.out::println,
                        System.out::println,
                        () -> System.out.println("All emitted!"));
    }

    // Используется для переключения потока
    @Test
    public void testObserveOn() {
        Observable.just("A", "B", "C", "D", "E", "F")
                .doOnNext(i -> System.out.println("Emitting " + i + " on thread " + Thread.currentThread().getName()))
                .observeOn(Schedulers.computation())
                .doOnNext(i -> System.out.println("Emitting " + i + " on thread " + Thread.currentThread().getName()))
                .observeOn(Schedulers.io())
                .doOnNext(i -> System.out.println("Emitting " + i + " on thread " + Thread.currentThread().getName()))
                .subscribe();
    }

    // Нужно помнить что subscribeOn при меняется только один раз. Т.е. если вы его вызовите хоть 10 раз
    // поток у вас уже не измениться. Им указывают поток источника данных.
    @Test
    public void testSubscribeOn() throws InterruptedException {
        Observable.just("A", "B", "C", "D", "E", "F")
                .subscribeOn(Schedulers.computation())
                .doOnNext(i -> System.out.println("Emitting " + i + " on thread " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.io())
                .doOnNext(i -> System.out.println("Emitting " + i + " on thread " + Thread.currentThread().getName()))
                .subscribe(System.out::println);

        Thread.sleep(3000);
    }

    // Какие у нас есть Schedulers из коробки
    // Schedulers.computation()
    // Содержит несколько потоков для вычислений. Количество потоков зависит от количества процессов,
    // доступных для Java на этом устройстве. Размер этого пула эквивалентен количеству ядер процессора.

    // Schedulers.io()
    // Идеально подходит для операций ввода-вывода, таких как чтение или запись в базу данных, запросы к серверу,
    // чтение или запись на диск. Другими словами, операции, которые требуют сложных вычислений
    // и много раз ожидают отправки или получения данных. Размер пула потоков не ограничен.

    // Schedulers.newThread()
    // Этот поток будет создавать новый поток всякий раз, когда кто-то подписывается на Observable,
    // и после того, как работа будет завершена, поток будет завершен.

    // Schedulers.immediate()
    // Позволяет сразу начать работу в текущем потоке (откуда вызывается Observable).
    // Этот планировщик используется по умолчанию для timeout(), timeInterval() и timestamp().
    // Чаще всего его используют для тестирования.

    // Schedulers.trampoline()
    // Позволяет отложить задачу в текущем потоке и поставить ее в очередь.
    // Этот планировщик будет обрабатывать свою очередь и запускать задачи одну за другой.
    // Планировщик по умолчанию для repeat() и retry()

    // Schedulers.from(executorService)
    // С помощью этого вы можете создать планировщик из ExecutorService.

    // Этот оператор преобразует Observable, который испускает элементы, в тот, который испускает указание количества времени,
    // прошедшего между этими выбросами. то есть, если мы больше заинтересованы в том, сколько времени прошло
    // с момента последнего элемента, а не в абсолютный момент времени, когда элементы были выпущены,
    // мы можем использовать метод timeInterval().
    @Test
    public void testTimeInterval() throws InterruptedException {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(3)
                .timeInterval()
                .subscribe(new Subject<Timed<Long>>() {
                    @Override
                    public boolean hasObservers() {
                        return false;
                    }

                    @Override
                    public boolean hasThrowable() {
                        return false;
                    }

                    @Override
                    public boolean hasComplete() {
                        return false;
                    }

                    @Override
                    public Throwable getThrowable() {
                        return null;
                    }

                    @Override
                    protected void subscribeActual(Observer<? super Timed<Long>> observer) {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Timed<Long> longTimed) {
                        System.out.println("onNext: " + longTimed);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Thread.sleep(3000);
    }

    // Этот оператор зеркалирует источник Observable, но выдает уведомление об ошибке,
    // если определенный период времени истекает без каких-либо излучаемых элементов.
    @Test
    public void testTimeout() throws InterruptedException {
        Observable.just(1, 2, 3, 4, 5, 6)
                .delay(1, TimeUnit.SECONDS)
                .timeout(500, TimeUnit.MILLISECONDS)
                .subscribe(
                        i -> System.out.println("onNext: " + i),
                        t -> {
                            t.printStackTrace();
                            System.out.println("onError: ");
                        });

        Thread.sleep(3000);
    }

    // Этот оператор прикрепляет временную метку к каждому элементу, излучаемым Observable.
    // Он преобразует элементы в тип метки времени, который содержит исходные элементы,
    // а также метку времени, когда событие было отправлено.
    @Test
    public void testTimestamp() throws InterruptedException {
        Observable.interval(300, TimeUnit.MILLISECONDS)
                .take(3)
                .timestamp()
                .subscribe(new DisposableObserver<Timed<Long>>() {
                    @Override
                    public void onNext(Timed<Long> longTimed) {
                        System.out.println(longTimed);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Thread.sleep(3000);
    }


    @Test
    public void testBuffer() throws InterruptedException {
        Observable.interval(300, TimeUnit.MILLISECONDS)
                .buffer(3)
                .subscribe(new DisposableObserver<List<Long>>() {
                    @Override
                    public void onNext(List<Long> longs) {
                        System.out.println(longs);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Thread.sleep(3000);
    }
}
