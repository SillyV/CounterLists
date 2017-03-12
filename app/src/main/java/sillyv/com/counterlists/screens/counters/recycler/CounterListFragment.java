package sillyv.com.counterlists.screens.counters.recycler;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.baseline.BasePresenter;
import sillyv.com.counterlists.baseline.CLFragment;
import sillyv.com.counterlists.database.controllers.CounterController;
import sillyv.com.counterlists.database.controllers.ListController;
import sillyv.com.counterlists.events.AddFragmentEvent;
import sillyv.com.counterlists.events.NotifyValueChangedEvent;
import sillyv.com.counterlists.screens.counters.upsert.UpsertCounterFragment;
import sillyv.com.counterlists.screens.lists.upsert.UpsertCounterListFragment;
import sillyv.com.counterlists.tools.CLStringUtils;

public class CounterListFragment
        extends CLFragment
        implements CounterListContract.CounterListView<CounterListModel> {

    public static final String COUNTER_LIST_ID = "COUNTER_LIST_ID";
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    private CounterListPresenter presenter;
    private long id;
    private CounterListAdapter adapter;
    private boolean interaction = false;


    public CounterListFragment() {
        // Required empty public constructor
    }

    public static CounterListFragment newInstance(long id) {
        CounterListFragment fragment = new CounterListFragment();
        Bundle args = new Bundle();
        args.putLong(COUNTER_LIST_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong(COUNTER_LIST_ID);
        }
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_counter_list, container, false);
        ButterKnife.bind(this, view);
        presenter = new CounterListPresenter(this, CounterController.getInstance(), ListController.getInstance(), AndroidSchedulers.mainThread());
        presenter.getData(id);
        invalidateOptionsMenu();
        return view;
    }

    @Override public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.counter_list_menu, menu);
        MenuItem delete = menu.findItem(R.id.delete);
        delete.setVisible(adapter != null && adapter.isSelectionMode());
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (interaction) {
            return false;
        }
        switch (item.getItemId()) {
            case R.id.edit:
                startEditing();
                return true;
            case R.id.delete:
                createAndShowAlertDialogForDeleting();
                return true;
            case R.id.restart:
                resetItems();
                return true;

        }
        return false;
    }

    private void startEditing() {
        if (adapter.selectedCount() == 1) {
            openSelectedCounterForEditing();
        } else {
            openCurrentListForEditing();
        }
    }

    private void createAndShowAlertDialogForDeleting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(CLStringUtils.getDeleteMessage(getContext(), adapter.getFirstSelectedList(), adapter.selectedCount()));
        builder.setMessage(R.string.consider);
        builder.setPositiveButton(android.R.string.yes, (dialog, id) -> {
            interaction = true;
            presenter.deleteCounters(adapter.getSelectedItems(), this.id);
            setTitle();
            invalidateOptionsMenu();
            dialog.dismiss();
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void resetItems() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(CLStringUtils.getResetMessage(getContext(),
                adapter.getFirstSelectedList(),
                adapter.selectedCount(),
                adapter.isSelectionMode()));

        builder.setMessage(R.string.consider);
        builder.setPositiveButton(android.R.string.yes, (dialog, id) -> {
            interaction = true;
            if (adapter.isSelectionMode()) {
                presenter.resetItems(adapter.getSelectedItems(), this.id);
            } else {
                presenter.resetItems(adapter.getAllIDs(), this.id);
            }
            setTitle();
            invalidateOptionsMenu();
            dialog.dismiss();
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openSelectedCounterForEditing() {
        List<Long> list = adapter.getSelectedItems();
        if (list.size() == 1) {
            EventBus.getDefault().post(new AddFragmentEvent(UpsertCounterFragment.newInstance(list.get(0), id)));
        }
    }

    private void openCurrentListForEditing() {
        EventBus.getDefault().post(new AddFragmentEvent(UpsertCounterListFragment.newInstance(id)));
    }


    @Override public void onStop() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    @Override public boolean volumePressed(int keyCode, KeyEvent event) {
        return false;
    }

    @Override protected List<BasePresenter> getPresenters() {
        return Collections.singletonList(presenter);
    }

    @Override public void setTitle() {

    }

    @Override public void onSaveInteractionSuccess() {
        interaction = false;
    }

    @Override public void onSaveInteractionError() {

    }

    @Override public void onSaveInteractionFailure() {
        interaction = false;
    }

    @Override public void onDeleteItemsErrorResponse() {
        interaction = false;
    }

    @Override public void onSaveDataErrorResponse() {
        interaction = false;
    }

    @Override public void onSaveDataSuccess() {
        interaction = false;
    }

    @Override public void onDataReceived(CounterListModel counterListModel) {
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layout);
        adapter = new CounterListAdapter(counterListModel.getCounterModels());
        recyclerView.setAdapter(adapter);
        recyclerView.setBackgroundColor(counterListModel.getBackgroundColor());
        interaction = false;
    }

    @Override public void onGetDataErrorResponse() {
        interaction = false;
    }

    @Subscribe public void valueChanged(NotifyValueChangedEvent event) {
        interaction = true;
        presenter.saveInteraction(event.getId(), event.getParentId(), event.getValue());


    }

    @Override public void onDeleteItemsSuccess() {
        interaction = false;
    }

    @Override public void onResetItemsSuccess() {
        interaction = false;
    }

    @Override public void onResetItemError() {
        interaction = false;
    }

    @OnClick(R.id.fab) void onClick() {
        EventBus.getDefault().post(new AddFragmentEvent(UpsertCounterFragment.newInstance(id)));
    }
}
