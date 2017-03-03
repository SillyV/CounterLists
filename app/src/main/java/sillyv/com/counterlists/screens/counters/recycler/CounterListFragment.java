package sillyv.com.counterlists.screens.counters.recycler;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sillyv.com.counterlists.CLFragment;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.baseline.BaseListView;
import sillyv.com.counterlists.database.controllers.CounterController;
import sillyv.com.counterlists.database.controllers.ListController;
import sillyv.com.counterlists.events.AddFragmentEvent;
import sillyv.com.counterlists.events.NotifyValueChangedEvent;
import sillyv.com.counterlists.screens.counters.upsert.UpsertCounterFragment;

public class CounterListFragment
        extends CLFragment
        implements BaseListView<CounterListModel> {

    public static final String COUNTER_LIST_ID = "COUNTER_LIST_ID";
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    private CounterListPresenter presenter;
    private long id;
    private CounterListAdapter adapter;


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
        presenter = new CounterListPresenter(this, CounterController.getInstance(), ListController.getInstance());
        presenter.getData(id);

        return view;
    }

    @Override public void setTitle() {

    }
    @Override public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override public void onStop() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }
    @Override public void onDataReceived(CounterListModel counterListModel) {
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layout);
        adapter = new CounterListAdapter(counterListModel.getCounterModels());
        recyclerView.setAdapter(adapter);
        recyclerView.setBackgroundColor(counterListModel.getBackgroundColor());
    }

    @Override public void onGetDataErrorResponse() {

    }

    @Override public void onDeleteBooksErrorResponse() {

    }

    @Override public void onSaveDataErrorResponse() {

    }

    @Override public void onSaveDataSuccess() {

    }

    @Subscribe public void valueChanged(NotifyValueChangedEvent event) {
        presenter.saveInteraction(event.getId(), event.getParentId(), event.getValue());









    }

    @OnClick(R.id.fab) void onClick() {
        EventBus.getDefault().post(new AddFragmentEvent(UpsertCounterFragment.newInstance(id)));
    }
}
