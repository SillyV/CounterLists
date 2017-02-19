package sillyv.com.counterlists.screens.lists.recycler;

import android.content.Context;
import android.util.LongSparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sillyv.com.counterlists.database.controllers.CounterController;
import sillyv.com.counterlists.database.controllers.ListController;
import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.CounterModel;
import sillyv.com.counterlists.database.models.ListModel;

/**
 * Created by Vasili on 2/18/2017.
 */

public class CounterListsPresenter implements CounterListsContract.CounterListsPresenter {

    private CounterListsContract.CounterListsView view;
    private RealmRepository<ListModel> repo;

    public CounterListsPresenter(CounterListsContract.CounterListsView view, RealmRepository<ListModel> repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void getData() {
        List<ListModel> items = repo.getItems();
        if (items == null) {
            view.onErrorResponse();
            return;
        }
        List<CounterListsModel.ListItem> responseModel = new ArrayList<>();
        for (ListModel item : items) {
            List<String> counterNames = item.getCounterNames();
            String subTitle = "";
            for (String str : counterNames) {
                subTitle += str + ", ";
            }
            subTitle = replaceLastComma(subTitle);
            responseModel.add(new CounterListsModel.ListItem(item.getName(), subTitle, item.getBackground(), item.getDefaultCardBackgroundColor(), item.getDefaultCardForegroundColor(), item.getId()));
        }
        view.onDataReceived(new CounterListsModel(responseModel));
    }

    @Override
    public void deleteItems(CounterListsModel.IDList idList) {
        for (Long aLong : idList.getItems()) {
            repo.deleteItem(aLong);
        }
        getData();
    }

    private String replaceLastComma(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == 'x') {
            str = str.substring(0, str.length() - 2);
        }
        return str + ".";
    }

}
