package sillyv.com.counterlists.screens.counters.recycler;

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
 * Created by Vasili.Fedotov on 2/19/2017.
 */

public class CounterListPresenter
        extends BasePresenter {


    private RealmRepository<CounterModel, Object> childRepo;
    private RealmRepository<ListModel, CounterModel> parentRepo;
    private BaseListView<CounterListModel> view;


    public CounterListPresenter(BaseListView<CounterListModel> view,
                                RealmRepository<CounterModel, Object> childRepo,
                                RealmRepository<ListModel, CounterModel> parentRepo) {


        this.view = view;
        this.childRepo = childRepo;
        this.parentRepo = parentRepo;
    }

    public void getData(long id) {


        Single<ListModel> item = parentRepo.getItem(id);

        if (item == null) {
            view.onErrorResponse();
            return;
        }

        compositeDisposable.add(item.map(dbItemToViewItem()).
                subscribeWith(new DisposableSingleObserver<CounterListModel>() {
                    @Override public void onSuccess(CounterListModel listModels) {
                        view.onDataReceived(listModels);
                    }

                    @Override public void onError(Throwable e) {
                        view.onErrorResponse();
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


    public void deleteCounter(List<Long> idList) {


    }

    public void saveInteraction(Long id, Long parentId, int value) {
        childRepo.updateItemValue(id, value);
        parentRepo.updateItemValue(parentId, value);
    }


    public void saveCustomOrder(List<Long> idList) {


    }
}
