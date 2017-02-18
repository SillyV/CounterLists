package sillyv.com.counterlists.screens.lists.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.database.models.ListModel;

/**
 * Created by Vasili on 1/28/2017.
 *
 */
class CounterListAdapter extends RecyclerView.Adapter<CounterListAdapter.ListHolder> {

    private final List<ListModel> items;

    CounterListAdapter(List<ListModel> counterLists) {
        items = counterLists;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_counter_list, parent, false);
        return new ListHolder(v);

    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class ListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view_items)
        TextView items;
        @BindView(R.id.text_view_title)
        TextView title;

        ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(ListModel listModel) {
            items.setText(listModel.getCounterItemsString());
            title.setText(listModel.getName());
        }
    }


}
