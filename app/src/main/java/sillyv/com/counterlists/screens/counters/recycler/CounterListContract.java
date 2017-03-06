package sillyv.com.counterlists.screens.counters.recycler;

import java.util.List;

import sillyv.com.counterlists.baseline.BaseListView;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 *
 */

public interface CounterListContract {

    interface CounterListPresenter {

        void getData(Long id);

        void deleteItems(List<Long> idList);
    }

    interface CounterListView<D>extends BaseListView<D> {

        void onSaveInteractionSuccess();
    }
}
