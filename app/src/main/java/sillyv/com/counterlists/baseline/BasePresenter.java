package sillyv.com.counterlists.baseline;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Vasili.Fedotov on 2/21/2017.
 *
 */

public abstract class BasePresenter {


    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
