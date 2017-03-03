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
        return null;
    }

    public void insert(final CounterModel model) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                Log.d(TAG, "execute:");
                long nextID = (long) (realm.where(Counter.class).max("id")) + 1L;
                final Counter list = new Counter(model, nextID);
                model.setId(nextID);
                realm.copyToRealm(list);
            }
        });
        realm.close();
    }

    public void updateItem(final CounterModel model) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                Log.d(TAG, "execute:");
                final Counter list = getCounter(model.getId());
                list.update(model);
                realm.copyToRealmOrUpdate(list);
            }
        });
        realm.close();
    }

    //query a single item with the given id
    public Counter getCounter(long id) {
        return realm.where(Counter.class).equalTo("id", id).findFirst();
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

    @Override public void insertNewChildItem(Long parentId, Object model) {

    }

    @Override public void updateItemValue(Long id, Integer value) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            Log.d(TAG, "execute:");
            Counter list = getCounter(id);
            list.setValue(value);
            list.setValueChanged(new Date());
            realm1.copyToRealmOrUpdate(list);
        });
        realm.close();
    }

    @Override public Completable deleteItems(List<Long> idList) {
        return null;
    }

    public void addNewList(final CounterModel model, final long nextID) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                Log.d(TAG, "execute:");
                final Counter list = new Counter(model, nextID);
                model.setId(nextID);
                realm.copyToRealm(list);
            }
        });
        realm.close();
    }

    public void childIncremented(final long id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                Log.d(TAG, "execute:");
                final Counter list = getCounter(id);
                list.incremented();
                realm.copyToRealmOrUpdate(list);
            }
        });
        realm.close();
    }


    //query example
    public RealmResults<Counter> queriedBooks() {

        return realm.where(Counter.class).contains("author", "Author 0").or().contains("title", "Realm").findAll();

    }
}
