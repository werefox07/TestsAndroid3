package com.geekbrains.lesson2;

import com.geekbrains.lesson2.models.Address;
import com.geekbrains.lesson2.models.User;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

//Здесь будут рассмотрены операторы трансформации
public class RxJavaTest2 {

    // метод эмулирующий запрос в сеть на получение данных о пользователе
    private Observable<User> getUsersObservable() {
        String[] names = new String[]{"mark", "john", "trump", "obama"};

        final List<User> users = new ArrayList<>();
        for (String name : names) {
            User user = new User();
            user.setName(name);
            user.setGender("male");

            users.add(user);
        }
        return Observable
                .create(new ObservableOnSubscribe<User>() {
                    @Override
                    public void subscribe(ObservableEmitter<User> emitter) throws Exception {
                        for (User user : users) {
                            if (!emitter.isDisposed()) {
                                emitter.onNext(user);
                            }
                        }

                        if (!emitter.isDisposed()) {
                            emitter.onComplete();
                        }
                    }
                }).subscribeOn(Schedulers.io());
    }

    private Observable<User> getAddressObservable(final User user) {

        final String[] addresses = new String[]{
                "1600 Amphitheatre Parkway, Mountain View, CA 94043",
                "2300 Traverwood Dr. Ann Arbor, MI 48105",
                "500 W 2nd St Suite 2900 Austin, TX 78701",
                "355 Main Street Cambridge, MA 02142"
        };

        return Observable
                .create(new ObservableOnSubscribe<User>() {
                    @Override
                    public void subscribe(ObservableEmitter<User> emitter) throws Exception {
                        Address address = new Address();
                        address.setAddress(addresses[new Random().nextInt(3)]);
                        if (!emitter.isDisposed()) {
                            user.setAddress(address);

                            // Generate network latency of random duration
                            int sleepTime = new Random().nextInt(1000) + 500;
                            Thread.sleep(sleepTime);

                            emitter.onNext(user);
                            emitter.onComplete();
                        }
                    }
                }).subscribeOn(Schedulers.io());
    }

    // Оператор Map модифицирует каждый полученный объект и передает его дальше
    @Test
    public void testMap() {
        getUsersObservable()
                .map(new Function<User, User>() {
                    @Override
                    public User apply(User user) throws Exception {
                        user.setEmail(String.format("%s@rxjava.wtf", user.getName()));
                        user.setName(user.getName().toUpperCase());
                        return user;
                    }
                })
                .subscribe(new DisposableObserver<User>() {
                    @Override
                    public void onNext(User user) {
                        System.out.println("User: " + user);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("All users emitted!");
                    }
                });
    }


    // Оператор FlatMap применяем функцию к каждому item но вместо возвращения модифицированного item
    // возвращается сам Observable который сам эмитить данные снова.
    @Test
    public void testFlatMap() throws InterruptedException {
        getUsersObservable()
                .map(new Function<User, User>() {
                    @Override
                    public User apply(User user) throws Exception {
                        user.setEmail(String.format("%s@rxjava.wtf", user.getName()));
                        user.setName(user.getName().toUpperCase());
                        return user;
                    }
                })
                .flatMap(new Function<User, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(User user) throws Exception {
                        return getAddressObservable(user);
                    }
                })
                .subscribe(new DisposableObserver<User>() {
                    @Override
                    public void onNext(User user) {
                        System.out.println("User: " + user);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("All users emitted!");
                    }
                });

