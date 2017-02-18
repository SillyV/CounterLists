package sillyv.com.counterlists.screens.lists.recycler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sillyv.com.counterlists.CLFragment;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.database.controllers.ListController;
import sillyv.com.counterlists.database.models.ListModel;
import sillyv.com.counterlists.events.AddFragmentEvent;
import sillyv.com.counterlists.screens.lists.upsert.UpsertCounterListFragment;

public class CountersListsFragment extends CLFragment {


    @OnClick(R.id.fab)
    public void onFabClick() {
//        ListController.getInstance().addNewList(new ListModel(0, 1, 1, 0, 0, 0, "A", true, true,
//                true, true, false, true, "list 1"));
//        initRecyclerView();
//        recyclerView.scrollToPosition(adapter.getItemCount() -1);
        EventBus.getDefault().post(new AddFragmentEvent(UpsertCounterListFragment.newInstance()));
    }


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    public CountersListsFragment() {
        // Required empty public constructor
    }

    public static CountersListsFragment newInstance() {
        CountersListsFragment fragment = new CountersListsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //noinspection StatementWithEmptyBody
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_counters_lists, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        setTitle("Counter List");
        return view;
    }

    private void initRecyclerView() {
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layout);
        CounterListAdapter adapter = new CounterListAdapter(ListController.getInstance().getAllCounters());
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void setTitle() {
        setTitle("Counter Lists");
    }
}
