package sillyv.com.counterlists.screens.lists.upsert;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.database.controllers.ListController;
import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.CounterModel;
import sillyv.com.counterlists.database.models.ListModel;
import sillyv.com.counterlists.screens.ParentTest;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class) public class ListUpsertPresenterTest
        extends ParentTest {
    private final UpsertCounterListModel.CounterListSettings MODEL = getModel();
    private final ListModel LIST_ITEM_NEW_ITEM = getListModel(true);
    private final ListModel LIST_ITEM_EXISTING_ITEM = getListModel(true);
    @Rule public MockitoRule rule = MockitoJUnit.rule();
    @Mock Context mMockContext;
    @Mock RealmRepository<ListModel, CounterModel> repo;
    @Mock UpsertCounterListContract.UpsertCounterListView<UpsertCounterListModel.CounterListSettings> view;
    private UpsertCounterListContract.UpsertCounterListPresenter presenter;
    private UpsertCounterListModel.CounterListSettings MODEL_TO_SAVE = getModel();

    @Before public void setUp() throws Exception {
        presenter = new UpsertCounterListPresenter(view, repo, Schedulers.trampoline());
        when(mMockContext.getString(R.string.new_list)).thenReturn("new list");
        when(mMockContext.getString(R.string.edit_list)).thenReturn("Edit List");
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());

    }

    @Test public void loadData_newList() throws Exception {
        //given
        when(repo.getItem(0)).thenReturn(Single.just(LIST_ITEM_NEW_ITEM));
        //when
        presenter.loadData(mMockContext, DATE_FORMAT, new UpsertCounterListModel.Identifier(0L));
        //then
        verify(view, only()).onDataReceived(any(UpsertCounterListModel.CounterListSettings.class));
    }

    @Test public void loadData_newList_whenRepoThrowsError() throws Exception {
        //given
        when(repo.getItem(0)).thenReturn(Single.error(new RuntimeException("test")));
        //when
        presenter.loadData(mMockContext, DATE_FORMAT, new UpsertCounterListModel.Identifier(0L));
        //then
        verify(view, only()).onGetDataErrorResponse();
    }

    @Test public void loadData_existingList() throws Exception {
        //given
        when(repo.getItem(2)).thenReturn(Single.just(LIST_ITEM_EXISTING_ITEM));
        //when
        presenter.loadData(mMockContext, DATE_FORMAT, new UpsertCounterListModel.Identifier(2L));
        //then
        verify(view, only()).onDataReceived(any(UpsertCounterListModel.CounterListSettings.class));
    }

    @Test public void loadData_existingList_whenErrorFromRepo() throws Exception {
        //given
        when(repo.getItem(anyLong())).thenReturn(Single.error(new RuntimeException("Test")));
        //when
        presenter.loadData(mMockContext, DATE_FORMAT, new UpsertCounterListModel.Identifier(2L));
        //then
        verify(view, only()).onGetDataErrorResponse();
    }


    //    @Test public void loadData_whenRepoThrowsException() throws Exception {
    //        //given
    //        when(repo.getItem(2)).thenReturn(Single.error(new RuntimeException("test")));
    //        //when
    //        presenter.loadData(mMockContext, DATE_FORMAT, new UpsertCounterListModel.Identifier(2L));
    //        //then
    //        verify(view, only()).onGetDataErrorResponse();
    //    }

    @Test public void loadData_newList_testForFields() throws Exception {  //given
        when(repo.getItem(ListController.DEFAULT_LIST)).thenReturn(Single.just(LIST_ITEM_NEW_ITEM));
        //when
        presenter.loadData(mMockContext, DATE_FORMAT, new UpsertCounterListModel.Identifier(ListController.DEFAULT_LIST));
        //then
        ArgumentCaptor<UpsertCounterListModel.CounterListSettings>
                argument =
                ArgumentCaptor.forClass(UpsertCounterListModel.CounterListSettings.class);
        verify(view).onDataReceived(argument.capture());
        assertEquals(argument.getValue().getDefaultValue(), "3");
        assertEquals(argument.getValue().getDefaultIncrement(), "5");
        assertEquals(argument.getValue().getDefaultDecrement(), "7");
        assertEquals(argument.getValue().getBackgroundColor(), 11);
        assertEquals(argument.getValue().getDefaultColorCounterBackground(), 13);
        assertEquals(argument.getValue().getDefaultColorCounterText(), 17);
        assertEquals(argument.getValue().getName(), "Happy Name");
        assertEquals(argument.getValue().getNote(), "Happy Note");
        assertEquals(argument.getValue().getClickSound(), 0);
        assertEquals(argument.getValue().getKeepAwake(), 1);
        assertEquals(argument.getValue().getSpeakName(), 0);
        assertEquals(argument.getValue().getSpeakValue(), 2);
        assertEquals(argument.getValue().getVibrate(), 1);
        assertEquals(argument.getValue().getUseVolume(), 2);
        assertEquals(argument.getValue().getDateCreated(), "new list");
        assertEquals(argument.getValue().getDateModified(), "new list");
        assertEquals(argument.getValue().getLastUsed(), "new list");
        assertEquals(argument.getValue().getToolbarTitle(), "new list");

    }

    @Test public void loadData_existingList_testForFields() throws Exception {
        when(repo.getItem(1)).thenReturn(Single.just(LIST_ITEM_NEW_ITEM));
        //when
        presenter.loadData(mMockContext, DATE_FORMAT, new UpsertCounterListModel.Identifier(1));
        //then
        ArgumentCaptor<UpsertCounterListModel.CounterListSettings>
                argument =
                ArgumentCaptor.forClass(UpsertCounterListModel.CounterListSettings.class);
        verify(view).onDataReceived(argument.capture());
        assertEquals(argument.getValue().getDefaultValue(), "3");
        assertEquals(argument.getValue().getDefaultIncrement(), "5");
        assertEquals(argument.getValue().getDefaultDecrement(), "7");
        assertEquals(argument.getValue().getBackgroundColor(), 11);
        assertEquals(argument.getValue().getDefaultColorCounterBackground(), 13);
        assertEquals(argument.getValue().getDefaultColorCounterText(), 17);
        assertEquals(argument.getValue().getName(), "Happy Name");
        assertEquals(argument.getValue().getNote(), "Happy Note");
        assertEquals(argument.getValue().getClickSound(), 0);
        assertEquals(argument.getValue().getKeepAwake(), 1);
        assertEquals(argument.getValue().getSpeakName(), 0);
        assertEquals(argument.getValue().getSpeakValue(), 2);
        assertEquals(argument.getValue().getVibrate(), 1);
        assertEquals(argument.getValue().getUseVolume(), 2);
        assertEquals(argument.getValue().getDateCreated(), DATE_FORMAT.format(getDateCreated()));
        assertEquals(argument.getValue().getDateModified(), DATE_FORMAT.format(getDateCreated()));
        assertEquals(argument.getValue().getLastUsed(), DATE_FORMAT.format(getDateCreated()));
        assertEquals(argument.getValue().getToolbarTitle(), "Edit List");
    }/*.format(dateCreated)*/

    @Test public void saveData_newItem() throws Exception {
        when(repo.insert(any())).thenReturn(Completable.complete());
        //when
        presenter.saveData(MODEL, new UpsertCounterListModel.Identifier(0));
        //then
        verify(repo, only()).insert(any(ListModel.class));
        verify(view, only()).onSaveDataSuccess();

    }

    @Test public void saveData_existingItem() throws Exception {
        when(repo.updateItem(any())).thenReturn(Completable.complete());

        //given
        presenter.saveData(MODEL_TO_SAVE, new UpsertCounterListModel.Identifier(1));
        //then
        verify(repo, only()).updateItem(any(ListModel.class));
        verify(view, only()).onSaveDataSuccess();

    }

    @Test public void saveData_newItem_testForFields() throws Exception {
        when(repo.insert(any())).thenReturn(Completable.complete());
        presenter.saveData(MODEL, new UpsertCounterListModel.Identifier(0));
        //then

        ArgumentCaptor<ListModel> argument = ArgumentCaptor.forClass(ListModel.class);
        verify(repo).insert(argument.capture());
        assertEquals(argument.getValue().getDefaultValue(), 1);
        assertEquals(argument.getValue().getDefaultIncrement(), 2);
        assertEquals(argument.getValue().getDefaultDecrement(), 3);
        assertEquals(argument.getValue().getBackground(), 100);
        assertEquals(argument.getValue().getDefaultCardBackgroundColor(), 300);
        assertEquals(argument.getValue().getDefaultCardForegroundColor(), 400);
        assertEquals(argument.getValue().getName(), "test");
        assertEquals(argument.getValue().getNote(), "test note");
        assertEquals(argument.getValue().getClickSound(), 0);
        assertEquals(argument.getValue().getKeepAwake(), 2);
        assertEquals(argument.getValue().getSpeechOutputName(), 0);
        assertEquals(argument.getValue().getSpeechOutputValue(), 1);
        assertEquals(argument.getValue().getVibrate(), 1);
        assertEquals(argument.getValue().getVolumeKey(), 2);
    }

    @Test public void saveData_existingItem_testForFields() throws Exception {
        when(repo.updateItem(any())).thenReturn(Completable.complete());
        //        when(repo.updateItem(any())).thenReturn(Completable.error(new RuntimeException("Test")));
        presenter.saveData(MODEL_TO_SAVE, new UpsertCounterListModel.Identifier(1));
        //then
        ArgumentCaptor<ListModel> argument = ArgumentCaptor.forClass(ListModel.class);
        verify(repo).updateItem(argument.capture());
        assertEquals(argument.getValue().getDefaultValue(), 1);
        assertEquals(argument.getValue().getDefaultIncrement(), 2);
        assertEquals(argument.getValue().getDefaultDecrement(), 3);
        assertEquals(argument.getValue().getBackground(), 100);
        assertEquals(argument.getValue().getDefaultCardBackgroundColor(), 300);
        assertEquals(argument.getValue().getDefaultCardForegroundColor(), 400);
        assertEquals(argument.getValue().getName(), "test");
        assertEquals(argument.getValue().getNote(), "test note");
        assertEquals(argument.getValue().getClickSound(), 0);
        assertEquals(argument.getValue().getKeepAwake(), 2);
        assertEquals(argument.getValue().getSpeechOutputName(), 0);
        assertEquals(argument.getValue().getSpeechOutputValue(), 1);
        assertEquals(argument.getValue().getVibrate(), 1);
        assertEquals(argument.getValue().getVolumeKey(), 2);
        assertEquals(argument.getValue().getId(), 1);

    }

    @Test public void saveData_whenRepoThrowsException() throws Exception {
        when(repo.updateItem(any())).thenReturn(Completable.error(new RuntimeException("Test")));
        presenter.saveData(MODEL_TO_SAVE, new UpsertCounterListModel.Identifier(1));
        verify(view, only()).onSaveDataErrorResponse();


    }

    @Test(expected = Exception.class) public void saveData_existingItem_whenItemDoesNotExist() throws Exception {

        doThrow(new Exception()).when(repo).updateItem(any(ListModel.class));

        presenter.saveData(getModel(), new UpsertCounterListModel.Identifier(1));

        verify(view, only()).onSaveDataErrorResponse();
    }

    @After public void tearDown() throws Exception {
        RxJavaPlugins.reset();
    }

}