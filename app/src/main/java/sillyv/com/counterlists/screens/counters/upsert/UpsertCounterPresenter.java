package sillyv.com.counterlists.screens.counters.upsert;

import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.baseline.BasePresenter;
import sillyv.com.counterlists.baseline.BaseView;
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


    private final BaseView<UpsertCounterModel.CounterModel> view;
    private final RealmRepository<CounterModel, Object> repo;
    private final RealmRepository<ListModel, CounterModel> parentRepo;

    public UpsertCounterPresenter(BaseView<UpsertCounterModel.CounterModel> view,
                                  RealmRepository<CounterModel, Object> repo,
                                  RealmRepository<ListModel, CounterModel> parentRepo) {
        this.view = view;
        this.repo = repo;
        this.parentRepo = parentRepo;
    }

    @Override public void loadData(Context context, DateFormat dateFormat, Long id, long parentId) {
        if (id == CounterController.DEFAULT_COUNTER) {
            loadNewList(context, dateFormat, parentId);
            return;
        }

    }

    @Override public void loadNewList(Context context, DateFormat dateFormat, Long id) {

        Single<ListModel> item = parentRepo.getItem(id);

        if (item == null) {
            view.onErrorResponse();
            return;
        }

        compositeDisposable.add(item.map(dbListItemToNewCounterItem(context, id, dateFormat)).
                subscribeWith(new DisposableSingleObserver<UpsertCounterModel.CounterModel>() {
                    @Override public void onSuccess(UpsertCounterModel.CounterModel counterListSettings) {
                        view.onDataReceived(counterListSettings);
                    }

                    @Override public void onError(Throwable e) {
                        view.onErrorResponse();
                    }
                }));

    }

    @Override public void saveData(UpsertCounterModel.CounterModel model, Long id, Long parentId) {
        if (id == 0) {
            parentRepo.insertNewChildItem(parentId,
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
                            0)


            );
        } else {

        }
    }


    private Function<ListModel, UpsertCounterModel.CounterModel> dbListItemToNewCounterItem(Context context, long id, DateFormat dateFormat) {

        return list -> {
            UpsertCounterModel.CounterModel.Builder counterListSettings = getNewCounterBuilderFromDBListItem(list);
            counterListSettings = getNewListBuilderData(context, counterListSettings);
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