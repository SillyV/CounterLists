package sillyv.com.counterlists.screens.lists.recycler;

/**
 * Created by Vasili on 2/18/2017.
 *
 */

interface CounterListsContract {

    interface CounterListsPresenter {

        void getData();

        void deleteItems(CounterListsModel.IDList idList);
    }

    interface CounterListsView {
        void onDataReceived(CounterListsModel model);

        void onErrorResponse();
    }


}
