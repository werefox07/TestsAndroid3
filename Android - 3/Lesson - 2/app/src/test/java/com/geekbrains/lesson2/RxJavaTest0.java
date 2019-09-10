package com.geekbrains.lesson2;

import io.reactivex.annotations.NonNull;

public class RxJavaTest0 {

    public interface Observer<T> {
        void onSubscribe(@NonNull Disposable d);
        void onNext(@NonNull T t);
        void onError(@NonNull Throwable e);
        void onComplete();
    }

    public interface Disposable {
        void dispose();
        boolean isDisposed();
    }

    public interface Subscriber<T> {
        void onSubscribe(@NonNull Disposable d);
        void onNext(@NonNull T t);
        void onError(@NonNull Throwable e);
        void onComplete();
    }

    public interface Subscription {
        public void request(long n);
        public void cancel();
    }


    // Observable vs Flowable
    //
    // Используйте Observable, если
    // 1. У вас не очень много данных и, накапливаясь, они не смогут привести к OutOfMemoryError.
    // 2. Вы работаете с данными, которые не поддерживают backpressure, например: touch-события.
    // Вы не можете попросить пользователя не нажимать на экран.
    //
    // Используйте Flowable, если
    // Данные поддерживают backpressure, например: чтение с диска. Вы всегда можете приостановить его,
    // чтобы обработать то, что уже пришло, а затем возобновить чтение.


    // Single
    // Этот источник предоставит вам либо один элемент в onNext, либо ошибку в onError,
    // после чего передача данных будет считаться завершенной. onComplete вы от него не дождетесь.

    // Completable
    // Этот источник предоставит вам либо onComplete, либо ошибку в onError. Никаких данных в onNext он не передаст.
    // Этот тип удобно использовать, когда вам нужно выполнить какую-то операцию,
    // которая не имеет результата, и получить уведомление, что она завершена.

    // Maybe
    // Его можно описать как Single OR Completable. Т.е. вам придет либо одно значение в onNext, либо onCompleted, но не оба.
    // Также может прийти onError.
}
