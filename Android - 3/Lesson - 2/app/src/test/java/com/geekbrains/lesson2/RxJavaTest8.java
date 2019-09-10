package com.geekbrains.lesson2;

import org.junit.Test;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

// обработка ошибок
public class RxJavaTest8 {

    // Получаем ошибку в Runtime т.к. определили только один Action
    @Test
    public void testError() {
        Observable.just("1", "2", "3", "a", "5")
                .map((s) -> Long.parseLong(s))
                .subscribe(System.out::println);
    }

    // Здесь мы уже не получаем ошибки в Runtime
    @Test
    public void testOnError() {
        Observable.just("1", "2", "3", "a", "5")
                .map((s) -> Long.parseLong(s))
                .subscribe(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("onNext: " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete:");
                    }
                });
    }

    @Test
    public void testDoOnError() {
        Observable.just("1", "2", "3", "a", "5")
                .map((s) -> Long.parseLong(s))
                .doOnError((t) -> System.out.println("Выполняем какое то действие при ошибке"))
                .subscribe(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("onNext: " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete:");
                    }
                });
    }

    @Test
    public void testOnErrorReturn() {
        Observable.just("1", "2", "3", "a", "5")
                .map((s) -> Long.parseLong(s))
                .onErrorReturn((t) -> 0L)
                .subscribe(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("onNext: " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete:");
                    }
                });
    }

    // Этот оператор аналогичен оператору onErrorReturn, но позволяет вместо ошибки отправить в Observer не одно значение,
    // а несколько - в виде Observable.
    @Test
    public void testOnErrorResumeNext() {
        Observable.just("1", "2", "3", "a", "5")
                .map((s) -> Long.parseLong(s))
                .onErrorResumeNext(Observable.just(8L, 9L, 10L))
                .subscribe(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("onNext: " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete:");
                    }
                });
    }

    // Этот оператор аналогичен оператору onErrorResumeNext, но не поймает ошибки Throwable и Error, только Exception.
    @Test
    public void testOnExceptionResumeNext() {
        Observable.just("1", "2", "3", "a", "5")
                .map((s) -> Long.parseLong(s))
                .onExceptionResumeNext(Observable.just(8L, 9L, 10L))
                .subscribe(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("onNext: " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete:");
                    }
                });
    }

    // Оператор Retry позволяет переподписываться на ваш Observable в случае ошибки и соответственно повторно
    // запрашивать данные. Это может быть удобно, например, при нестабильном коннекте.
    // Но стоит понимать что при не правильном использовании ваш метод может уйти в бесконечность.
    // В нашем примере ошибка будет возникать всегда поэтому retry будет бесконечно пытаться заново получить данные.
    // Так же в retry можно передать функцию которая будет определять стоит ли еще раз пытаться
    @Test
    public void testRetry() throws InterruptedException {
        Observable.just("1", "2", "3", "a", "5")
                .zipWith(Observable.interval(500, TimeUnit.MILLISECONDS),
                        new BiFunction<String, Long, Long>() {
                            @Override
                            public Long apply(String s, Long aLong) throws Exception {
                                return Long.parseLong(s) + aLong;
                            }
                        })
                .retry() // чтобы не уйти в бесконечность можем явно указать кол-во попыток
                .subscribe(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("onNext: " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete:");
                    }
                });

        Thread.sleep(7000);
    }


    // Итак, у нас есть Observable.just (далее observableMain), в котором может возникнуть ошибка.
    // Мы добавляем к нему оператор retryWhen. Для retryWhen нам необходимо использовать функцию:
    // Function<Observable<Throwable>, ObservableSource<?>>
    //
    // Т.е. на вход функции мы получаем Observable<? extends Throwable> (далее observableErrors),
    // а вернуть нам надо ObservableSource<?> (далее observableRetry).
    //
    // Давайте разбираться, что из себя представляет observableErrors, и как нам создать observableRetry.
    //
    // observableErrors - это Observable, который будет постить все возникающие при работе ошибки.
    // Т.е. как только в observableMain возникнет ошибка, observableErrors перехватит ее и отправит своим подписчикам.
    // Наша задача - на основе observableErrors создать свой observableRetry.
    // Этот observableRetry будет использован оператором retryWhen для принятия решения, что делать с ошибкой.
    // Т.е. retryWhen будет реагировать на события observableRetry:
    // onNext - сигнал о том, что надо запускать retry для observableMain
    // onError - observableMain завершится с onError
    // onCompleted - observableMain завершится с onCompleted
    //
    // При этом тип данных observableRetry абсолютно не важен. Главное - какое событие пришло.
    // Хорошая статья на эту тему
    // https://blog.danlew.net/2016/01/25/rxjavas-repeatwhen-and-retrywhen-explained/
    @Test
    public void testRetryWhen() throws InterruptedException {
        Observable.just("1", "2", "3", "a", "5")
                .zipWith(Observable.interval(500, TimeUnit.MILLISECONDS),
                        new BiFunction<String, Long, Long>() {
                            @Override
                            public Long apply(String s, Long aLong) throws Exception {
                                return Long.parseLong(s) + aLong;
                            }
                        })
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        System.out.println("call retryWhen");
                        return throwableObservable
                                //.delay(3, TimeUnit.SECONDS);
                                .zipWith(Observable.range(1, 3), new BiFunction<Throwable, Integer, Observable>() {
                                    @Override
                                    public Observable apply(Throwable throwable, Integer integer) throws Exception {
                                        if (integer < 3) {
                                            return Observable.timer(2, TimeUnit.SECONDS);
                                        } else {
                                            return Observable.error(throwable);
                                        }
                                    }
                                })
                                .flatMap(new Function<Observable, Observable<?>>() {
                                    @Override
                                    public Observable<?> apply(Observable observable) {
                                        return observable;
                                    }
                                });
                    }
                })
                .subscribe(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("onNext: " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete:");
                    }
                });

        Thread.sleep(15000);
    }
}
