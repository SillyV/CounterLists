package sillyv.com.counterlists.screens.counters.upsert;

import android.content.Context;

import java.text.DateFormat;

import sillyv.com.counterlists.screens.Contracts;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 */

public interface UpsertCounterContract {

    interface UpsertCounterPresenter {


        void loadData(Context context, DateFormat dateFormat, Long id, long parentId);

        void loadNewList(Context context, DateFormat dateFormat, Long id);

        void saveData(UpsertCounterModel.CounterModel model, Long id, Long parentId);
    }


    interface UpsertCounterView<D>
            extends Contracts.UpsertContract.UpsertViewContract<D> {}
}
