package sillyv.com.counterlists.screens.lists.recycler;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import sillyv.com.counterlists.baseline.BaseListView;
import sillyv.com.counterlists.baseline.BasePresenter;
import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.CounterModel;
import sillyv.com.counterlists.database.models.ListModel;

/**
 * Created by Vasili.Fedotov on 2/18/2017.
 */

class CounterListsPresenter
        extends BasePresenter
        implements CounterListsContract.CounterListsPresenter {


    private BaseListView<CounterListsModel> view;
    private RealmRepository<ListModel,CounterModel> repo;

    CounterListsPresenter(BaseListView<CounterListsModel> view, RealmRepository<ListModel,CounterModel> repo) {

        this.view = view;
        this.repo = repo;
    }

    public void getData() {
        Single<List<ListModel>> items = repo.getItems();

        if (items == null) {
            view.onErrorResponse();
            return;
        }

        compositeDisposable.add(items.map(dbItemToViewItem()).
                subscribeWith(new DisposableSingleObserver<CounterListsModel>() {
                    @Override public void onSuccess(CounterListsModel listModels) {
                        view.onDataReceived(listModels);
                    }

                    @Override public void onError(Throwable e) {
                        view.onErrorResponse();
                    }
                }));
    }


    @Override public void deleteItems(List<Long> idList) {
        for (Long aLong : idList) {
            repo.deleteItem(aLong);
        }
        getData();
    }



    private Function<List<ListModel>, CounterListsModel> dbItemToViewItem() {
        return listModels -> {

            List<CounterListsModel.ListItem> responseModel = new ArrayList<>();

            for (ListModel item : listModels) {
                String subTitle = item.getCounterItemsString();
                responseModel.add(new CounterListsModel.ListItem(item.getName(),
                        item.getItemStrings(),
                        item.getBackground(),
                        item.getDefaultCardBackgroundColor(),
                        item.getDefaultCardForegroundColor(),
                        item.getId()));
            }
            return (new CounterListsModel(responseModel));
        };
    }

}
