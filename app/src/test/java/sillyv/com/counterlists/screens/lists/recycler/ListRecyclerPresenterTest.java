package sillyv.com.counterlists.screens.lists.recycler;

import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.CounterModel;
import sillyv.com.counterlists.database.models.ListModel;
import sillyv.com.counterlists.screens.ParentTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 */
@RunWith(MockitoJUnitRunner.class) public class ListRecyclerPresenterTest
        extends ParentTest {
    @Rule public MockitoRule rule = MockitoJUnit.rule();
    @Mock CounterListsContract.CounterListsView<CounterListsModel> view;
    @Mock RealmRepository<ListModel, CounterModel> repo;
    private CounterListsPresenter presenter;
    private List<Long> ID_LIST = getIdList();

    @Before public void setUp() throws Exception {
        presenter = new CounterListsPresenter(view, repo, Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test public void deleteItems() throws Exception {
        when(repo.getItems()).thenReturn(Single.just(MANY_ITEMS));

        presenter.deleteItems(ID_LIST);

        verify(repo, times(ID_LIST.size())).deleteItem(anyLong());
    }

    @Test public void getData() throws Exception {
        when(repo.getItems()).thenReturn(Single.just(MANY_ITEMS));

        presenter.getData();

        ArgumentCaptor<CounterListsModel> argument = ArgumentCaptor.forClass(CounterListsModel.class);

        verify(view, only()).onDataReceived(any(CounterListsModel.class));
        verify(view).onDataReceived(argument.capture());
        assertEquals(argument.getValue().getItems().size(), MANY_ITEMS.size());

    }

    @Test public void getData_whenRepoIsEmpty() throws Exception {
        when(repo.getItems()).thenReturn(Single.just(Collections.emptyList()));

        presenter.getData();

        ArgumentCaptor<CounterListsModel> argument = ArgumentCaptor.forClass(CounterListsModel.class);
        verify(view).onDataReceived(Mockito.any(CounterListsModel.class));
        verify(view, Mockito.times(0)).onGetDataErrorResponse();
        verify(view).onDataReceived(argument.capture());
        assertEquals(argument.getValue().getItems().size(), 0);
    }

    @Test public void getData_whenRepoThrowsException() throws Exception {

        when(repo.getItems()).thenReturn(Single.error(new RuntimeException("test")));

        presenter.getData();

        verify(view, times(0)).onDataReceived(Mockito.any(CounterListsModel.class));
        verify(view).onGetDataErrorResponse();
    }

    @Test public void getData_checkForSubtitle() throws Exception {
        when(repo.getItems()).thenReturn(Single.just(MANY_ITEMS));

        presenter.getData();

        ArgumentCaptor<CounterListsModel> argument = ArgumentCaptor.forClass(CounterListsModel.class);
        verify(view).onDataReceived(argument.capture());
        List<String> abcdList = new ArrayList<>();
        abcdList.add("AAA");
        abcdList.add("BBB");
        abcdList.add("CCC");
        abcdList.add("DDD");
        assertEquals(argument.getValue().getItems().get(0).getSubtitle(), abcdList);

    }

    @Test public void getData_checkForFields() throws Exception {
        when(repo.getItems()).thenReturn(Single.just(MANY_ITEMS));

        presenter.getData();

        ArgumentCaptor<CounterListsModel> argument = ArgumentCaptor.forClass(CounterListsModel.class);
        verify(view).onDataReceived(argument.capture());

        assertEquals(argument.getValue().getItems().get(0).getTitle(), "Super Test Name");
        assertEquals(argument.getValue().getItems().get(0).getBackgroundColor(), 100);
        assertEquals(argument.getValue().getItems().get(0).getCardBackgroundColor(), 200);
        assertEquals(argument.getValue().getItems().get(0).getCardForegroundColor(), 300);
        assertEquals(argument.getValue().getItems().get(0).getId(), 20);


    }

    @After public void tearDown() throws Exception {
        RxJavaPlugins.reset();

    }

    @NonNull private List<Long> getIdList() {
        List<Long> items = new ArrayList<>();
        items.add(3L);
        return items;
    }

    //// TODO: 2/21/2017 Test Mapping

}