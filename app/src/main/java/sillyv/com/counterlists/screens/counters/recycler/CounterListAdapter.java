package sillyv.com.counterlists.screens.counters.recycler;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.events.NotifyValueChangedEvent;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 */

class CounterListAdapter
        extends RecyclerView.Adapter<CounterListAdapter.CounterHolder> {


    private List<CounterListModel.CounterModel> items;

    public CounterListAdapter(List<CounterListModel.CounterModel> counterModels) {

        this.items = counterModels;
    }

    @Override public CounterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_counter, parent, false);
        return new CounterListAdapter.CounterHolder(v);
    }

    @Override public void onBindViewHolder(CounterHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override public int getItemCount() {
        return items.size();
    }

    class CounterHolder
            extends RecyclerView.ViewHolder {
        @BindView(R.id.title) TextView title;
        @BindView(R.id.counter) TextView counter;
        @BindView(R.id.plus_one) View plusOne;
        @BindView(R.id.plus_two) View plusTwo;
        @BindView(R.id.minus_one) View minusOne;
        @BindView(R.id.plus_button) View plusButton;
        @BindView(R.id.minus_button) View minusButton;
        @BindView(R.id.card_view) CardView cardView;

        private int increment;
        private int decrement;
        private int value;
        private long id;
        private long parentId;

        CounterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(CounterListModel.CounterModel counterModel) {
            title.setText(counterModel.getName());
            plusOne.setBackgroundColor(counterModel.getForegroundColor());
            plusTwo.setBackgroundColor(counterModel.getForegroundColor());
            minusOne.setBackgroundColor(counterModel.getForegroundColor());
            cardView.setCardBackgroundColor(counterModel.getBackgroundColor());
            title.setTextColor(counterModel.getForegroundColor());
            counter.setTextColor(counterModel.getForegroundColor());
            increment = counterModel.getIncrement();
            decrement = counterModel.getDecrement();
            value = counterModel.getAmount();
            setValueText();
            id = counterModel.getId();
            parentId = counterModel.getParentId();

            plusButton.setOnClickListener(view -> {
                value += increment;
                updateValue(counterModel);

            });

            minusButton.setOnClickListener(view -> {
                value -= decrement;
                updateValue(counterModel);

            });

        }

        private void setValueText() {
            String text = value + "";
            counter.setText(text);

        }

        private void updateValue(CounterListModel.CounterModel counterModel) {
            setValueText();
            counterModel.setAmount(value);
            EventBus.getDefault().post(new NotifyValueChangedEvent(id, value, parentId));
        }
    }
}
