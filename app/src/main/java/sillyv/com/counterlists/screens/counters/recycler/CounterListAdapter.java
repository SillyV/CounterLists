package sillyv.com.counterlists.screens.counters.recycler;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.events.AddFragmentEvent;
import sillyv.com.counterlists.events.MenuChangedEvent;
import sillyv.com.counterlists.events.NotifyValueChangedEvent;
import sillyv.com.counterlists.events.ToolbarTitleChangedEvent;
import sillyv.com.counterlists.screens.fullscreen.FullScreenCounterFragment;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 */

class CounterListAdapter
        extends RecyclerView.Adapter<CounterListAdapter.CounterHolder> {


    private final List<CounterListModel.CounterModel> regularItems;
    private final List<CounterListModel.CounterModel> selectionItems;
    private boolean selectionMode;
    private List<CounterListModel.CounterModel> currentList;

    public CounterListAdapter(List<CounterListModel.CounterModel> counterModels) {
        this.regularItems = counterModels;
        this.selectionItems = new ArrayList<>();
        for (CounterListModel.CounterModel regularItem : regularItems) {
            selectionItems.add(new CounterListModel.CounterModel(regularItem.getId(),
                    regularItem.getParentId(),
                    regularItem.getName(),
                    regularItem.getAmount(),
                    regularItem.getIncrement(),
                    regularItem.getDecrement(),
                    regularItem.getCustomOrder(),
                    regularItem.isClickSound(),
                    regularItem.isVibrate(),
                    regularItem.isSpeakName(),
                    regularItem.isSpeakValue(),
                    Color.WHITE,
                    Color.BLACK));
        }
        this.currentList = regularItems;

    }

    public boolean isSelectionMode() {
        return selectionMode;
    }

    @Override public CounterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_counter, parent, false);
        return new CounterListAdapter.CounterHolder(v);
    }

    @Override public void onBindViewHolder(CounterHolder holder, int position) {
        holder.setData(currentList.get(position));
    }

    @Override public int getItemCount() {
        return currentList.size();
    }

    public int selectedCount() {
        int i = 0;
        for (CounterListModel.CounterModel selectionItem : selectionItems) {
            if (selectionItem.isSelected()) {
                i++;
            }
        }
        return i;
    }

    public List<Long> getAllIDs() {
        List<Long> response = new ArrayList<>();
        for (CounterListModel.CounterModel counterModel : currentList) {
            response.add(counterModel.getId());
        }
        return response;
    }


    List<Long> getSelectedItems() {
        List<Long> model = new ArrayList<>();
        for (CounterListModel.CounterModel selectionItem : selectionItems) {
            if (selectionItem.isSelected()) {
                model.add(selectionItem.getId());
            }
        }
        return model;
    }


    String getFirstSelectedList() {

        for (CounterListModel.CounterModel selectionItem : selectionItems) {
            if (selectionItem.isSelected()) {
                return selectionItem.getName();
            }
        }
        return "";

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
                updateValue(getAdapterPosition());

            });

            minusButton.setOnClickListener(view -> {
                value -= decrement;
                updateValue(getAdapterPosition());

            });
        }

        private void setValueText() {
            String text = value + "";
            counter.setText(text);

        }

        private void updateValue(int adapterPosition) {
            setValueText();
            regularItems.get(adapterPosition).setAmount(value);
            selectionItems.get(adapterPosition).setAmount(value);
            EventBus.getDefault().post(new NotifyValueChangedEvent(id, value, parentId));

        }



        @OnLongClick(R.id.card_view) boolean onLongClick() {
            currentList = selectionItems;
            selectionMode = true;
            setItemSelectedOrNot();
            return true;
        }

        private void setItemSelectedOrNot() {
            CounterListModel.CounterModel counterModel = selectionItems.get(getAdapterPosition());
            counterModel.setSelected(!counterModel.isSelected());
            if (!counterModel.isSelected() && selectedCount() == 0) {
                selectionMode = false;
                currentList = regularItems;
            }
            EventBus.getDefault().post(new MenuChangedEvent());
            notifyDataSetChanged();
            EventBus.getDefault().post(new ToolbarTitleChangedEvent());
        }

        @OnClick(R.id.card_view) void onClick() {
            if (selectionMode) {
                setItemSelectedOrNot();
                return;
            }
            EventBus.getDefault().post(new AddFragmentEvent(FullScreenCounterFragment.newInstance(currentList.get(getAdapterPosition()).getId())));
        }


    }


}
