package sillyv.com.counterlists.screens.counters.recycler;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
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
    private CounterListContract.CounterListView<CounterListModel> view;
    private Scheduler mainScheduler;


    public CounterListPresenter(CounterListContract.CounterListView<CounterListModel> view,
                                RealmRepository<CounterModel, Object> childRepo,
                                RealmRepository<ListModel, CounterModel> parentRepo,
                                Scheduler mainScheduler) {

        this.mainScheduler = mainScheduler;
        this.parentRepo = parentRepo;
        this.childRepo = childRepo;
        this.view = view;
    }

    public void deleteCounters(List<Long> idList, long parentID) {
        compositeDisposable.add(childRepo.deleteItems(idList)
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override public void onComplete() {
                        view.onDeleteItemsSuccess();
                        getData(parentID);
                    }

                    @Override public void onError(Throwable e) {
                        view.onDeleteItemsErrorResponse();
                        getData(parentID);
                    }
                }));

    }

    public void getData(long id) {
        compositeDisposable.add(parentRepo.getItem(id).subscribeOn(Schedulers.io()).observeOn(mainScheduler).map(dbItemToViewItem()).
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
        return listModel -> { return new CounterListModel(listModel.getName(),
                listModel.getItemStrings(),
                listModel.getBackground(),
                listModel.getDefaultCardBackgroundColor(),
                listModel.getDefaultCardForegroundColor(),
                listModel.getId(),
                listModel);};
    }

    public void resetItems(List<Long> id_list, Long parentID) {
        List<Completable> items = new ArrayList<>();
        for (Long aLong : id_list) {
            items.add(childRepo.resetItems(aLong));
        }
        compositeDisposable.add(Completable.merge(items)
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override public void onComplete() {
                        getData(parentID);
                        view.onResetItemsSuccess();
                    }

                    @Override public void onError(Throwable e) {
                        view.onResetItemError();
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
                        view.onSaveInteractionSuccess();
                    }

                    @Override public void onError(Throwable e) {
                        view.onSaveInteractionFailure();
                        Log.d(TAG, "onError: ");
                    }
                }));
    }


    public void saveCustomOrder(List<Long> idList) {


    }
}
