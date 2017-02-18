package sillyv.com.counterlists.screens.lists.upsert;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;

import java.util.Date;

import sillyv.com.counterlists.database.controllers.ListController;
import sillyv.com.counterlists.database.dbitems.CounterList;
import sillyv.com.counterlists.database.models.ListModel;

/**
 * Created by vasil on 2/18/2017.
 */

public class UpsertCounterListPresenter implements UpsertCounterListContract.UpsertCounterListPresenter {

    @Override
    public void getData(Context context, UpsertCounterListContract.UpsertCounterListView view, UpsertCounterListModel.Identifier identifier) {
        CounterList list = ListController.getInstance().getCounterList(identifier.getId());
        UpsertCounterListModel.CounterListSettings.Builder counterListSettings =
                getBuilderFromDBItem(list);

        if (identifier.getId() == ListController.DEFAULT_LIST) {
            counterListSettings = getNewListBuilderData(counterListSettings);
        } else {
            counterListSettings = getExistingListBuilderData(context, list, counterListSettings);
        }
        view.onDataReceived(counterListSettings.build());
    }

    private UpsertCounterListModel.CounterListSettings.Builder getExistingListBuilderData(Context context, CounterList list, UpsertCounterListModel.CounterListSettings.Builder counterListSettings) {
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        Date dateCreated = list.getCreated();
        Date dateModified = list.getValueChanged();
        Date dateEdited = list.getEdited();
        UpsertCounterListModel.CounterListSettings.Builder builder = counterListSettings.dateCreated(dateFormat.format(dateCreated))
                .dateModified(dateFormat.format(dateEdited))
                .lastUsed(dateFormat.format(dateModified))
                .toolbarTitle("Edit list");
        counterListSettings = builder;
        return counterListSettings;
    }

    private UpsertCounterListModel.CounterListSettings.Builder getNewListBuilderData(UpsertCounterListModel.CounterListSettings.Builder counterListSettings) {
        return counterListSettings.dateCreated("new list")
                .dateModified("new list")
                .lastUsed("new list")
                .toolbarTitle("New list");
    }

    private UpsertCounterListModel.CounterListSettings.Builder getBuilderFromDBItem(CounterList list) {
        return new UpsertCounterListModel.CounterListSettings
                .Builder()
                .name(list.getName())
                .note(list.getNote())
                .defaultVaule(String.valueOf(list.getDefaultValue()))
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
    public boolean saveData(Context context, UpsertCounterListModel.CounterListSettings model, UpsertCounterListModel.Identifier identifier) {


        ListModel dbModel = getListModel(model);
        if (identifier.getId() == ListController.DEFAULT_LIST) {
            ListController.getInstance().addNewList(dbModel);
        } else {
            dbModel.setId(identifier.getId());
            ListController.getInstance().updateList(dbModel);
        }
        return true;
    }

    @NonNull
    private ListModel getListModel(UpsertCounterListModel.CounterListSettings model) {
        return new ListModel(Integer.parseInt(model.getDefaultVaule()),
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