        Thread.sleep(5000);
    }

    // Оператор ConcatMap аналогичен FlatMap за исключеним того что он выполняет последовательной действий
    // в том же порядке в котором она испускается источником. И этот оператор синхронный.
    @Test
    public void testConcatMap() throws InterruptedException {
        getUsersObservable()
                .map(new Function<User, User>() {
                    @Override
                    public User apply(User user) throws Exception {
                        user.setEmail(String.format("%s@rxjava.wtf", user.getName()));
                        user.setName(user.getName().toUpperCase());
                        return user;
                    }
                })
                .concatMap(new Function<User, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(User user) throws Exception {
                        return getAddressObservable(user);
                    }
                })
                .subscribe(new DisposableObserver<User>() {
                    @Override
                    public void onNext(User user) {
                        System.out.println("User: " + user);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("All users emitted!");
                    }
                });

        Thread.sleep(5000);
    }

    // Оператор SwitchMap всегда возвращает последний Observable
    @Test
    public void testSwitchMap() throws InterruptedException {
        Observable.fromArray(new Integer[]{1, 2, 3, 4, 5, 6})
                .subscribeOn(Schedulers.io())
                .switchMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        return Observable.just(integer).delay(1, TimeUnit.SECONDS);
                    }
                })
                .subscribe(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("Integer: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("All Integer emitted!");
                    }
                });

        Thread.sleep(5000);
    }

    //Что выбрать

    // Подумайте об использовании оператора Map, когда необходимо выполнить автономные операции с передаваемыми данными.
    // Например, мы получили что-то от сервера, но это не соответствует нашему требованию.
    // В этом случае Map может использоваться для изменения отправленных данных.

    // Выберите FlatMap, когда порядок не важен.
    // Допустим, вы создаете авиа приложение, которое выбирает цены каждой авиакомпании отдельно
    // и отображает на экране. Для этого могут использоваться как FlatMap, так и ConcatMap.
    // Но если порядок не важен и вы хотите отправлять все сетевые вызовы одновременно,
    // я бы рассмотрел FlatMap поверх ConcatMap. Если вы рассматриваете ConcatMap в этом сценарии,
    // то время, необходимое для получения цен, занимает очень много времени,
    // поскольку ConcatMap не будет совершать одновременные вызовы для поддержания порядка.

    // SwitchMap лучше всего подходит, когда вы хотите отказаться от промежуточных ответов и получить последний.
    // Допустим, вы пишете приложение для поиска чего либо, которое отправляет поисковый запрос на сервер каждый раз,
    // когда пользователь что-то вводит. В этом случае несколько запросов будут отправлены на сервер,
    // но мы хотим показать результат только последнего запроса.
    // В этом случае лучше всего использовать SwitchMap.
    // Еще один вариант использования SwitchMap: у вас есть экран ленты, в котором лента обновляется каждый раз,
    // когда пользователь выполняет swipe для обновления. В этом сценарии лучше всего подходит SwitchMap,
    // поскольку он может игнорировать более старый ответ и учитывать только последний запрос.


    // Оператор Scan превращает каждый элемент в другой элемент, так же как и Map.
    // Но также передает «предыдущий» элемент в функцию
    @Test
    public void testScan() {
        Observable.fromArray(new Integer[]{1, 2, 3, 4, 5, 6})
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                })
                .subscribe(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("Integer: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("All Integer emitted!");
                    }
                });
    }

    @Test
    public void testGroupBy() {
        final String EVEN_NUMBER_KEY = "even number";
        final String ODD_NUMBER_KEY = "odd number";

        Observable.fromArray(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9})
                .groupBy(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        if (integer % 2 == 0) {
                            return EVEN_NUMBER_KEY;
                        } else {
                            return ODD_NUMBER_KEY;
                        }
                    }
                })
                .subscribe(new DisposableObserver<GroupedObservable<String, Integer>>() {
                    @Override
                    public void onNext(GroupedObservable<String, Integer> groupedObservable) {
                        if (groupedObservable.getKey().equals(EVEN_NUMBER_KEY)) {
                            groupedObservable.subscribe(new DisposableObserver<Integer>() {
                                @Override
                                public void onNext(Integer integer) {
                                    System.out.println("Integer: " + integer);
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {
                                    System.out.println("All Integer emitted!");
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("All emitted!");
                    }
                });
    }

    // Оператор compose принимает реализацию ObservableTransformer для преобразования источника в observable
    @Test
    public void testCompose() {
                Observable.just("language", "framework", "os", "library", "os")
                        .compose(new ObservableTransformer<String, String>(){
                            //возвращает новый observable который будет эмитить уникальные значения от источника
                            @Override
                            public ObservableSource<String> apply(Observable<String> upstream) {
                                return upstream.distinct();
                            }})
                        .subscribe(s -> System.out.println("values after compose operator "+s));
    }
}