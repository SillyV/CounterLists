package sillyv.com.counterlists.screens.lists.recycler;

import android.content.DialogInterface;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sillyv.com.counterlists.CLFragment;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.events.AddFragmentEvent;
import sillyv.com.counterlists.screens.lists.upsert.UpsertCounterListFragment;

public class CountersListsFragment extends CLFragment implements CounterListsContract.CounterListsView {


    private CounterListsAdapter adapter;
    private CounterListsPresenter presenter;

    @OnClick(R.id.fab)
    public void onFabClick() {
        EventBus.getDefault().post(new AddFragmentEvent(UpsertCounterListFragment.newInstance(4)));
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
        presenter = new CounterListsPresenter();
        presenter.getData(getContext(), this);
        setTitle("Counter List");
        return view;
    }

    private void initRecyclerView() {
    }

    @Override
    public void setTitle() {
        if (adapter.isDeleteMode()) {
            setTitle("Delete " + adapter.selectedCount() + " lists?");
        } else {
            setTitle("Counter Lists");
        }
    }

    @Override
    public void onDataReceived(CounterListsModel model) {
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layout);
        adapter = new CounterListsAdapter(model.getItems());
        recyclerView.setAdapter(adapter);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem delete = menu.findItem(R.id.delete);
        if (adapter != null) {
            delete.setVisible(adapter.isDeleteMode());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
            case R.id.about:
                Toast.makeText(getContext(), "Not yet implemented", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete:
                createAndShowAlertDialog();
        }

        return false;
//        return super.onOptionsItemSelected(item);


    }

    private String getDeleteMessage() {
        String startOfString = getString(R.string.are_you_sure_you_want_to_delete) + " ";
        if (adapter.selectedCount() == 1) {
            return startOfString + getString(R.string.the_list) + adapter.getFirstSelectedList() + "'";
        } else {
            return startOfString + adapter.selectedCount() + " " + getString(R.string.lists);
        }
    }


    private void createAndShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getDeleteMessage());
        builder.setMessage(R.string.consider);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                presenter.deleteItems(getContext(), CountersListsFragment.this, adapter.getItemsToDelete());
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
