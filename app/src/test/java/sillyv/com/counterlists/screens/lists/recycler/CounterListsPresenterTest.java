package sillyv.com.counterlists.screens.lists.recycler;

import android.support.annotation.NonNull;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.ListModel;
import sillyv.com.counterlists.screens.lists.upsert.UpsertCounterListContract;
import sillyv.com.counterlists.screens.lists.upsert.UpsertCounterListModel;
import sillyv.com.counterlists.screens.lists.upsert.UpsertCounterListPresenterTest;

import static org.junit.Assert.*;

/**
 * Created by Vasili on 2/19/2017.
 */
public class CounterListsPresenterTest {

    @Test
    public void getDataFromExistingItems() throws Exception {
//given
        CounterListsContract.CounterListsView view = new MockView();
        RealmRepository<ListModel> repo = new MockRepo(5);

        //when
        CounterListsPresenter presenter = new CounterListsPresenter(view, repo);
        presenter.getData();

        //then
        Assert.assertEquals(true, ((MockView) view).onDataReceivedCalled);
        Assert.assertEquals(false, ((MockView) view).onErrorResponseCalled);
    }

    @Test
    public void getDataFromEmptyRepo() throws Exception {
//given
        CounterListsContract.CounterListsView view = new MockView();
        RealmRepository<ListModel> repo = new MockRepo(0);

        //when
        CounterListsPresenter presenter = new CounterListsPresenter(view, repo);
        presenter.getData();

        //then
        Assert.assertEquals(true, ((MockView) view).onDataReceivedCalled);
        Assert.assertEquals(false, ((MockView) view).onErrorResponseCalled);

    }

    @Test
    public void errorGettingItems() throws Exception {
//given
        CounterListsContract.CounterListsView view = new MockView();
        RealmRepository<ListModel> repo = new MockRepo(-1);

        //when
        CounterListsPresenter presenter = new CounterListsPresenter(view, repo);
        presenter.getData();

        //then
        Assert.assertEquals(true, ((MockView) view).onErrorResponseCalled);
        Assert.assertEquals(false, ((MockView) view).onDataReceivedCalled);
    }

    @Test
    public void deleteItems() throws Exception {

        CounterListsContract.CounterListsView view = new MockView();
        RealmRepository<ListModel> repo = new MockRepo(-1);

        //when
        CounterListsPresenter presenter = new CounterListsPresenter(view, repo);
        presenter.deleteItems(getIdList());

        Assert.assertEquals(true, ((MockRepo) repo).deleteItemsCalled);
    }

    @NonNull
    private CounterListsModel.IDList getIdList() {
        List<Long> items = new ArrayList<>();
        items.add(3L);
        return new CounterListsModel.IDList(items);
    }

    private class MockView implements CounterListsContract.CounterListsView {
        boolean onDataReceivedCalled;
        boolean onErrorResponseCalled;

        @Override
        public void onDataReceived(CounterListsModel model) {
            onDataReceivedCalled = true;
        }

        @Override
        public void onErrorResponse() {
            onErrorResponseCalled = true;
        }
    }


    private class MockRepo implements RealmRepository<ListModel> {

        private int items;
        public boolean deleteItemsCalled;

        public MockRepo(int items) {
            this.items = items;
        }

        @Override
        public List<ListModel> getItems() {
            if (items == -1)
                return null;
            List<ListModel> response = new ArrayList<>();
            for (int i = 0; i < items; i++) {
                ListModel e = new ListModel(i, 0, 0, 0, 0, 0, 0, "", true, true, true, true, true, true, "");
                e.setCounterNames(Arrays.asList("AAA", "BBB", "CCC", "DDD"));
                response.add(e);
            }
            return response;
        }

        @Override
        public ListModel getItem(long id) {
            return null;
        }

        @Override
        public void addNewList(ListModel dbModel) {

        }

        @Override
        public void updateList(ListModel dbModel) {

        }

        @Override
        public void deleteItem(Long aLong) {
            deleteItemsCalled = true;
        }
    }


}