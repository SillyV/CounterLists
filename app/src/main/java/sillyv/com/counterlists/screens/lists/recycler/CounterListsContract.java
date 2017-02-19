package sillyv.com.counterlists.screens.lists.recycler;

import android.content.Context;


/**
 * Created by Vasili on 2/18/2017.
 *
 */

public interface CounterListsContract {

    interface CounterListsPresenter {

        void getData();

        void deleteItems(CounterListsModel.IDList idList);
    }

    interface CounterListsView {
        void onDataReceived(CounterListsModel model);

        void onErrorResponse();
    }


}
