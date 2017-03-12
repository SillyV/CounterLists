package sillyv.com.counterlists.screens.fullscreen;

import android.util.Log;

import java.util.Collections;

import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import sillyv.com.counterlists.baseline.BasePresenter;
import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.CounterModel;
import sillyv.com.counterlists.database.models.ListModel;
import sillyv.com.counterlists.tools.DefaultSwitchConfiguration;

import static android.content.ContentValues.TAG;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 *
 */

public class FullScreenCounterPresenter
        extends BasePresenter {
    private final FullScreenCounterContract.fullScreenView view;
    private final RealmRepository<CounterModel, Object> repo;
    private final RealmRepository<ListModel, CounterModel> parentRepo;
    private final Scheduler mainScheduler;

    public FullScreenCounterPresenter(FullScreenCounterContract.fullScreenView view,
                                      RealmRepository<CounterModel, Object> repo,
                                      RealmRepository<ListModel, CounterModel> parentRepo,
                                      Scheduler mainScheduler) {

        this.view = view;
        this.repo = repo;
        this.parentRepo = parentRepo;
        this.mainScheduler = mainScheduler;
    }

    public void getData(Long counterId, Long parentID) {
        compositeDisposable.add(parentRepo.getItem(parentID)
                .subscribeOn(Schedulers.io())
                .map(mapDBCounterToCounter(counterId))
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

    private Function<ListModel, FullScreenCounterModel.CounterModel> mapDBCounterToCounter(Long counterId) {
        return listModel -> {
            CounterModel counter = getCounterModel(counterId, listModel);
            if (counter == null) {
                return null;
            }
            return new FullScreenCounterModel.CounterModel.Builder().withVibrate(getBooleanFromParentAndChildSwitch(counter.getVibrate(),
                    listModel.getVibrate(),
                    DefaultSwitchConfiguration.isVibrate())).withSpeakName(getBooleanFromParentAndChildSwitch(counter.getSpeechOutputName(),
                    listModel.getSpeechOutputName(),
                    DefaultSwitchConfiguration.isSpeakName())).withSpeakValue(getBooleanFromParentAndChildSwitch(counter.getSpeechOutputValue(),
                    listModel.getSpeechOutputValue(),
                    DefaultSwitchConfiguration.isSpeakValue())).withUseVolume(getBooleanFromParentAndChildSwitch(counter.getVolumeKey(),
                    listModel.getVolumeKey(),
                    DefaultSwitchConfiguration.isUseVolume())).withClickSound(getBooleanFromParentAndChildSwitch(counter.getClickSound(),
                    listModel.getClickSound(),
                    DefaultSwitchConfiguration.isClickSound())).withKeepAwake(getBooleanFromParentAndChildSwitch(counter.getKeepAwake(),
                    listModel.getKeepAwake(),
                    DefaultSwitchConfiguration.isKeepAwake()))
                    .withName(counter.getName()).withNote(counter.getNote())
                    .withDateCreated(counter.getCreated())
                    .withDateModified(counter.getEdited())
                    .withLastUsed(counter.getValueChanged())
                    .withValue(counter.getValue())
                    .withDefaultValue(counter.getDefaultValue())
                    .withDecrement(counter.getDecrement())
                    .withIncrement(counter.getIncrement())
                    .withForeground(counter.getForeground())
                    .withBackground(counter.getBackground())
                    .build();
        };
    }

    private CounterModel getCounterModel(Long counterId, ListModel listModel) {
        for (CounterModel counterModel : listModel.getCounters()) {
            if (counterModel.getId() == counterId) {
                return counterModel;
            }
        }
        return null;
    }

    private boolean getBooleanFromParentAndChildSwitch(int clickSound, int clickSound1, boolean defaultValue) {
        return clickSound == 1 || clickSound != 2 && (clickSound1 == 1 || clickSound1 != 2 && defaultValue);
    }

    public void deleteCounter(Long id) {
        compositeDisposable.add(repo.deleteItems(Collections.singletonList(id))
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override public void onComplete() {
                        view.onDeleteItemSuccess();
                    }

                    @Override public void onError(Throwable e) {
                        view.onDeleteItemError();
                    }
                }));
    }

    public void saveInteraction(Long counterId, Long parentId, int newValue) {
        compositeDisposable.add(repo.updateItemValue(counterId, newValue).concatWith(parentRepo.updateItemValue(parentId, newValue)).subscribeOn(
                Schedulers.io()).observeOn(mainScheduler).subscribeWith(new DisposableCompletableObserver() {
            @Override public void onComplete() {
                view.onSaveInteractionSuccess();
            }

            @Override public void onError(Throwable e) {
                view.onSaveInteractionError();
                Log.d(TAG, "onError: ");
            }
        }));
    }

    public void resetItem(long counterId) {

        compositeDisposable.add(repo.resetItems(counterId)
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override public void onComplete() {
                        view.onResetSuccess();
                    }

                    @Override public void onError(Throwable e) {
                        view.onResetError();
                    }
                }));
    }


    //getData
    //saveInteraction
    //reset
    //deleteCounter
}
