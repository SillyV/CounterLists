package sillyv.com.counterlists.screens.counters.upsert;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.baseline.BasePresenter;
import sillyv.com.counterlists.database.controllers.CounterController;
import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.CounterModel;
import sillyv.com.counterlists.database.models.ListModel;

import static android.content.ContentValues.TAG;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 */

public class UpsertCounterPresenter
        extends BasePresenter
        implements UpsertCounterContract.UpsertCounterPresenter {


    private final UpsertCounterContract.UpsertCounterView<UpsertCounterModel.CounterModel> view;
    private final RealmRepository<CounterModel, Object> repo;
    private final RealmRepository<ListModel, CounterModel> parentRepo;
    private Scheduler mainScheduler;

    public UpsertCounterPresenter(UpsertCounterContract.UpsertCounterView<UpsertCounterModel.CounterModel> view,
                                  RealmRepository<CounterModel, Object> repo,
                                  RealmRepository<ListModel, CounterModel> parentRepo,
                                  Scheduler mainScheduler) {
        this.view = view;
        this.repo = repo;
        this.parentRepo = parentRepo;
        this.mainScheduler = mainScheduler;
    }

    @Override public void loadData(Context context, DateFormat dateFormat, Long id, long parentId) {
        if (id == CounterController.DEFAULT_COUNTER) {
            loadNewList(context, dateFormat, parentId);
            return;
        }
        loadExistingList(context, dateFormat, id, parentId);


    }

    private void loadExistingList(Context context, DateFormat dateFormat, Long id, long parentId) {
        compositeDisposable.add(parentRepo.getItem(parentId).map(dbListItemToExistingCounterItem(context, id, dateFormat)).
                subscribeWith(new DisposableSingleObserver<UpsertCounterModel.CounterModel>() {
                    @Override public void onSuccess(UpsertCounterModel.CounterModel counterListSettings) {
                        view.onDataReceived(counterListSettings);
                    }

                    @Override public void onError(Throwable e) {
                        view.onGetDataErrorResponse();
                    }
                }));
    }

    private Function<ListModel, UpsertCounterModel.CounterModel> dbListItemToNewCounterItem(Context context, long id, DateFormat dateFormat) {

        return list -> {
            UpsertCounterModel.CounterModel.Builder counterListSettings = getNewCounterBuilderFromDBListItem(list);
            counterListSettings = getNewListBuilderData(context, counterListSettings);
            return counterListSettings.build();
        };

    }

    private Function<ListModel, UpsertCounterModel.CounterModel> dbListItemToExistingCounterItem(Context context, long id, DateFormat dateFormat) {
        return list -> {
            UpsertCounterModel.CounterModel.Builder counterListSettings = getExistingCounterBuilderFromDBListItem(list, id);
            counterListSettings = getExistingListBuilderData(context, counterListSettings, dateFormat, getCounterModel(list, id));
            return counterListSettings.build();
        };

    }

    private UpsertCounterModel.CounterModel.Builder getNewCounterBuilderFromDBListItem(ListModel list) {
        return new UpsertCounterModel.CounterModel.Builder().withDefaultValue(String.valueOf(list.getDefaultValue()))
                .withValue(String.valueOf(list.getDefaultValue()))
                .withIncrement(String.valueOf(list.getDefaultIncrement()))
                .withDecrement(String.valueOf(list.getDefaultDecrement()))
                .withBackgroundColor(list.getDefaultCardBackgroundColor())
                .withForeground(list.getDefaultCardForegroundColor())
                .withClickSound(0)
                .withVibrate(0)
                .withSpeakValue(0)
                .withSpeakName(0)
                .withKeepAwake(0)
                .withUseVolume(0);
    }

    private UpsertCounterModel.CounterModel.Builder getNewListBuilderData(Context context,
                                                                          UpsertCounterModel.CounterModel.Builder counterListSettings) {
        return counterListSettings.withDateCreated(context.getString(R.string.new_counter))
                .withDateModified(context.getString(R.string.new_counter))
                .withLastUsed(context.getString(R.string.new_counter))
                .withToolbarTitle(context.getString(R.string.new_counter));
    }

    private UpsertCounterModel.CounterModel.Builder getExistingCounterBuilderFromDBListItem(ListModel list, long id) {
        CounterModel selectedCounterModel = getCounterModel(list, id);
        if (selectedCounterModel == null) { return null; }

        return new UpsertCounterModel.CounterModel.Builder().withDefaultValue(String.valueOf(selectedCounterModel.getDefaultValue()))
                .withValue(String.valueOf(selectedCounterModel.getDefaultValue()))
                .withIncrement(String.valueOf(selectedCounterModel.getIncrement()))
                .withDecrement(String.valueOf(selectedCounterModel.getDecrement()))
                .withBackgroundColor(selectedCounterModel.getBackground())
                .withForeground(selectedCounterModel.getForeground())
                .withClickSound(selectedCounterModel.getClickSound())
                .withVibrate(selectedCounterModel.getVibrate())
                .withName(selectedCounterModel.getName())
                .withNote(selectedCounterModel.getNote())
                .withSpeakValue(selectedCounterModel.getSpeechOutputValue())
                .withSpeakName(selectedCounterModel.getSpeechOutputName())
                .withKeepAwake(selectedCounterModel.getKeepAwake())
                .withUseVolume(selectedCounterModel.getVolumeKey());
    }

    private UpsertCounterModel.CounterModel.Builder getExistingListBuilderData(Context context,
                                                                               UpsertCounterModel.CounterModel.Builder counterListSettings,
                                                                               DateFormat dateFormat,
                                                                               CounterModel counterModel) {
        return counterListSettings.withDateCreated(dateFormat.format(counterModel.getCreated()))
                .withDateModified(dateFormat.format(counterModel.getEdited()))
                .withLastUsed(dateFormat.format(counterModel.getValueChanged()))
                .withToolbarTitle(counterModel.getName());
    }

    @Nullable private CounterModel getCounterModel(ListModel list, long id) {
        CounterModel selectedCounterModel = null;
        for (CounterModel counterModel : list.getCounters()) {
            if (counterModel.getId() == id) {
                selectedCounterModel = counterModel;
                break;
            }
        }

        if (selectedCounterModel == null) {
            return null;
        }
        return selectedCounterModel;
    }

    @Override public void loadNewList(Context context, DateFormat dateFormat, Long id) {
        compositeDisposable.add(parentRepo.getItem(id).map(dbListItemToNewCounterItem(context, id, dateFormat)).
                subscribeWith(new DisposableSingleObserver<UpsertCounterModel.CounterModel>() {
                    @Override public void onSuccess(UpsertCounterModel.CounterModel counterListSettings) {
                        view.onDataReceived(counterListSettings);
                    }

                    @Override public void onError(Throwable e) {
                        view.onGetDataErrorResponse();
                    }
                }));

    }

    @Override public void saveData(UpsertCounterModel.CounterModel model, Long id, Long parentId) {

        Completable completable;
        if (id == 0) {
            completable = parentRepo.insertNewChildItem(parentId,
                    new CounterModel(0,
                            new Date(),
                            new Date(),
                            new Date(),
                            model.getNote(),
                            model.getClickSound(),
                            model.getVibrate(),
                            model.getSpeakValue(),
                            model.getSpeakName(),
                            model.getKeepAwake(),
                            model.getUseVolume(),
                            model.getName(),
                            Integer.parseInt(model.getValue()),
                            Integer.parseInt(model.getDefaultValue()),
                            Integer.parseInt(model.getIncrement()),
                            Integer.parseInt(model.getDecrement()),
                            model.getBackgroundColor(),
                            model.getForegroundColor(),
                            0));
        } else {
            completable = repo.updateItem(new CounterModel(id,
                    new Date(),
                    new Date(),
                    new Date(),
                    model.getNote(),
                    model.getClickSound(),
                    model.getVibrate(),
                    model.getSpeakValue(),
                    model.getSpeakName(),
                    model.getKeepAwake(),
                    model.getUseVolume(),
                    model.getName(),
                    Integer.parseInt(model.getValue()),
                    Integer.parseInt(model.getDefaultValue()),
                    Integer.parseInt(model.getIncrement()),
                    Integer.parseInt(model.getDecrement()),
                    model.getBackgroundColor(),
                    model.getForegroundColor(),
                    0));
        }
        compositeDisposable.add(completable.subscribeOn(Schedulers.io()).observeOn(mainScheduler).subscribeWith(new DisposableCompletableObserver() {

            @Override public void onComplete() {
                view.onSaveDataSuccess();
            }

            @Override public void onError(Throwable e) {
                view.onSaveDataErrorResponse();
            }
        }));
    }

    private UpsertCounterModel.CounterModel.Builder getExistingListBuilderData(Context context,
                                                                               DateFormat dateFormat,
                                                                               ListModel list,
                                                                               UpsertCounterModel.CounterModel.Builder counterListSettings) {
        //        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        Date dateCreated = list.getDateCreated();
        Date dateModified = list.getDateUsed();
        Date dateEdited = list.getDateModified();
        Log.d(TAG, dateCreated.toString());
        return counterListSettings.withDateCreated(dateFormat.format(dateCreated)).withDateModified(dateFormat.format(dateEdited)).withLastUsed(
                dateFormat.format(dateModified)).withToolbarTitle(context.getString(R.string.edit_list));
        //        counterListSettings = builder;
        //        return builder;
    }


}
