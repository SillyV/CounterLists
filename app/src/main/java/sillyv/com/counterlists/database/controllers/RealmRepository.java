package sillyv.com.counterlists.database.controllers;

import java.util.List;

import sillyv.com.counterlists.database.models.ListModel;

/**
 * Created by Vasili on 2/19/2017.
 */

public interface RealmRepository<T> {

    List<T> getItems();

    T getItem(long id);

    void addNewList(T dbModel);

    void updateList(T dbModel);

    void deleteItem(Long aLong);
}
