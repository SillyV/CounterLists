package sillyv.com.counterlists.database.controllers;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import sillyv.com.counterlists.database.dbitems.Counter;
import sillyv.com.counterlists.database.dbitems.CounterList;
import sillyv.com.counterlists.database.models.CounterModel;
import sillyv.com.counterlists.database.models.ListModel;

/**
 * Created by Vasili.Fedotov on 1/28/2017.
 */

public class ListController
        implements RealmRepository<ListModel, CounterModel> {
    public static final long DEFAULT_LIST = 0L;
    private static final String TAG = ListController.class.getSimpleName();
    private static ListController instance;
    private Realm realm;

    private ListController() {
        realm = Realm.getDefaultInstance();
    }

    public static ListController getInstance() {
        if (instance == null) {
            instance = new ListController();
        }
        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    public void addNewList(final ListModel model, final long nextID) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            Log.d(TAG, "execute:");
            final CounterList list = new CounterList(model, nextID);
            model.setId(nextID);
            realm1.copyToRealm(list);
        });
        realm.close();
    }

    public void childIncremented(final long id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            Log.d(TAG, "execute:");
            final CounterList list = ListController.this.getCounterList(id);
            list.incremented();
            realm1.copyToRealmOrUpdate(list);
        });
        realm.close();
    }

    //query a single item with the given id
    private CounterList getCounterList(long id) {
        return realm.where(CounterList.class).equalTo("id", id).findFirst();
    }

    @Override public Single<ListModel> getItem(long id) {
        return Single.fromCallable(() -> {
            ListModel id1 = new ListModel(realm.where(CounterList.class).equalTo("id", id).findFirst());
            return id1;
        });
    }

    public void insert(final ListModel model) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            Log.d(TAG, "execute:");
            long nextID = (long) (realm1.where(CounterList.class).max("id")) + 1L;
            final CounterList list = new CounterList(model, nextID);
            model.setId(nextID);
            realm1.copyToRealm(list);
        });
        realm.close();
    }

    public void updateItem(final ListModel model) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            Log.d(TAG, "execute:");
            final CounterList list = ListController.this.getCounterList(model.getId());
            list.update(model);
            realm1.copyToRealmOrUpdate(list);
        });
        realm.close();
    }

    public Completable deleteItem(final Long aLong) {
        return Completable.fromAction(() -> Realm.getDefaultInstance().executeTransaction(realm1 -> {
            RealmResults<CounterList> result = realm1.where(CounterList.class).equalTo("id", aLong).findAll();
            result.deleteAllFromRealm();
        }));
    }

    @Override public Single<List<ListModel>> getItems() throws RuntimeException {
        return Single.fromCallable(() -> {
            System.out.println("Thread DB: " + Thread.currentThread().getId());
            realm = Realm.getDefaultInstance();
            List<ListModel> result = new ArrayList<>();
            RealmResults<CounterList> list = getCounterLists();
            for (CounterList counterList : list) {
                result.add(new ListModel(counterList));
            }
            return result;
        });
    }

    @Override public Single<List<ListModel>> getItems(long id) throws RuntimeException {
        return null;
    }

    @Override public void insertNewChildItem(Long parentId, CounterModel model) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            Log.d(TAG, "execute:");
            CounterList list = ListController.this.getCounterList(parentId);
            RealmList<Counter> counters = list.getCounters();
            long nextID;
            try {
                nextID = ((Long) realm1.where(Counter.class).max("id")) + 1L;
            } catch (Exception ex) {
                nextID = 1;
            }
            Counter counter = realm.createObject(Counter.class, nextID);
            counter.setData(model);
            realm1.copyToRealmOrUpdate(counter);
            counters.add(counter);
            //            list.setCounters(counters);
            realm1.copyToRealmOrUpdate(list);
        });
        realm.close();
    }

    @Override public void updateItemValue(Long id, Integer value) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            Log.d(TAG, "execute:");
            final CounterList list = ListController.this.getCounterList(id);
            list.setValueChanged(new Date());
            realm1.copyToRealmOrUpdate(list);
        });
        realm.close();
    }

    @Override public Completable deleteItems(List<Long> idList) {
        return Completable.fromAction(() -> Realm.getDefaultInstance().executeTransaction(realm1 -> {
            for (Long aLong : idList) {
                RealmResults<CounterList> result = realm1.where(CounterList.class).equalTo("id", aLong).findAll();
                result.deleteAllFromRealm();
            }
        }));

    }


    //find all objects in the Book.class
    private RealmResults<CounterList> getCounterLists() {
        return realm.where(CounterList.class).greaterThan("id", 0).findAll();
    }


}
