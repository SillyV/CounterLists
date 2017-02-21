package sillyv.com.counterlists.screens.counters.recycler;

import java.util.List;

import io.reactivex.functions.Function;
import sillyv.com.counterlists.baseline.BaseListView;
import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.CounterModel;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 *
 */

public class CounterListPresenter
        {


    protected Function<List<CounterModel>, CounterListModel> dbItemToViewItem() {
        return null;
    }

}
