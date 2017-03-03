package sillyv.com.counterlists.screens.lists.recycler;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import sillyv.com.counterlists.CLFragment;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.database.controllers.ListController;
import sillyv.com.counterlists.events.AddFragmentEvent;
import sillyv.com.counterlists.screens.lists.upsert.UpsertCounterListFragment;

public class CountersListsFragment
        extends CLFragment
        implements CounterListsContract.CounterListsView<CounterListsModel> {


    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    private CounterListsAdapter adapter;
    private CounterListsPresenter presenter;

    public CountersListsFragment() {
        // Required empty public constructor
    }

    @OnClick(R.id.fab) public void onFabClick() {
        EventBus.getDefault().post(new AddFragmentEvent(UpsertCounterListFragment.newInstance()));
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //noinspection StatementWithEmptyBody
        if (getArguments() != null) {
        }
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_counters_lists, container, false);
        ButterKnife.bind(this, view);
        presenter = new CounterListsPresenter(this, ListController.getInstance(), AndroidSchedulers.mainThread());
        presenter.getData();
        setTitle("Counter List");
        return view;
    }

    @Override public void onStop() {
        presenter.unsubscribe();
        super.onStop();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_list_list_menu, menu);
        MenuItem delete = menu.findItem(R.id.delete);
        MenuItem edit = menu.findItem(R.id.edit);
        if (adapter != null) {
            delete.setVisible(adapter.isDeleteMode());
            edit.setVisible(adapter.selectedCount() == 1);
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
            case R.id.about:
                Toast.makeText(getContext(), "Not yet implemented", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete:
                createAndShowAlertDialogForDeleting();
                return true;
            case R.id.edit:
                openSelectedFragmentForEditing();
                return true;
        }

        return false;
        //        return super.onOptionsItemSelected(item);


    }

    private void createAndShowAlertDialogForDeleting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getDeleteMessage());
        builder.setMessage(R.string.consider);
        builder.setPositiveButton(android.R.string.yes, (dialog, id) -> {
            presenter.deleteItems(adapter.getSelectedItems());
            setTitle();
            invalidateOptionsMenu();
            dialog.dismiss();
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openSelectedFragmentForEditing() {
        List<Long> list = adapter.getSelectedItems();
        if (list.size() == 1) {
            EventBus.getDefault().post(new AddFragmentEvent(UpsertCounterListFragment.newInstance(list.get(0))));
        }
    }

    private String getDeleteMessage() {
        String startOfString = getString(R.string.are_you_sure_you_want_to_delete) + " ";
        if (adapter.selectedCount() == 1) {
            return startOfString + getString(R.string.the_list) + adapter.getFirstSelectedList() + "'";
        } else {
            return startOfString + adapter.selectedCount() + " " + getString(R.string.lists);
        }
    }

    @Override public void setTitle() {
        if (adapter.isDeleteMode()) {
            setTitle(adapter.selectedCount() + " List selected");
        } else {
            setTitle("Counter Lists");
        }
    }

    @Override public void onDataReceived(CounterListsModel model) {
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layout);
        adapter = new CounterListsAdapter(getContext(), model.getItems());
        recyclerView.setAdapter(adapter);
    }

    @Override public void onGetDataErrorResponse() {

    }

    @Override public void onDeleteBooksErrorResponse() {

    }

    @Override public void onSaveDataErrorResponse() {

    }

    @Override public void onSaveDataSuccess() {

    }
}
