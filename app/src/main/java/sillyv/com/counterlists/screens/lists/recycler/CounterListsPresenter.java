package sillyv.com.counterlists.screens.lists.recycler;


import java.util.ArrayList;
import java.util.List;
import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.ListModel;

/**
 * Created by Vasili.Fedotov on 2/18/2017.
 *
 */

class CounterListsPresenter implements CounterListsContract.CounterListsPresenter {

    private final CounterListsContract.CounterListsView view;
    private final RealmRepository<ListModel> repo;

    CounterListsPresenter(CounterListsContract.CounterListsView view, RealmRepository<ListModel> repo) {
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
            String subTitle = getSubtitle(item);
            responseModel.add(new CounterListsModel.ListItem(item.getName(), subTitle, item.getBackground(), item.getDefaultCardBackgroundColor(), item.getDefaultCardForegroundColor(), item.getId()));
        }
        view.onDataReceived(new CounterListsModel(responseModel));
    }

    private String getSubtitle(ListModel item) {
        List<String> counterNames = item.getCounterNames();
        String subTitle = "";
        for (String str : counterNames) {
            subTitle += str + ", ";
        }
        subTitle = replaceLastComma(subTitle);
        return subTitle;
    }

    @Override
    public void deleteItems(CounterListsModel.IDList idList) {
        for (Long aLong : idList.getItems()) {
            repo.deleteItem(aLong);
        }
        getData();
    }

    private String replaceLastComma(String str) {
        if (str != null && str.length() > 1) {
            str = str.substring(0, str.length() - 2) + ".";
        }
        return str;

    }



}
