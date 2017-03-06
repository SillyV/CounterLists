package sillyv.com.counterlists.screens.counters.recycler;

import android.util.Log;

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
 * Created by Vasili.Fedotov on 2/19/2017.
 */

public class CounterListPresenter
        extends BasePresenter {
    private static final String TAG = "CounterListPresenter";

    private RealmRepository<ListModel, CounterModel> parentRepo;
    private RealmRepository<CounterModel, Object> childRepo;
    private BaseListView<CounterListModel> view;
    private Scheduler mainScheduler;


    public CounterListPresenter(BaseListView<CounterListModel> view,
                                RealmRepository<CounterModel, Object> childRepo,
                                RealmRepository<ListModel, CounterModel> parentRepo,
                                Scheduler mainScheduler) {

        this.mainScheduler = mainScheduler;
        this.parentRepo = parentRepo;
        this.childRepo = childRepo;
        this.view = view;
    }

    public void getData(long id) {
        compositeDisposable.add(parentRepo.getItem(id).map(dbItemToViewItem()).
                subscribeWith(new DisposableSingleObserver<CounterListModel>() {
                    @Override public void onSuccess(CounterListModel listModels) {
                        view.onDataReceived(listModels);
                    }

                    @Override public void onError(Throwable e) {
                        view.onGetDataErrorResponse();
                    }
                }));
    }

    private Function<ListModel, CounterListModel> dbItemToViewItem() {
        return listModel -> new CounterListModel(listModel.getName(),
                listModel.getItemStrings(),
                listModel.getBackground(),
                listModel.getDefaultCardBackgroundColor(),
                listModel.getDefaultCardForegroundColor(),
                listModel.getId(),
                listModel);
    }


    public void deleteCounter(List<Long> idList, long parentID) {
        compositeDisposable.add(childRepo.deleteItems(idList)
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override public void onComplete() {
                        getData(parentID);
                    }

                    @Override public void onError(Throwable e) {
                        view.onDeleteItemsErrorResponse();
                        getData(parentID);
                    }
                }));

    }

    public void saveInteraction(Long id, Long parentId, int value) {
        compositeDisposable.add(childRepo.updateItemValue(id, value)
                .concatWith(parentRepo.updateItemValue(parentId, value))
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }

                    @Override public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }
                }));
    }


    public void saveCustomOrder(List<Long> idList) {


    }
}
