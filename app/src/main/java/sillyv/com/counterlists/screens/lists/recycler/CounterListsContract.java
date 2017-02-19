package sillyv.com.counterlists.screens.lists.recycler;

import android.content.Context;


/**
 * Created by Vasili on 2/18/2017.
 *
 */

public interface CounterListsContract {

    interface CounterListsPresenter {
        void getData(Context context, CounterListsContract.CounterListsView view);

        void deleteItems(Context context, CounterListsView view, CounterListsModel.IDList idList);
    }

    interface CounterListsView {
        void onDataReceived(CounterListsModel model);
    }


}
