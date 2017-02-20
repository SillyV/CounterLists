package sillyv.com.counterlists.screens.lists.recycler;

import android.support.annotation.NonNull;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.ListModel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CounterListsPresenterTest {
    private static final int ITEM_WITH_HEFTY_SUBTITLE = 3;

    @Rule
    MockitoRule rule = MockitoJUnit.rule();

    @Mock
    CounterListsContract.CounterListsView view;

    @Mock
    RealmRepository<ListModel> repo;

    private CounterListsPresenter presenter;
    private final List<ListModel> MANY_ITEMS = getListModels(ITEM_WITH_HEFTY_SUBTITLE);

    @Before
    public void setUp() throws Exception {
        presenter = new CounterListsPresenter(view, repo);
    }


    @Test
    public void getDataFromPopulatedRepo() throws Exception {
        when(repo.getItems()).thenReturn(MANY_ITEMS);

        presenter.getData();

        ArgumentCaptor<CounterListsModel> argument = ArgumentCaptor.forClass(CounterListsModel.class);

        verify(view).onDataReceived(Mockito.any(CounterListsModel.class));
        verify(view, Mockito.times(0)).onErrorResponse();
        verify(view).onDataReceived(argument.capture());
        assertEquals(argument.getValue().getItems().size(), MANY_ITEMS.size());

    }

    @Test
    public void getDataFromEmptyRepo() throws Exception {
        when(repo.getItems()).thenReturn(Collections.<ListModel>emptyList());

        presenter.getData();

        ArgumentCaptor<CounterListsModel> argument = ArgumentCaptor.forClass(CounterListsModel.class);
        verify(view).onDataReceived(Mockito.any(CounterListsModel.class));
        verify(view, Mockito.times(0)).onErrorResponse();
        verify(view).onDataReceived(argument.capture());
        assertEquals(argument.getValue().getItems().size(), 0);
    }

    @Test
    public void errorGettingItems() throws Exception {

        when(repo.getItems()).thenReturn(null);

        presenter.getData();

        verify(view, times(0)).onDataReceived(Mockito.any(CounterListsModel.class));
        verify(view).onErrorResponse();
    }

    @Test
    public void deleteItems() throws Exception {

        presenter.deleteItems(getIdList());

        verify(repo, times(getIdList().items.size())).deleteItem(anyLong());
    }

    /**
     * @throws Exception Tests for private methods getSubtitle and replaceLastComma
     */
    @Test
    public void getSubtitle() throws Exception {
        when(repo.getItems()).thenReturn(MANY_ITEMS);

        presenter.getData();

        ArgumentCaptor<CounterListsModel> argument = ArgumentCaptor.forClass(CounterListsModel.class);
        verify(view).onDataReceived(argument.capture());

        assertEquals(argument.getValue().getItems().get(0).getSubtitle(), "AAA, BBB, CCC, DDD.");
        assertEquals(argument.getValue().getItems().get(1).getSubtitle(), "AAA.");
        assertEquals(argument.getValue().getItems().get(2).getSubtitle(), "");

    }


    private List<ListModel> getListModels(int itemType) {
        List<ListModel> response = new ArrayList<>();

        int items = 4;
        if (itemType == ITEM_WITH_HEFTY_SUBTITLE) {
            ListModel a = new ListModel(0, 0, 0, 0, 0, 0, 0, "", true, true, true, true, true, true, "");
            a.setCounterNames(Arrays.asList("AAA", "BBB", "CCC", "DDD"));
            response.add(a);

            ListModel b = new ListModel(1, 0, 0, 0, 0, 0, 0, "", true, true, true, true, true, true, "");
            b.setCounterNames(Collections.singletonList("AAA"));
            response.add(b);


            ListModel c = new ListModel(2, 0, 0, 0, 0, 0, 0, "", true, true, true, true, true, true, "");
            c.setCounterNames(Collections.<String>emptyList());
            response.add(c);


            items += 3;
        }

        for (int i = itemType; i < items; i++) {
            ListModel e = new ListModel(i, 0, 0, 0, 0, 0, 0, "", true, true, true, true, true, true, "");
            e.setCounterNames(Arrays.asList("AAA", "BBB", "CCC", "DDD"));
            response.add(e);
        }

        return response;
    }

    @NonNull
    private CounterListsModel.IDList getIdList() {
        List<Long> items = new ArrayList<>();
        items.add(3L);
        return new CounterListsModel.IDList(items);
    }


//    private class MockRepo implements RealmRepository<ListModel> {
//
//        private int items;
//        public boolean deleteItemsCalled;
//        private int itemType;
//
//        public MockRepo(int items) {
//            this.items = items;
//        }
//
//
//        public MockRepo(int items, int itemType) {
//            this.itemType = itemType;
//            this.items = items;
//        }
//
//        @Override
//        public List<ListModel> getItems() {
//            if (items == -1)
//                return null;
//            return getListModels();
//        }
//
//
//
//        @Override
//        public ListModel getItem(long id) {
//            return null;
//        }
//
//        @Override
//        public void addNewList(ListModel dbModel) {
//
//        }
//
//        @Override
//        public void updateList(ListModel dbModel) {
//
//        }
//
//        @Override
//        public void deleteItem(Long aLong) {
//            deleteItemsCalled = true;
//        }
//    }


}