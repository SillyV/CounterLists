package sillyv.com.counterlists.database.controllers;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmResults;
import sillyv.com.counterlists.database.dbitems.Counter;
import sillyv.com.counterlists.database.models.CounterModel;

/**
 * Created by Vasili on 1/28/2017.
 */

public class CounterController
        implements RealmRepository<CounterModel, Object> {
    public static final long DEFAULT_COUNTER = 0L;
    private static final String TAG = CounterController.class.getSimpleName();
    private static CounterController instance;
    private final Realm realm;

    private CounterController() {
        realm = Realm.getDefaultInstance();
    }

    public static CounterController getInstance() {
        if (instance == null) {
            instance = new CounterController();
        }
        return instance;
    }

    public Realm getRealm() {

        return realm;
    }


    public List<CounterModel> getAllCounters() {
        List<CounterModel> result = new ArrayList<>();
        RealmResults<Counter> list = getCounters();
        for (Counter counter : list) {
            result.add(new CounterModel(counter));
        }
        return result;

    }


    //find all objects in the Book.class
    private RealmResults<Counter> getCounters() {
        return realm.where(Counter.class).greaterThan("id", 0).findAll();
    }

    @Override public Single<CounterModel> getItem(long id) throws RuntimeException {
        return Single.fromCallable(() -> new CounterModel(Realm.getDefaultInstance().where(Counter.class).equalTo("id", id).findFirst()));
    }

    public Completable insert(final CounterModel model) {
        return Completable.fromAction(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm1 -> {
                Log.d(TAG, "execute:");
                long nextID = (long) (realm1.where(Counter.class).max("id")) + 1L;
                final Counter list = new Counter(model, nextID);
                model.setId(nextID);
                realm1.copyToRealm(list);
            });
            realm.close();
        });
    }

    public Completable updateItem(final CounterModel model) {
        return Completable.fromAction(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm1 -> {
                Log.d(TAG, "execute:");
                final Counter counter = getCounter(model.getId());
                counter.update(model);
                realm1.copyToRealmOrUpdate(counter);
            });
            realm.close();
        });
    }

    //query a single item with the given id
    private Counter getCounter(long id) {
        return Realm.getDefaultInstance().where(Counter.class).equalTo("id", id).findFirst();
    }

    @Override public Completable deleteItem(Long aLong) {
        return null;
    }

    @Override public Single<List<CounterModel>> getItems() throws RuntimeException {
        return null;
    }

    @Override public Single<List<CounterModel>> getItems(long id) throws RuntimeException {
        return null;
    }

    @Override public Completable insertNewChildItem(Long parentId, Object model) {
        return Completable.fromAction(() -> {});
    }

    @Override public Completable updateItemValue(Long id, Integer value) {
        return Completable.fromAction(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm1 -> {
                Log.d(TAG, "execute:");
                Counter list = getCounter(id);
                list.setValue(value);
                list.setValueChanged(new Date());
                realm1.copyToRealmOrUpdate(list);
            });
            realm.close();
        });
    }

    @Override public Completable deleteItems(List<Long> idList) {
        return Completable.fromAction(() -> Realm.getDefaultInstance().executeTransaction(realm1 -> {
            for (Long aLong : idList) {
                RealmResults<Counter> result = realm1.where(Counter.class).equalTo("id", aLong).findAll();
                result.deleteAllFromRealm(); //// TODO: 3/3/2017 Test deletion and list behaviors
            }
        }));

    }

    public void addNewList(final CounterModel model, final long nextID) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            Log.d(TAG, "execute:");
            final Counter list = new Counter(model, nextID);
            model.setId(nextID);
            realm1.copyToRealm(list);
        });
        realm.close();
    }

    public void childIncremented(final long id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            Log.d(TAG, "execute:");
            final Counter list = getCounter(id);
            list.incremented();
            realm1.copyToRealmOrUpdate(list);
        });
        realm.close();
    }
}
