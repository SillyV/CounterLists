package sillyv.com.counterlists.screens.lists.recycler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.database.controllers.ListController;
import sillyv.com.counterlists.database.models.ListModel;

public class CountersListsFragment extends Fragment {


    private LinearLayoutManager layout;
    private CounterListAdapter adapter;

    @OnClick(R.id.fab)
    public void onFabClick(View view) {
        ListController.getInstance().addNewList(new ListModel(0, 1, 1, 0, 0, 0, "A", true, true,
                true, true, false, true, "list 1"));
        initRecyclerView();
        recyclerView.scrollToPosition(adapter.getItemCount() -1);

    }


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    public CountersListsFragment() {
        // Required empty public constructor
    }

    public static CountersListsFragment newInstance(String param1, String param2) {
        CountersListsFragment fragment = new CountersListsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        return view;
    }

    private void initRecyclerView() {
        layout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layout);
        adapter = new CounterListAdapter(ListController.with(this).getAllCounters());
        recyclerView.setAdapter(adapter);
    }


}
