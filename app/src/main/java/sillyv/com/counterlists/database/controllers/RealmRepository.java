package sillyv.com.counterlists.database.controllers;

import java.util.List;

import io.reactivex.Single;
import sillyv.com.counterlists.database.models.ListModel;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 *
 */

public interface RealmRepository<T> {


    T getItem(long id);

    void addNewList(T dbModel);

    void updateList(T dbModel);

    void deleteItem(Long aLong);

    Single<List<T>> getItems() throws RuntimeException;
}
