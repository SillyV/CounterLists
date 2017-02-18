package sillyv.com.counterlists.database.controllers;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import sillyv.com.counterlists.database.dbitems.CounterList;
import sillyv.com.counterlists.database.models.ListModel;

/**
 * Created by Vasili on 1/28/2017.
 *
 */

public class ListController {
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


    public List<ListModel> getAllCounters() {
        List<ListModel> result = new ArrayList<>();
        RealmResults<CounterList> list = getCounterLists();
        for (CounterList counterList : list) {
            result.add(new ListModel(counterList));
        }
        return result;

    }


    //find all objects in the Book.class
    private RealmResults<CounterList> getCounterLists() {
        return realm.where(CounterList.class).greaterThan("id",0). findAll();
    }

    //query a single item with the given id
    public CounterList getCounterList(long id) {

        return realm.where(CounterList.class).equalTo("id", id).findFirst();
    }


    public void addNewList(final ListModel model) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.d(TAG, "execute:");
                long nextID = (long) (realm.where(CounterList.class).max("id")) + 1L;
                final CounterList list = new CounterList(model, nextID);
                model.setId(nextID);
                realm.copyToRealm(list);
            }
        });
        realm.close();
    }

    public void addNewList(final ListModel model, final long nextID) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.d(TAG, "execute:");
                final CounterList list = new CounterList(model, nextID);
                model.setId(nextID);
                realm.copyToRealm(list);
            }
        });
        realm.close();
    }


    public void updateList(final ListModel model) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.d(TAG, "execute:");
                final CounterList list = getCounterList(model.getId());
                list.update(model);
                realm.copyToRealmOrUpdate(list);
            }
        });
        realm.close();
    }

    public void childIncremented(final long id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.d(TAG, "execute:");
                final CounterList list = getCounterList(id);
                list.incremented();
                realm.copyToRealmOrUpdate(list);
            }
        });
        realm.close();
    }


    //query example
    public RealmResults<CounterList> queriedBooks() {

        return realm.where(CounterList.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }
}
