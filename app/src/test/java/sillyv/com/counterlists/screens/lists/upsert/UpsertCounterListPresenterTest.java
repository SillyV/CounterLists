package sillyv.com.counterlists.screens.lists.upsert;

import android.content.Context;
import android.support.annotation.NonNull;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.dbitems.CounterList;
import sillyv.com.counterlists.database.models.CounterModel;
import sillyv.com.counterlists.database.models.ListModel;

import static org.junit.Assert.*;

/**
 * Created by vasil on 2/19/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UpsertCounterListPresenterTest {

    @Mock
    Context mMockContext;

    @Test
    public void aShouldPass() {
        Assert.assertEquals(1, 1);
    }

    @Test
    public void successfullyPassNewListToView() throws Exception {
        //given
        UpsertCounterListContract.UpsertCounterListView view = new MockView();
        RealmRepository<ListModel> repo = new MockListsRepo();
        //when
        UpsertCounterListContract.UpsertCounterListPresenter presenter = new UpsertCounterListPresenter(view, repo);
        presenter.loadData(mMockContext, new UpsertCounterListModel.Identifier(0L));
        //then
        Assert.assertEquals(true, ((MockView) view).passed);
    }

    @Test
    public void successfullyPassExistingListToView() throws Exception {
        //given
        UpsertCounterListContract.UpsertCounterListView view = new MockView();
        RealmRepository<ListModel> repo = new MockListsRepo();
        //when
        UpsertCounterListContract.UpsertCounterListPresenter presenter = new UpsertCounterListPresenter(view, repo);
        presenter.loadData(mMockContext, new UpsertCounterListModel.Identifier(1L));
        //then
        Assert.assertEquals(true, ((MockView) view).passed);
    }


    @Test
    public void addNewItem() throws Exception {
        //given
        UpsertCounterListContract.UpsertCounterListView view = new MockView();
        RealmRepository<ListModel> repo = new MockListsRepo();
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
        RealmRepository<ListModel> repo = new MockListsRepo();
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
        boolean passed;

        @Override
        public void onDataReceived(UpsertCounterListModel.CounterListSettings model) {
            passed = true;
        }
    }


    private class MockListsRepo implements RealmRepository<ListModel> {
        public boolean passed;

        @Override
        public List<ListModel> getItems() {
            return null;
        }

        @Override
        public ListModel getItem(long id) {
            return new ListModel(id, 0, 0, 0, 0, 0, 0, "", true, true, true, true, true, true, "");
        }

        @Override
        public void addNewList(ListModel dbModel) {
            passed = true;
        }

        @Override
        public void updateList(ListModel dbModel) {
            passed = true;
        }
    }
}