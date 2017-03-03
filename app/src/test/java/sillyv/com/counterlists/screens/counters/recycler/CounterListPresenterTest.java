package sillyv.com.counterlists.screens.counters.recycler;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Date;

import io.reactivex.Single;
import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.CounterModel;
import sillyv.com.counterlists.database.models.ListModel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Vasili.Fedotov on 2/21/2017.
 */
public class CounterListPresenterTest {
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
        presenter = new CounterListPresenter(view, childRepo, parentRepo);
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

    @Test public void getData_whenRepoReturnsNull() throws Exception {
        when(parentRepo.getItems(2)).thenReturn(null);

        presenter.getData(2);

        verify(view).onGetDataErrorResponse();
    }

    @Test public void getData_whenRepoThrowsException() throws Exception {

        when(parentRepo.getItems(2)).thenReturn(Single.error(new RuntimeException("test")));

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
        assertEquals(1, 2);
    }

    @Test public void saveInteraction() throws Exception {
        presenter.saveInteraction(CHILD_ID, PARENT_ID, NEW_VALUE);
        verify(childRepo).updateItemValue(anyLong(), anyInt());
        verify(parentRepo).updateItemValue(anyLong(), anyInt());
    }


    @Test public void saveInteraction_checkForFields() throws Exception {
        presenter.saveInteraction(CHILD_ID, PARENT_ID, NEW_VALUE);

        assertEquals(1, 2);
    }

    @Test public void saveCustomOrder() throws Exception {
        assertEquals(1, 2);
    }

    private ListModel getListModel(boolean addCounters) {
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

}