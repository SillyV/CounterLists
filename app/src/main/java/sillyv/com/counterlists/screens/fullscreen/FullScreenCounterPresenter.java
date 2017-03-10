package sillyv.com.counterlists.screens.fullscreen;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import sillyv.com.counterlists.baseline.BasePresenter;
import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.CounterModel;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 */

public class FullScreenCounterPresenter
        extends BasePresenter {
    private final FullScreenCounterContract.fullScreenView view;
    private final RealmRepository<CounterModel, Object> repo;
    private final Scheduler mainScheduler;

    public FullScreenCounterPresenter(FullScreenCounterContract.fullScreenView view,
                                      RealmRepository<CounterModel, Object> repo,
                                      Scheduler mainScheduler) {

        this.view = view;
        this.repo = repo;
        this.mainScheduler = mainScheduler;
    }

    public void getData(Long counterId) {
        compositeDisposable.add(repo.getItem(counterId)
                .subscribeOn(Schedulers.io())
                .map(mapDBCounterToCounter())
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableSingleObserver<FullScreenCounterModel.CounterModel>() {
                    @Override public void onSuccess(FullScreenCounterModel.CounterModel counterModel) {
                        view.onDataReceived(counterModel);
                    }

                    @Override public void onError(Throwable e) {
                        view.onGetDataErrorResponse();
                    }
                }));
    }

    @NonNull private Function<CounterModel, FullScreenCounterModel.CounterModel> mapDBCounterToCounter() {
        return new Function<CounterModel, FullScreenCounterModel.CounterModel>() {
            @Override public FullScreenCounterModel.CounterModel apply(CounterModel counterModel) throws Exception {
                return null;
            }
        };
    }

    public void saveInteraction(Long counterId, int newValue) {

    }

    public void setVibration(long counterId, boolean value) {

    }

    public void setVolume(long counterId, int value) {

    }

    public void resetItem(long counterId) {

    }

    public void deleteItem(long counterId) {

    }

    //saveInteraction
    //toggleVibration
    //toggleSound
    //reset
    //deleteCounter
}
