package sillyv.com.counterlists.screens.lists.recycler;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import sillyv.com.counterlists.baseline.BaseListView;
import sillyv.com.counterlists.baseline.BasePresenter;
import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.CounterModel;
import sillyv.com.counterlists.database.models.ListModel;

/**
 * Created by Vasili.Fedotov on 2/18/2017.
 *
 */

class CounterListsPresenter
        extends BasePresenter
        implements CounterListsContract.CounterListsPresenter {


    private RealmRepository<ListModel, CounterModel> repo;
    private BaseListView<CounterListsModel> view;
    private Scheduler mainScheduler;

    CounterListsPresenter(BaseListView<CounterListsModel> view, RealmRepository<ListModel, CounterModel> repo, Scheduler mainScheduler) {
        this.mainScheduler = mainScheduler;
        this.view = view;
        this.repo = repo;
    }

    public void getData() {
        compositeDisposable.add(repo.getItems().subscribeOn(Schedulers.io()).map(dbItemToViewItem()).observeOn(mainScheduler).
                subscribeWith(new DisposableSingleObserver<CounterListsModel>() {

                    @Override public void onSuccess(CounterListsModel listModels) {
                        System.out.println("Thread Subscribe: " + Thread.currentThread().getId());
                        view.onDataReceived(listModels);
                    }

                    @Override public void onError(Throwable e) {
                        view.onGetDataErrorResponse();
                    }
                }));
    }


    @Override public void deleteItems(List<Long> idList) {
        compositeDisposable.add(repo.deleteItems(idList).subscribeOn(Schedulers.io()).subscribeWith(new DisposableCompletableObserver() {
            @Override public void onComplete() {
                getData();
            }

            @Override public void onError(Throwable e) {
                view.onDeleteBooksErrorResponse();
                getData();
            }
        }));
    }

    private Function<List<ListModel>, CounterListsModel> dbItemToViewItem() {
        return listModels -> {
            List<CounterListsModel.ListItem> responseModel = new ArrayList<>();
            System.out.println("Thread Map: " + Thread.currentThread().getId());
            for (ListModel item : listModels) {
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
