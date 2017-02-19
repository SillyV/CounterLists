package sillyv.com.counterlists.screens.lists.recycler;

import android.content.Context;
import android.util.LongSparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sillyv.com.counterlists.database.controllers.CounterController;
import sillyv.com.counterlists.database.controllers.ListController;
import sillyv.com.counterlists.database.models.CounterModel;
import sillyv.com.counterlists.database.models.ListModel;

/**
 * Created by Vasili on 2/18/2017.
 */

public class CounterListsPresenter implements CounterListsContract.CounterListsPresenter {
    @Override
    public void getData(Context context, CounterListsContract.CounterListsView view) {
//        List<ListModel> items = ListController.getInstance().getItems();
//        List<CounterListsModel.ListItem> responseModel = new ArrayList<>();
//        for (ListModel item : items) {
//            List<String> counterNames = item.getCounterNames();
//            String subTitle = "";
//            for (String str : counterNames) {
//                subTitle += str + ", ";
//            }
//            responseModel.add(new CounterListsModel.ListItem(item.getName(), subTitle, item.getBackground(), item.getDefaultCardBackgroundColor(), item.getDefaultCardForegroundColor(), item.getId()));
//        }
//        view.onDataReceived(new CounterListsModel(responseModel));
    }

    @Override
    public void deleteItems(Context context, CounterListsContract.CounterListsView view, CounterListsModel.IDList idList) {
        for (Long aLong : idList.getItems()) {
            ListController.getInstance().deleteItem(aLong);
        }
        getData(context, view);
    }

    public String replaceLastComma(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == 'x') {
            str = str.substring(0, str.length() - 2);
        }
        return str + ".";
    }

}
