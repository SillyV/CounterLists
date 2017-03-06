package sillyv.com.counterlists.screens.counters.recycler;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Date;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.CounterModel;
import sillyv.com.counterlists.database.models.ListModel;
import sillyv.com.counterlists.screens.ParentTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Vasili.Fedotov on 2/21/2017.
 */
public class CounterRecyclerPResenterTest
        extends ParentTest {
    public static final int NEW_VALUE = 19;
    public static final long CHILD_ID = 7L;
    public static final long PARENT_ID = 91L;
    private final ListModel LIST_ITEM = getListModel(true);
    private final ListModel EMPTY_LIST_ITEM = getListModel(false);
    @Rule public MockitoRule rule = MockitoJUnit.rule();
    @Mock CounterListContract.CounterListView<CounterListModel> view;
    @Mock RealmRepository<CounterModel, Object> childRepo;
    @Mock RealmRepository<ListModel, CounterModel> parentRepo;
    private CounterListPresenter presenter;

    @Before public void setUp() throws Exception {
        presenter = new CounterListPresenter(view, childRepo, parentRepo,Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());

    }

    @Test public void getData() throws Exception {
        when(parentRepo.getItem(2)).thenReturn(Single.just(LIST_ITEM));
        presenter.getData(2);
        ArgumentCaptor<CounterListModel> argument = ArgumentCaptor.forClass(CounterListModel.class);
        verify(view, only()).onDataReceived(any(CounterListModel.class));
        verify(view).onDataReceived(argument.capture());
        assertEquals(argument.getValue().getCounterModels().size(), LIST_ITEM.getCounters().size());
    }

    @Test public void getData_whenRepoIsEmpty() throws Exception {
        when(parentRepo.getItem(2)).thenReturn(Single.just(EMPTY_LIST_ITEM));

        presenter.getData(2);

        ArgumentCaptor<CounterListModel> argument = ArgumentCaptor.forClass(CounterListModel.class);
        verify(view, only()).onDataReceived(any(CounterListModel.class));
        verify(view).onDataReceived(argument.capture());
        assertEquals(argument.getValue().getCounterModels().size(), 0);
    }

    @Test public void getData_whenRepoThrowsException() throws Exception {

        when(parentRepo.getItem(2)).thenReturn(Single.error(new RuntimeException("test")));

        presenter.getData(2);

        verify(view, only()).onGetDataErrorResponse();
    }

    @Test public void getData_checkForFields() throws Exception {
        when(parentRepo.getItem(2)).thenReturn(Single.just(LIST_ITEM));
        presenter.getData(2);
        ArgumentCaptor<CounterListModel> argument = ArgumentCaptor.forClass(CounterListModel.class);
        verify(view, only()).onDataReceived(any(CounterListModel.class));
        verify(view).onDataReceived(argument.capture());
        assertEquals(argument.getValue().getCounterModels().get(0).getAmount(), 6);
        assertEquals(argument.getValue().getCounterModels().get(0).getDecrement(), 9);
        assertEquals(argument.getValue().getCounterModels().get(0).getIncrement(), 8);
        assertEquals(argument.getValue().getCounterModels().get(0).getName(), "Test Name 1");
        assertEquals(argument.getValue().getCounterModels().get(0).getCustomOrder(), 1);
        assertEquals(argument.getValue().getCounterModels().get(0).isClickSound(), false);
        assertEquals(argument.getValue().getCounterModels().get(0).isSpeakName(), true);
        assertEquals(argument.getValue().getCounterModels().get(0).isSpeakValue(), false);
        assertEquals(argument.getValue().getCounterModels().get(0).isVibrate(), true);

    }


    @Test public void deleteCounters() throws Exception {
        when(parentRepo.getItem(PARENT_ID)).thenReturn(Single.just(LIST_ITEM));
        when(childRepo.deleteItems(any())).thenReturn(Completable.complete());

        presenter.deleteCounter(ID_LIST, PARENT_ID);

        verify(childRepo, only()).deleteItems(any());
        verify(view, only()).onSaveDataSuccess();
    }

    @Test public void deleteCounters_whenRepoThrowsException() throws Exception {
        when(parentRepo.getItem(PARENT_ID)).thenReturn(Single.just(LIST_ITEM));
        when(childRepo.deleteItems(any())).thenReturn(Completable.error(new RuntimeException("test")));

        presenter.deleteCounter(ID_LIST, PARENT_ID);

        verify(view, times(1)).onDeleteItemsErrorResponse();
    }

    //    @Test public void deleteCounters_checkGetDataIsCalled() throws Exception {

    @Test public void saveInteraction() throws Exception {
        when(parentRepo.updateItemValue(any(), anyInt())).thenReturn(Completable.complete());
        when(childRepo.updateItemValue(any(), anyInt())).thenReturn(Completable.complete());


        presenter.saveInteraction(CHILD_ID, PARENT_ID, NEW_VALUE);

        verify(childRepo).updateItemValue(anyLong(), anyInt());
        verify(parentRepo).updateItemValue(anyLong(), anyInt());
        verify(view).onSaveInteractionSuccess();

    }
    //        when(parentRepo.getItem(PARENT_ID)).thenReturn(Single.just(LIST_ITEM));
    //        when(childRepo.deleteItems(any())).thenReturn(Completable.complete());

    @Test public void saveInteraction_checkForFields() throws Exception {
        when(parentRepo.updateItemValue(any(), anyInt())).thenReturn(Completable.complete());
        when(childRepo.updateItemValue(any(), anyInt())).thenReturn(Completable.complete());

        presenter.saveInteraction(CHILD_ID, PARENT_ID, NEW_VALUE);

        ArgumentCaptor<Long> childId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> parentID = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Integer> newValue = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> newValueForParent = ArgumentCaptor.forClass(Integer.class);

        verify(childRepo).updateItemValue(childId.capture(), newValue.capture());
        verify(parentRepo).updateItemValue(parentID.capture(), newValueForParent.capture());

        assertEquals(childId.getValue().longValue(), CHILD_ID);
        assertEquals(parentID.getValue().longValue(), PARENT_ID);
        assertEquals(newValue.getValue().intValue(), NEW_VALUE);
        assertEquals(newValueForParent.getValue().intValue(), NEW_VALUE);
    }
    //        presenter.deleteItems(ID_LIST);

    @Test public void saveCustomOrder() throws Exception {
        assertEquals(1, 2);
    }
    //        ArgumentCaptor<CounterListsModel> argument = ArgumentCaptor.forClass(CounterListsModel.class);

    protected ListModel getListModel(boolean addCounters) {
        ListModel e = new ListModel(1, 0, 0, 0, 0, 0, 0, "", 0, 1, 2, 0, 1, 2, "");
        if (addCounters) {
            ArrayList<CounterModel> counters = new ArrayList<>();
            CounterModel e1 = new CounterModel(1,
                    new Date(),
                    new Date(),
                    new Date(),
                    "test Note 1",
                    0,
                    1,
                    2,
                    1,
                    4,
                    5,
                    "Test Name 1",
                    6,
                    7,
                    8,
                    9,
                    10,
                    11,
                    1);

            CounterModel e2 = new CounterModel(1,
                    new Date(),
                    new Date(),
                    new Date(),
                    "test Note",
                    0,
                    1,
                    2,
                    0,
                    1,
                    2,
                    "Test Name 2",
                    0,
                    0,
                    1,
                    1,
                    0,
                    0,
                    2);
            e2.setName("BBB");
            CounterModel e3 = new CounterModel(1,
                    new Date(),
                    new Date(),
                    new Date(),
                    "test Note",
                    0,
                    1,
                    2,
                    0,
                    1,
                    2,
                    "Test Name 3",
                    0,
                    0,
                    1,
                    1,
                    0,
                    0,
                    3);
            e3.setName("CCC");
            CounterModel e4 = new CounterModel(1,
                    new Date(),
                    new Date(),
                    new Date(),
                    "test Note",
                    0,
                    1,
                    2,
                    0,
                    1,
                    2,
                    "Test Name 4",
                    0,
                    0,
                    1,
                    1,
                    0,
                    0,
                    4);
            e4.setName("DDD");
            counters.add(e1);
            counters.add(e2);
            counters.add(e3);
            counters.add(e4);
            e.setCounters(counters);
        }
        return e;
    }
    //        verify(view, only()).onDataReceived(any(CounterListsModel.class));
    //        verify(view).onDataReceived(argument.capture());
    //        assertEquals(argument.getValue().getItems().size(), MANY_ITEMS.size());
    //    }
    //
    //    @Test public void deleteCounters_whenRepoThrowsException_checkGetDataIsCalled() throws Exception {
    //        when(repo.getItems()).thenReturn(Single.just(MANY_ITEMS));
    //        when(repo.deleteItems(any())).thenReturn(Completable.error(new RuntimeException("test")));
    //
    //        presenter.deleteItems(ID_LIST);
    //
    //        ArgumentCaptor<CounterListsModel> argument = ArgumentCaptor.forClass(CounterListsModel.class);
    //
    //        verify(view, times(1)).onDataReceived(any(CounterListsModel.class));
    //        verify(view).onDataReceived(argument.capture());
    //        assertEquals(argument.getValue().getItems().size(), MANY_ITEMS.size());
    //    }
    //
    //
    //
    //
    @After public void tearDown() throws Exception {
        RxJavaPlugins.reset();
    }

}