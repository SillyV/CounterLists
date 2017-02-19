package sillyv.com.counterlists.database.controllers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import sillyv.com.counterlists.database.dbitems.Counter;
import sillyv.com.counterlists.database.dbitems.CounterList;
import sillyv.com.counterlists.database.models.CounterModel;
import sillyv.com.counterlists.database.models.ListModel;

/**
 * Created by Vasili on 1/28/2017.
 *
 */

public class CounterController {
    private static final String TAG = CounterController.class.getSimpleName();
    public static final long DEFAULT_LIST = 0L;
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
        return realm.where(Counter.class).greaterThan("id",0). findAll();
    }

    //query a single item with the given id
    public Counter getCounter(long id) {
        return realm.where(Counter.class).equalTo("id", id).findFirst();
    }


    public void addNewList(final CounterModel model) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.d(TAG, "execute:");
                long nextID = (long) (realm.where(Counter.class).max("id")) + 1L;
                final Counter list = new Counter(model, nextID);
                model.setId(nextID);
                realm.copyToRealm(list);
            }
        });
        realm.close();
    }

    public void addNewList(final CounterModel model, final long nextID) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.d(TAG, "execute:");
                final Counter list = new Counter(model, nextID);
                model.setId(nextID);
                realm.copyToRealm(list);
            }
        });
        realm.close();
    }


    public void updateList(final CounterModel model) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.d(TAG, "execute:");
                final Counter list = getCounter(model.getId());
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
                final Counter list = getCounter(id);
                list.incremented();
                realm.copyToRealmOrUpdate(list);
            }
        });
        realm.close();
    }


    //query example
    public RealmResults<Counter> queriedBooks() {

        return realm.where(Counter.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }
}
