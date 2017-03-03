package sillyv.com.counterlists.screens.lists.recycler;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

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
import sillyv.com.counterlists.screens.counters.recycler.CounterListFragment;

/**
 * Created by Vasili.Fedotov on 1/28/2017.
 */
class CounterListsAdapter
        extends RecyclerView.Adapter<CounterListsAdapter.ListHolder> {

    private final List<CounterListsModel.ListItem> regularItems;
    private final List<CounterListsModel.ListItem> selectionItems;
    private List<CounterListsModel.ListItem> currentList;

    private boolean deleteMode = false;
    private DisplayMetrics metrics;

    CounterListsAdapter(Context context, List<CounterListsModel.ListItem> counterLists) {
        metrics = context.getResources().getDisplayMetrics();
        regularItems = counterLists;
        selectionItems = new ArrayList<>();
        for (CounterListsModel.ListItem item : regularItems) {
            selectionItems.add(new CounterListsModel.ListItem(item.getTitle(),
                    item.getSubtitle(),
                    Color.WHITE,
                    Color.BLACK,
                    Color.BLACK,
                    item.getId()));
        }
        currentList = regularItems;
    }

    @Override public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_counter_list_tesst, parent, false);
        return new ListHolder(v);

    }

    @Override public void onBindViewHolder(ListHolder holder, int position) {
        holder.setData(currentList.get(position));
    }

    @Override public int getItemCount() {
        return currentList.size();
    }

    boolean isDeleteMode() {
        return deleteMode;
    }

    String getFirstSelectedList() {
        for (CounterListsModel.ListItem selectionItem : selectionItems) {
            if (selectionItem.isSelected()) {
                return selectionItem.getTitle();
            }
        }
        return "";
    }

    List<Long> getSelectedItems() {
        List<Long> model = new ArrayList<>();
        for (CounterListsModel.ListItem selectionItem : selectionItems) {
            if (selectionItem.isSelected()) {
                model.add(selectionItem.getId());
            }
        }
        return model;
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

    class ListHolder
            extends RecyclerView.ViewHolder {
        //        @BindView(R.id.text_view_items)
        //        TextView items;

        @BindView(R.id.text_view_title) TextView title;
        @BindView(R.id.card_view) View cardView;

        @BindView(R.id.flex_box) FlexboxLayout flexboxLayout;

        @BindView(R.id.checked_image_view) View checkedImageView;

        ListHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressWarnings("ResourceType") public void setData(CounterListsModel.ListItem listModel) {
            title.setText(listModel.getTitle());
            itemView.setBackgroundColor(listModel.getBackgroundColor());
            title.setTextColor(listModel.getCardForegroundColor());
            title.setShadowLayer(0.11f, -2, 2, listModel.getCardBackgroundColor());
            checkedImageView.setVisibility(listModel.getVisibility());
            flexboxLayout.removeAllViews();
            for (String s : listModel.getSubtitle()) {
                createCounterTextView(listModel, s);
            }
            flexboxLayout.requestLayout();
        }

        private void createCounterTextView(CounterListsModel.ListItem listModel, String s) {
            FlexboxLayout.MarginLayoutParams flexboxLayoutLayoutParams = getMarginLayoutParams();
            TextView tv = new TextView(flexboxLayout.getContext());
            tv.setPadding(convertPixelsToDp(100, metrics),
                    convertPixelsToDp(50, metrics),
                    convertPixelsToDp(100, metrics),
                    convertPixelsToDp(50, metrics));
            tv.setTextColor(listModel.getCardForegroundColor());
            tv.setBackgroundColor(listModel.getCardBackgroundColor());
            tv.setText(s);
            flexboxLayout.addView(tv, flexboxLayoutLayoutParams);
        }

        @NonNull private FlexboxLayout.MarginLayoutParams getMarginLayoutParams() {
            FlexboxLayout.MarginLayoutParams flexboxLayoutLayoutParams = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT);
            flexboxLayoutLayoutParams.setMargins(convertPixelsToDp(50, metrics),
                    convertPixelsToDp(70, metrics),
                    convertPixelsToDp(50, metrics),
                    convertPixelsToDp(70, metrics));
            return flexboxLayoutLayoutParams;
        }

        int convertPixelsToDp(float px, DisplayMetrics metrics) {
            return (int) (px / (metrics.densityDpi / 160f));
        }


        @OnClick(R.id.card_view) void onClick() {
            if (deleteMode) {
                setItemSelectedOrNot();
                return;
            }
            EventBus.getDefault().post(new AddFragmentEvent(CounterListFragment.newInstance(currentList.get(getAdapterPosition()).getId())));

        }

        private void setItemSelectedOrNot() {
            CounterListsModel.ListItem me = selectionItems.get(getAdapterPosition());
            me.setSelected(!me.isSelected());
            if (!me.isSelected() && selectedCount() == 0) {
                deleteMode = false;
                currentList = regularItems;
            }
            EventBus.getDefault().post(new MenuChangedEvent());
            notifyDataSetChanged();
            EventBus.getDefault().post(new ToolbarTitleChangedEvent());
        }

        @SuppressWarnings("SameReturnValue") @OnLongClick(R.id.card_view) boolean onLongClick() {
            currentList = selectionItems;
            deleteMode = true;
            setItemSelectedOrNot();
            return true;
        }
    }


}
