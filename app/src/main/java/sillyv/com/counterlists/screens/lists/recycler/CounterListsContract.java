package sillyv.com.counterlists.screens.lists.recycler;

import java.util.List;

import sillyv.com.counterlists.baseline.BaseListView;

/**
 * Created by Vasili on 2/18/2017.
 *
 */

interface CounterListsContract {

    interface CounterListsPresenter {

        void getData();

        void deleteItems(List<Long> idList);
    }

    interface CounterListsView<D>extends BaseListView<D> {

    }


}
