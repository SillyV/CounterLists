package sillyv.com.counterlists.screens.lists.recycler;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import sillyv.com.counterlists.baseline.BaseListView;
import sillyv.com.counterlists.baseline.BasePresenter;
import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.ListModel;

/**
 * Created by Vasili.Fedotov on 2/18/2017.
 *
 */

class CounterListsPresenter extends BasePresenter
        implements CounterListsContract.CounterListsPresenter {


    private BaseListView<CounterListsModel> view;
    private RealmRepository<ListModel> repo;

    CounterListsPresenter(BaseListView<CounterListsModel> view, RealmRepository<ListModel> repo) {

        this.view = view;
        this.repo = repo;
    }

    public void getData() {
        compositeDisposable.add(repo.getItems().map(dbItemToViewItem()).
                subscribeWith(new DisposableSingleObserver<CounterListsModel>() {
                    @Override public void onSuccess(CounterListsModel listModels) {
                        view.onDataReceived(listModels);
                    }

                    @Override public void onError(Throwable e) {
                        view.onErrorResponse();
                    }
                }));
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

    @Override public void deleteItems(List<Long> idList) {
        for (Long aLong : idList) {
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

    private Function<List<ListModel>, CounterListsModel> dbItemToViewItem() {
        return listModels -> {

            List<CounterListsModel.ListItem> responseModel = new ArrayList<>();

            for (ListModel item : listModels) {
                String subTitle = getSubtitle(item);
                responseModel.add(new CounterListsModel.ListItem(item.getName(),
                        subTitle,
                        item.getBackground(),
                        item.getDefaultCardBackgroundColor(),
                        item.getDefaultCardForegroundColor(),
                        item.getId()));
            }
            return (new CounterListsModel(responseModel));
        };
    }

}
