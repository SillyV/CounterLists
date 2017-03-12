package sillyv.com.counterlists.screens.fullscreen;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.CounterModel;
import sillyv.com.counterlists.screens.ParentTest;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Vasili.Fedotov on 3/10/2017.
 *
 */
public class FullScreenCounterPresenterTest
        extends ParentTest {


    public static final long COUNTER_ID = 5L;
    private final CounterModel COUNTER_MODEL = getCounterModel(5);
    @Rule public MockitoRule rule = MockitoJUnit.rule();
    @Mock FullScreenCounterContract.fullScreenView view;
    @Mock RealmRepository<CounterModel, Object> repo;
    private FullScreenCounterPresenter presenter;

    @Before public void setUp() throws Exception {
        presenter = new FullScreenCounterPresenter(view, repo, Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test public void getData() throws Exception {
        when(repo.getItem(5)).thenReturn(Single.just(COUNTER_MODEL));

        presenter.getData(COUNTER_ID);

        verify(view, only()).onDataReceived(any());
    }

    @Test public void getData_errorFromRepo() throws Exception {
        when(repo.getItem(any())).thenReturn(Single.error(new RuntimeException("test")));

        presenter.getData(COUNTER_ID);

        verify(view, only()).onGetDataErrorResponse();
    }

    @Test public void getData_TestFields() throws Exception {
        Assert.assertEquals(1,2); ////TODO 3/10/2017
    }

    @Test public void saveInteraction() throws Exception {
        when(repo.updateItemValue(any(), any())).thenReturn(Completable.complete());

        presenter.saveInteraction(COUNTER_ID, 10);

        verify(view, only()).onSaveInteractionSuccess();
    }

    @Test public void saveInteraction_errorFromRepo() throws Exception {
        when(repo.updateItemValue(any(), any())).thenReturn(Completable.error(new RuntimeException("test")));

        presenter.saveInteraction(COUNTER_ID, 10);

        verify(view, only()).onSaveInteractionError();
    }

    @Test public void saveInteraction_TestFields() throws Exception {
        Assert.assertEquals(1,2); ////TODO 3/10/2017
    }

    @Test public void toggleVibration() throws Exception {
        when(repo.updateItemVibration()).thenReturn(Completable.complete());

        presenter.setVibration(COUNTER_ID, true);

        verify(view).onVibrateChangedSuccess();
    }

    @Test public void toggleVibration_errorFromRepo() throws Exception {
        when(repo.updateItemVibration()).thenReturn(Completable.error(new RuntimeException("test")));

        presenter.setVibration(COUNTER_ID, true);

        verify(view).onVibrateChangeError();
    }

    @Test public void toggleVibration_TestFields() throws Exception {
        Assert.assertEquals(1,2); ////TODO 3/10/2017
    }

    @Test public void toggleSound() throws Exception {
        when(repo.updateItemVolume(any(), any())).thenReturn(Completable.complete());

        presenter.setVolume(COUNTER_ID, 0);

        verify(view).onVolumeChangedSuccess();

    }

    @Test public void toggleSound_errorFromRepo() throws Exception {
        when(repo.updateItemVolume(any(), any())).thenReturn(Completable.error(new RuntimeException("test")));

        presenter.setVolume(COUNTER_ID, 0);

        verify(view).onVolumeChangeError();
    }

    @Test public void toggleSound_TestFields() throws Exception {
        Assert.assertEquals(1,2); ////TODO 3/10/2017
    }

    @Test public void reset() throws Exception {
        when(repo.resetItems(any())).thenReturn(Completable.complete());

        presenter.resetItem(COUNTER_ID);

        verify(view).onResetSuccess();
    }

    @Test public void reset_errorFromRepo() throws Exception {
        when(repo.resetItems(any())).thenReturn(Completable.error(new RuntimeException("test")));

        presenter.resetItem(COUNTER_ID);

        verify(view).onResetError();
    }

    @Test public void reset_TestFields() throws Exception {
        Assert.assertEquals(1,2); ////TODO 3/10/2017
    }

    @Test public void deleteCounter() throws Exception {
        when(repo.deleteItems(any())).thenReturn(Completable.complete());

        presenter.deleteItem(COUNTER_ID);

        verify(view).onDeleteItemSuccess();
    }

    @Test public void deleteCounter_errorFromRepo() throws Exception {
        when(repo.deleteItems(any())).thenReturn(Completable.error(new RuntimeException("test")));

        presenter.deleteItem(COUNTER_ID);

        verify(view).onDeleteItemError();
    }

    @Test public void deleteCounter_TestFields() throws Exception {
        Assert.assertEquals(1,2); ////TODO 3/10/2017
        Assert.assertEquals(1,2);
    }


    @After public void tearDown() throws Exception {
        RxJavaPlugins.reset();
    }

}