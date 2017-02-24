package sillyv.com.counterlists.database.controllers;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 */

public interface RealmRepository<P, C> {


    Single<P> getItem(long id) throws RuntimeException;

    void insert(P dbModel);

    void updateItem(P dbModel);

    void deleteItem(Long aLong);

    Single<List<P>> getItems() throws RuntimeException;

    Single<List<P>> getItems(long id) throws RuntimeException;


    void insertNewChildItem(Long parentId, C model);

    void updateItemValue(Long id, Integer value);
}
