package sillyv.com.counterlists.baseline;

/**
 * Created by Vasili.Fedotov on 2/21/2017.
 *
 */

public interface BaseView<D> {
    void onDataReceived(D d);

    void onGetDataErrorResponse();

    void onDeleteItemsErrorResponse();

    void onSaveDataErrorResponse();

    void onSaveDataSuccess();
}