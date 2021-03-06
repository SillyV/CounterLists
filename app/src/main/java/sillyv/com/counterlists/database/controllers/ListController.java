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
 *
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

    public void addNewList(final ListModel model) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            Log.d(TAG, "execute:");
            final CounterList list = new CounterList(model, ListController.DEFAULT_LIST);
            model.setId(ListController.DEFAULT_LIST);
            realm1.copyToRealm(list);
        });
        realm.close();
    }

    //query a single item with the given id
    private CounterList getCounterList(Realm realm, long id) {
        return realm.where(CounterList.class).equalTo("id", id).findFirst();
    }

    @Override public Single<ListModel> getItem(long id) {
        return Single.fromCallable(() -> new ListModel(Realm.getDefaultInstance().where(CounterList.class).equalTo("id", id).findFirst()));
    }

    public Completable insert(final ListModel model) {
        return Completable.fromAction(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm1 -> {
                Log.d(TAG, "execute:");
                long nextID = (long) (realm1.where(CounterList.class).max("id")) + 1L;
                final CounterList list = new CounterList(model, nextID);
                model.setId(nextID);
                realm1.copyToRealm(list);
            });
            realm.close();
        });
    }

    public Completable updateItem(final ListModel model) {
        return Completable.fromAction(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm1 -> {
                Log.d(TAG, "execute:");
                final CounterList list = ListController.this.getCounterList(realm1,model.getId());
                list.update(model);
                realm1.copyToRealmOrUpdate(list);
            });
            realm.close();
        });
    }

    public Completable deleteItem(final Long aLong) {
        return Completable.fromAction(() -> Realm.getDefaultInstance().executeTransaction(realm1 -> {
            RealmResults<CounterList> result = realm1.where(CounterList.class).equalTo("id", aLong).findAll();
            result.deleteAllFromRealm();
            realm1.close();
        }));
    }

    @Override public Single<List<ListModel>> getItems() throws RuntimeException {
        return Single.fromCallable(() -> {
            realm = Realm.getDefaultInstance();
            List<ListModel> result = new ArrayList<>();
            RealmResults<CounterList> list = getCounterLists();
            for (CounterList counterList : list) {
                result.add(new ListModel(counterList));
            }
            realm.close();
            return result;
        });
    }

    @Override public Completable insertNewChildItem(Long parentId, CounterModel model) {
        return Completable.fromAction(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm1 -> {
                Log.d(TAG, "execute:");
                CounterList list = getCounterList(realm1,parentId);
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
        });
    }

    @Override public Completable updateItemValue(Long id, Integer value) {
        return Completable.fromAction(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm1 -> {
                Log.d(TAG, "execute:");
                final CounterList list = ListController.this.getCounterList(realm1, id);
                list.setValueChanged(new Date());
                realm1.copyToRealmOrUpdate(list);
            });
            realm.close();
        });
    }

    @Override public Completable deleteItems(List<Long> idList) {
        return Completable.fromAction(() -> Realm.getDefaultInstance().executeTransaction(realm1 -> {
            for (Long aLong : idList) {
                RealmResults<CounterList> result = realm1.where(CounterList.class).equalTo("id", aLong).findAll();
                result.deleteAllFromRealm();
            }
            realm1.close();
        }));
    }


    @Override public Completable resetItems(Long id) {
        return Completable.fromAction(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm1 -> {
                CounterList list = realm1.where(CounterList.class).equalTo("id", id).findFirst();
                for (Counter counter : list.getCounters()) {
                    counter.setValue(counter.getDefaultValue());
                }
                realm1.copyToRealmOrUpdate(list);
            });
            realm.close();
        });
    }

    @Override public Completable updateItemVibration() {
        return null;
    }

    @Override public Completable updateItemVolume(long id, boolean value) {
        return null;
    }

    //find all objects in the Book.class
    private RealmResults<CounterList> getCounterLists() {
        return realm.where(CounterList.class).greaterThan("id", 0).findAll();
    }


}
