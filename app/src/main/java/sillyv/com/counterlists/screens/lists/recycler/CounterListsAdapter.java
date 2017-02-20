package sillyv.com.counterlists.screens.lists.recycler;

import android.graphics.Color;
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
import sillyv.com.counterlists.events.ToolbarTitleChangedEvent;
import sillyv.com.counterlists.screens.lists.upsert.UpsertCounterListFragment;

/**
 * Created by Vasili.Fedotov on 1/28/2017.
 *
 */
class CounterListsAdapter extends RecyclerView.Adapter<CounterListsAdapter.ListHolder> {

    private final List<CounterListsModel.ListItem> regularItems;
    private final List<CounterListsModel.ListItem> selectionItems;
    private List<CounterListsModel.ListItem> currentList;

    private boolean deleteMode = false;

    boolean isDeleteMode() {
        return deleteMode;
    }

    CounterListsAdapter(List<CounterListsModel.ListItem> counterLists) {
        regularItems = counterLists;
        selectionItems = new ArrayList<>();
        for (CounterListsModel.ListItem item : regularItems) {
            selectionItems.add(new CounterListsModel.ListItem(item.getTitle(), item.getSubtitle(), Color.WHITE, Color.BLACK, Color.BLACK, item.getId()));
        }
        currentList = regularItems;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_counter_list, parent, false);
        return new ListHolder(v);

    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        holder.setData(currentList.get(position));
    }

    @Override
    public int getItemCount() {
        return currentList.size();
    }

    String getFirstSelectedList() {
        for (CounterListsModel.ListItem selectionItem : selectionItems) {
            if (selectionItem.isSelected()) {
                return selectionItem.getTitle();
            }
        }
        return "";
    }

    CounterListsModel.IDList getItemsToDelete() {
        CounterListsModel.IDList model = new CounterListsModel.IDList();
        for (CounterListsModel.ListItem selectionItem : selectionItems) {
            if (selectionItem.isSelected()) {
                model.getItems().add(selectionItem.getId());
            }
        }
        return model;
    }


    class ListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view_items)
        TextView items;
        @BindView(R.id.text_view_title)
        TextView title;
        @BindView(R.id.card_view)
        View cardView;

        @BindView(R.id.checked_image_view)
        View checkedImageView;

        @OnClick(R.id.card_view)
        void onClick() {
            if (deleteMode) {
                setItemSelectedOrNot();
                return;
            }
            EventBus.getDefault().post(new AddFragmentEvent(UpsertCounterListFragment.newInstance(currentList.get(getAdapterPosition()).getId())));

        }

        @SuppressWarnings("SameReturnValue")
        @OnLongClick(R.id.card_view)
        boolean onLongClick() {
            currentList = selectionItems;
            deleteMode = true;
            setItemSelectedOrNot();
            EventBus.getDefault().post(new MenuChangedEvent());
            return true;
        }

        private void setItemSelectedOrNot() {
            CounterListsModel.ListItem me = selectionItems.get(getAdapterPosition());
            me.setSelected(!me.isSelected());
            if (!me.isSelected() && selectedCount() == 0) {
                deleteMode = false;
                currentList = regularItems;
                EventBus.getDefault().post(new MenuChangedEvent());
            }
            notifyDataSetChanged();
            EventBus.getDefault().post(new ToolbarTitleChangedEvent());
        }


        ListHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressWarnings("ResourceType")
        public void setData(CounterListsModel.ListItem listModel) {
            items.setText(listModel.getSubtitle());
            title.setText(listModel.getTitle());
            itemView.setBackgroundColor(listModel.getBackgroundColor());
            items.setTextColor(listModel.getCardForegroundColor());
            title.setTextColor(listModel.getCardForegroundColor());
            title.setShadowLayer(0.11f, -2, 2, listModel.getCardBackgroundColor());
            items.setBackgroundColor(listModel.getCardBackgroundColor());
            checkedImageView.setVisibility(listModel.getVisibility());
        }
    }

    int selectedCount() {
        int i = 0;
        for (CounterListsModel.ListItem selectionItem : selectionItems) {
            if (selectionItem.isSelected()) {
                i++;
            }
        }
        return i;
    }


}
