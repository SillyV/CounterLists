package sillyv.com.counterlists.screens.lists.upsert;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

import sillyv.com.counterlists.R;
import sillyv.com.counterlists.database.controllers.ListController;
import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.ListModel;


/**
 * Created by Vasili on 2/18/2017.
 */

class UpsertCounterListPresenter implements UpsertCounterListContract.UpsertCounterListPresenter {

    private static final String TAG = UpsertCounterListPresenter.class.getSimpleName();

    private UpsertCounterListContract.UpsertCounterListView view;
    private RealmRepository<ListModel> repo;

    UpsertCounterListPresenter(UpsertCounterListContract.UpsertCounterListView view, RealmRepository<ListModel> repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void loadData(Context context, UpsertCounterListModel.Identifier identifier) {
        ListModel list = repo.getItem(identifier.getId());

        if (list == null) {
            view.onDataError();
            return;
        }

        UpsertCounterListModel.CounterListSettings data = getCounterListSettings(context, identifier, list);

        view.onDataReceived(data);
    }

    @NonNull
    private UpsertCounterListModel.CounterListSettings getCounterListSettings(Context context, UpsertCounterListModel.Identifier identifier, ListModel list) {

        UpsertCounterListModel.CounterListSettings.Builder counterListSettings =
                getBuilderFromDBItem(list);

        if (identifier.getId() == ListController.DEFAULT_LIST) {
            counterListSettings = getNewListBuilderData(context, counterListSettings);
        } else {
            counterListSettings = getExistingListBuilderData(context, list, counterListSettings);
        }

        return counterListSettings.build();
    }

    private UpsertCounterListModel.CounterListSettings.Builder getExistingListBuilderData(Context context, ListModel list, UpsertCounterListModel.CounterListSettings.Builder counterListSettings) {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        Date dateCreated = list.getDateCreated();
        Date dateModified = list.getDateUsed();
        Date dateEdited = list.getDateModified();
        Log.d(TAG, dateCreated.toString());
        return
                counterListSettings
                        .dateCreated(dateFormat.format(dateCreated))
                        .dateModified(dateFormat.format(dateEdited))
                        .lastUsed(dateFormat.format(dateModified))
                        .toolbarTitle(context.getString(R.string.edit_list));
//        counterListSettings = builder;
//        return builder;
    }

    private UpsertCounterListModel.CounterListSettings.Builder getNewListBuilderData(Context context, UpsertCounterListModel.CounterListSettings.Builder counterListSettings) {
        return counterListSettings.dateCreated(context.getString(R.string.new_list))
                .dateModified(context.getString(R.string.new_list))
                .lastUsed(context.getString(R.string.new_list))
                .toolbarTitle(context.getString(R.string.new_list));
    }

    private UpsertCounterListModel.CounterListSettings.Builder getBuilderFromDBItem(ListModel list) {
        return new UpsertCounterListModel.CounterListSettings
                .Builder()
                .name(list.getName())
                .note(list.getNote())
                .defaultValue(String.valueOf(list.getDefaultValue()))
                .defaultIncrement(String.valueOf(list.getDefaultIncrement()))
                .defaultDecrement(String.valueOf(list.getDefaultDecrement()))
                .backgroundColor(list.getBackground())
                .defaultColorCounterBackground(list.getDefaultCardBackgroundColor())
                .defaultColorCounterText(list.getDefaultCardForegroundColor())
                .clickSound(list.isClickSound())
                .vibrate(list.isVibrate())
                .speakValue(list.isSpeechOutputValue())
                .speakName(list.isSpeechOutputName())
                .keepAwake(list.isKeepAwake())
                .useVolume(list.isVolumeKey());
    }

    @Override
    public void saveData(UpsertCounterListModel.CounterListSettings model, UpsertCounterListModel.Identifier identifier) {
        ListModel repoModel = getListModel(model);

        if (identifier.getId() == ListController.DEFAULT_LIST) {
            repo.addNewList(repoModel);
        } else {
            repoModel.setId(identifier.getId());
            repo.updateList(repoModel);
        }
    }

    @NonNull
    private ListModel getListModel(UpsertCounterListModel.CounterListSettings model) {
        return new ListModel(Integer.parseInt(model.getDefaultValue()),
                Integer.parseInt(model.getDefaultIncrement()),
                Integer.parseInt(model.getDefaultDecrement()),
                model.getBackgroundColor(),
                model.getDefaultColorCounterBackground(),
                model.getDefaultColorCounterText(),
                model.getNote(),
                model.isClickSound(),
                model.isVibrate(),
                model.isSpeakValue(),
                model.isSpeakName(),
                model.isKeepAwake(),
                model.isUseVolume(),
                model.getName());
    }


}
