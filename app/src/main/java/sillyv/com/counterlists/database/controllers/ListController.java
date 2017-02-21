package sillyv.com.counterlists.database.controllers;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import sillyv.com.counterlists.database.dbitems.CounterList;
import sillyv.com.counterlists.database.models.ListModel;

/**
 * Created by Vasili.Fedotov on 1/28/2017.
 *
 */

public class ListController
        implements RealmRepository<ListModel> {
    private static final String TAG = ListController.class.getSimpleName();
    public static final long DEFAULT_LIST = 0L;
    private static ListController instance;
    private final Realm realm;

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


    //find all objects in the Book.class
    private RealmResults<CounterList> getCounterLists() {
        return realm.where(CounterList.class).greaterThan("id", 0).findAll();
    }

    //query a single item with the given id
    private CounterList getCounterList(long id) {
        return realm.where(CounterList.class).equalTo("id", id).findFirst();
    }


    public void addNewList(final ListModel model) {
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


    public void updateList(final ListModel model) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            Log.d(TAG, "execute:");
            final CounterList list = ListController.this.getCounterList(model.getId());
            list.update(model);
            realm1.copyToRealmOrUpdate(list);
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


    //    //query example
    //    public RealmResults<CounterList> queriedBooks() {
    //
    //        return realm.where(CounterList.class)
    //                .contains("author", "Author 0")
    //                .or()
    //                .contains("title", "Realm")
    //                .findAll();
    //
    //    }

    public void deleteItem(final Long aLong) {
        realm.executeTransaction(realm1 -> {
            RealmResults<CounterList> result = realm1.where(CounterList.class).equalTo("id", aLong).findAll();
            result.deleteAllFromRealm();
        });
    }

    @Override public Single<List<ListModel>> getItems() throws RuntimeException {
        return Single.fromCallable(() -> {
            List<ListModel> result = new ArrayList<>();
            RealmResults<CounterList> list = getCounterLists();
            for (CounterList counterList : list) {
                result.add(new ListModel(counterList));
            }
            return result;
        });
    }

    @Override public ListModel getItem(long id) {
        CounterList list = realm.where(CounterList.class).equalTo("id", id).findFirst();
        if (list == null) { return null; }
        return new ListModel(list);
    }


}
