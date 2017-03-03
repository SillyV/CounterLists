package sillyv.com.counterlists.database.controllers;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 *
 */

public interface RealmRepository<P, C> {


    Single<P> getItem(long id) throws RuntimeException;

    Completable insert(P dbModel);

    Completable updateItem(P dbModel);

    Completable deleteItem(Long aLong);

    Single<List<P>> getItems() throws RuntimeException;

    Single<List<P>> getItems(long id) throws RuntimeException;


    Completable insertNewChildItem(Long parentId, C model);

    Completable updateItemValue(Long id, Integer value);

    Completable deleteItems(List<Long> idList);
}
