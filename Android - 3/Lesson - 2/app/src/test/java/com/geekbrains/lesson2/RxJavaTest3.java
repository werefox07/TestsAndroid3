package com.geekbrains.lesson2;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableObserver;

// Здесь будут рассмотрены операторы фильтрации
public class RxJavaTest3 {

    // Оператор debounce при получении нового текста будет делать паузу в одну секунду.
    // Если в течение этой секунды больше не будет новых данных, то последнее полученное значение пойдет дальше.
    // Если же в течение этой секунды пришел новый текст, то предыдущий текст никуда не пойдет,
    // а debounce снова сделает паузу в одну секунду. И т.д.
    //
    // В переводе на нормальный язык - debounce будет отсеивать передаваемые ему данные,
    // если между ними пауза меньше, чем указанное время. Это как раз то, что нам нужно, чтобы отсеять излишние срабатывания поиска.
    @Test
    public void testDebounce() {

       /* Observable.defer(() ->
                Observable.interval(500, TimeUnit.MILLISECONDS)
                .take(5)
        )
                .debounce(600, TimeUnit.MILLISECONDS)
                .subscribe(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long integer) {
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

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    // Оператор который убирает дубликаты. Для кастомных данных необходимо переопределять equals() и hashCode()
    // Есть похожий оператор DistinctUntilChanged. Данный оператор используется для того, чтобы избежать дублирования сетевых вызовов.
    // Например, последний поисковой запрос был «abc», затем пользователь удалил «c» и заново ввел «c». Результат снова «abc».
    // Если сетевой вызов уже в процессе с тем же параметром, то оператор distinctUntilChanged не даст осуществить аналогичный
    // вызов повторно. Таким образом, оператор distinctUntilChanged отсеивает повторяющиеся последовательно переданные ему элементы.
    @Test
    public void testDistinct() {
        Observable.fromArray(new Integer[]{1, 2, 3, 4, 5, 5, 5, 6, 6, 7})
                .distinct()
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

    // Оператор который возьмет только указанный элемент, в нашем случае 4 элемент в массиве.
    @Test
    public void testElementAt() {
        Observable.fromArray(new Integer[]{1, 2, 3, 4, 5, 6, 7})
                .elementAt(4)
                .subscribe(new DisposableMaybeObserver<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
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

    @Test
    public void testFilter() {
        Observable.fromArray(new Integer[]{1, 2, 3, 4, 5, 6, 7})
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer % 2 == 0;
                    }
                })
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

    // Этот оператор испускает самый последний элемент, испускаемый Observable в течение периодических интервалов времени.
    // (позволяет получать только самый последний элемент из всех элементов во временном периоде.)
    // Оператор Sample периодически просматривает Observable и испускает тот элемент,
    // который был выпущен последним со времени предыдущей выборки.
    // В коде ниже у нас испускается массив от 1 до 10 с интервалом в 1 секунду. А оператору sample сказали
    // проверять каждые 2 секунды у нашего Observable последние данные и их выдавать.
    @Test
    public void testSample() throws InterruptedException {
        Observable.intervalRange(1, 10, 0, 1, TimeUnit.SECONDS)
                .sample(2, TimeUnit.SECONDS)
                .subscribe(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long integer) {
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

        Thread.sleep(5000);
    }

    // Оператор Skip пропускает первые n элементов
    // Есть аналогичный оператор SkipLast который пропускает последние n элементов
    @Test
    public void testSkip() {
        Observable.fromArray(new Integer[]{1, 2, 3, 4, 5, 6, 7})
                .skip(2)
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

    // Оператор Take наоборт берет первые n элементов
    // А оператор TakeLast берет последние n элементов
    @Test
    public void testTake() {
        Observable.fromArray(new Integer[]{1, 2, 3, 4, 5, 6, 7})
                .take(2)
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
}
