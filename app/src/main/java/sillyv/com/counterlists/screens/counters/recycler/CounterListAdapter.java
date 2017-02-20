package sillyv.com.counterlists.screens.counters.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sillyv.com.counterlists.R;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 *
 */

class CounterListAdapter extends RecyclerView.Adapter<CounterListAdapter.CounterHolder> {


    @Override
    public CounterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_counter_list, parent, false);
        return new CounterListAdapter.CounterHolder(v);
    }

    @Override
    public void onBindViewHolder(CounterHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class CounterHolder extends RecyclerView.ViewHolder {

        CounterHolder(View itemView) {
            super(itemView);
        }
    }
}
