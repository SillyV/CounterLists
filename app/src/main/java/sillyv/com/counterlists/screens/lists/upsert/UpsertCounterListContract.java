package sillyv.com.counterlists.screens.lists.upsert;

import android.content.Context;

import java.text.DateFormat;

/**
 * Created by Vasili on 2/18/2017.
 *
 */

public interface UpsertCounterListContract {

    interface UpsertCounterListPresenter {


        void loadData(Context context, DateFormat dateFormat, UpsertCounterListModel.Identifier identifier);

        void saveData(UpsertCounterListModel.CounterListSettings model, UpsertCounterListModel.Identifier identifier);
    }

    interface UpsertCounterListView {
        void onDataReceived(UpsertCounterListModel.CounterListSettings model);

        void onDataError();
    }
}
