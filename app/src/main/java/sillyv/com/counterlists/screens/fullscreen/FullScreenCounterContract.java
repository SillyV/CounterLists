package sillyv.com.counterlists.screens.fullscreen;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 *
 */

public interface FullScreenCounterContract {



    interface fullScreenView{
        void onDataReceived(FullScreenCounterModel.CounterModel data);

        void onGetDataErrorResponse();

        void onSaveInteractionSuccess();

        void onSaveInteractionError();

        void onVibrateChangedSuccess();

        void onVibrateChangeError();

        void onVolumeChangedSuccess();

        void onVolumeChangeError();

        void onResetSuccess();

        void onResetError();

        void onDeleteItemSuccess();

        void onDeleteItemError();
    }

}
