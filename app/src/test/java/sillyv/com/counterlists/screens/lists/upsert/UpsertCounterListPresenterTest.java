package sillyv.com.counterlists.screens.lists.upsert;

import android.content.Context;
import android.support.annotation.NonNull;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.List;

import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.ListModel;

/**
 * Created by Vasili on 2/19/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UpsertCounterListPresenterTest {

    @Mock
    Context mMockContext;


    @Test
    public void successfullyPassNewListToView() throws Exception {
        //given
        UpsertCounterListContract.UpsertCounterListView view = new MockView();
        RealmRepository<ListModel> repo = new MockListsRepo(4);
        //when
        UpsertCounterListContract.UpsertCounterListPresenter presenter = new UpsertCounterListPresenter(view, repo);
        presenter.loadData(mMockContext,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), new UpsertCounterListModel.Identifier(0L));
        //then
        Assert.assertEquals(true, ((MockView) view).onDataReceivedCalled);
    }

    @Test
    public void successfullyPassExistingListToView() throws Exception {
        //given
        UpsertCounterListContract.UpsertCounterListView view = new MockView();
        RealmRepository<ListModel> repo = new MockListsRepo(4);
        //when
        UpsertCounterListContract.UpsertCounterListPresenter presenter = new UpsertCounterListPresenter(view, repo);
        presenter.loadData(mMockContext,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), new UpsertCounterListModel.Identifier(1L));
        //then
        Assert.assertEquals(true, ((MockView) view).onDataReceivedCalled);
    }

    @Test
    public void unSuccessfullyPassExistingListToView() throws Exception {
        //given
        UpsertCounterListContract.UpsertCounterListView view = new MockView();
        RealmRepository<ListModel> repo = new MockListsRepo(0);
        //when
        UpsertCounterListContract.UpsertCounterListPresenter presenter = new UpsertCounterListPresenter(view, repo);
        presenter.loadData(mMockContext,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), new UpsertCounterListModel.Identifier(1L));
        //then
        Assert.assertEquals(true, ((MockView) view).onDataErrorCalled);
    }

    @Test
    public void addNewItem() throws Exception {
        //given
        UpsertCounterListContract.UpsertCounterListView view = new MockView();
        RealmRepository<ListModel> repo = new MockListsRepo(5);
        //when
        UpsertCounterListContract.UpsertCounterListPresenter presenter = new UpsertCounterListPresenter(view, repo);
        presenter.saveData(getModel(), new UpsertCounterListModel.Identifier(0));
        //then
        Assert.assertEquals(true, ((MockListsRepo) repo).passed);


    }

    @Test
    public void updateExistingItem() throws Exception {
        //given
        UpsertCounterListContract.UpsertCounterListView view = new MockView();
        RealmRepository<ListModel> repo = new MockListsRepo(5);
        //when
        UpsertCounterListContract.UpsertCounterListPresenter presenter = new UpsertCounterListPresenter(view, repo);
        presenter.saveData(getModel(), new UpsertCounterListModel.Identifier(1));
        //then
        Assert.assertEquals(true, ((MockListsRepo) repo).passed);

    }

    @Test
    public void updateNonExistingItem() throws Exception {
        //given
        UpsertCounterListContract.UpsertCounterListView view = new MockView();
        RealmRepository<ListModel> repo = new MockListsRepo(0);
        //when
        UpsertCounterListContract.UpsertCounterListPresenter presenter = new UpsertCounterListPresenter(view, repo);
        presenter.saveData(getModel(), new UpsertCounterListModel.Identifier(1));
        //then
        Assert.assertEquals(true, ((MockListsRepo) repo).passed);
    }

    @NonNull
    private UpsertCounterListModel.CounterListSettings getModel() {
        return new UpsertCounterListModel.CounterListSettings("test1", "note1", "0", "1", "1", 1, 1, 1, true, true, true, true, true, true);
    }

    private class MockView implements UpsertCounterListContract.UpsertCounterListView {
        boolean onDataReceivedCalled;
        boolean onDataErrorCalled;

        @Override
        public void onDataReceived(UpsertCounterListModel.CounterListSettings model) {
            onDataReceivedCalled = true;
        }

        @Override
        public void onDataError() {
            onDataErrorCalled = true;
        }
    }

    private class MockListsRepo implements RealmRepository<ListModel> {

        private int items;

        public MockListsRepo(int items) {

            this.items = items;
        }

        public boolean passed;

        @Override
        public List<ListModel> getItems() {
            return null;
        }

        @Override
        public ListModel getItem(long id) {
            if (id > items) {
                return null;
            }
            return new ListModel(id, 0, 0, 0, 0, 0, 0, "", true, true, true, true, true, true, "");
        }

        @Override
        public void addNewList(ListModel dbModel) {
            passed = true;
        }

        @Override
        public void updateList(ListModel dbModel) {
            if (dbModel.getId() > items)
                passed = false;
            passed = true;
        }

        @Override
        public void deleteItem(Long aLong) {

        }
    }
}