package sillyv.com.counterlists.screens.lists.upsert;

import android.content.Context;
import android.support.annotation.NonNull;
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
import sillyv.com.counterlists.baseline.BaseView;
import sillyv.com.counterlists.database.controllers.ListController;
import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.CounterModel;
import sillyv.com.counterlists.database.models.ListModel;


/**
 * Created by Vasili.Fedotov on 2/18/2017.
 *
 */

class UpsertCounterListPresenter
        extends BasePresenter
        implements UpsertCounterListContract.UpsertCounterListPresenter {

    private static final String TAG = UpsertCounterListPresenter.class.getSimpleName();

    private final BaseView<UpsertCounterListModel.CounterListSettings> view;
    private final RealmRepository<ListModel, CounterModel> repo;
    private Scheduler mainScheduler;

    UpsertCounterListPresenter(BaseView<UpsertCounterListModel.CounterListSettings> view,
                               RealmRepository<ListModel, CounterModel> repo,
                               Scheduler mainScheduler) {
        this.mainScheduler = mainScheduler;
        this.view = view;
        this.repo = repo;
    }

    @Override public void loadData(Context context, DateFormat dateFormat, UpsertCounterListModel.Identifier identifier) {
        compositeDisposable.add(repo.getItem(identifier.getId()).subscribeOn(Schedulers.io()).map(dbItemToViewItem(context,
                identifier.getId(),
                dateFormat)).observeOn(mainScheduler).subscribeWith(new DisposableSingleObserver<UpsertCounterListModel.CounterListSettings>() {
            @Override public void onSuccess(UpsertCounterListModel.CounterListSettings counterListSettings) {
                view.onDataReceived(counterListSettings);
            }

            @Override public void onError(Throwable e) {
                view.onGetDataErrorResponse();
            }
        }));
    }

    private Function<ListModel, UpsertCounterListModel.CounterListSettings> dbItemToViewItem(Context context, long id, DateFormat dateFormat) {

        return list -> {
            UpsertCounterListModel.CounterListSettings.Builder counterListSettings = getBuilderFromDBItem(list);

            if (id == ListController.DEFAULT_LIST) {
                counterListSettings = getNewListBuilderData(context, counterListSettings);
            } else {
                counterListSettings = getExistingListBuilderData(context, dateFormat, list, counterListSettings);
            }

            return counterListSettings.build();
        };

    }


    private UpsertCounterListModel.CounterListSettings.Builder getBuilderFromDBItem(ListModel list) {
        return new UpsertCounterListModel.CounterListSettings.Builder().withName(list.getName())
                .withNote(list.getNote())
                .withDefaultValue(String.valueOf(list.getDefaultValue()))
                .withDefaultIncrement(String.valueOf(list.getDefaultIncrement()))
                .withDefaultDecrement(String.valueOf(list.getDefaultDecrement()))
                .withBackgroundColor(list.getBackground())
                .withDefaultColorCounterBackground(list.getDefaultCardBackgroundColor())
                .withDefaultColorCounterText(list.getDefaultCardForegroundColor())
                .withClickSound(list.getClickSound())
                .withVibrate(list.getVibrate())
                .withSpeakValue(list.getSpeechOutputValue())
                .withSpeakName(list.getSpeechOutputName())
                .withKeepAwake(list.getKeepAwake())
                .withUseVolume(list.getVolumeKey());
    }

    private UpsertCounterListModel.CounterListSettings.Builder getNewListBuilderData(Context context,
                                                                                     UpsertCounterListModel.CounterListSettings.Builder counterListSettings) {
        return counterListSettings.withDateCreated(context.getString(R.string.new_list))
                .withDateModified(context.getString(R.string.new_list))
                .withLastUsed(context.getString(R.string.new_list))
                .withToolbarTitle(context.getString(R.string.new_list));
    }

    private UpsertCounterListModel.CounterListSettings.Builder getExistingListBuilderData(Context context,
                                                                                          DateFormat dateFormat,
                                                                                          ListModel list,
                                                                                          UpsertCounterListModel.CounterListSettings.Builder counterListSettings) {
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

    @Override public void saveData(UpsertCounterListModel.CounterListSettings model, UpsertCounterListModel.Identifier identifier) {

        Completable completable;
        ListModel repoModel = getListModel(model);
        if (identifier.getId() == ListController.DEFAULT_LIST) {
            completable = repo.insert(repoModel);
        } else {
            repoModel.setId(identifier.getId());
            completable = repo.updateItem(repoModel);
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

    @NonNull private ListModel getListModel(UpsertCounterListModel.CounterListSettings model) {
        return new ListModel(Integer.parseInt(model.getDefaultValue()),
                Integer.parseInt(model.getDefaultIncrement()),
                Integer.parseInt(model.getDefaultDecrement()),
                model.getBackgroundColor(),
                model.getDefaultColorCounterBackground(),
                model.getDefaultColorCounterText(),
                model.getNote(),
                model.getClickSound(),
                model.getVibrate(),
                model.getSpeakValue(),
                model.getSpeakName(),
                model.getKeepAwake(),
                model.getUseVolume(),
                model.getName());

    }


}
