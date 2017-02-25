package sillyv.com.counterlists.screens.lists.recycler;

import android.graphics.Color;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vasili.Fedotov on 2/18/2017.
 */

public class CounterListsModel {

    private final List<ListItem> items;

    CounterListsModel(List<ListItem> items) {
        this.items = items;
    }

    List<ListItem> getItems() {
        return items;
    }

    public static class ListItem {
        private final String title;
        private final long id;
        private List<String> subtitle;
        private int backgroundColor;
        private int cardBackgroundColor;
        private int cardForegroundColor;
        private boolean selected = false;
        private int visibility;

        protected ListItem(String title, List<String> subtitle, int backgroundColor, int cardBackgroundColor, int cardForegroundColor, long id) {
            this.title = title;
            this.subtitle = subtitle;
            this.backgroundColor = backgroundColor;
            this.cardBackgroundColor = cardBackgroundColor;
            this.cardForegroundColor = cardForegroundColor;
            this.id = id;
            visibility = View.INVISIBLE;
        }

        public String getTitle() {
            return title;
        }

        public List<String> getSubtitle() {
            return subtitle;
        }

        public int getBackgroundColor() {
            return backgroundColor;
        }

        public int getCardBackgroundColor() {
            return cardBackgroundColor;
        }

        public int getCardForegroundColor() {
            return cardForegroundColor;
        }

        public long getId() {
            return id;
        }

        int getVisibility() {
            return visibility;
        }

        boolean isSelected() {
            return selected;
        }

        void setSelected(boolean selected) {
            this.selected = selected;
            if (selected) {
                backgroundColor = Color.BLACK;
                cardBackgroundColor = Color.BLACK;
                cardForegroundColor = Color.WHITE;
                visibility = View.VISIBLE;
            } else {
                backgroundColor = Color.WHITE;
                cardBackgroundColor = Color.WHITE;
                cardForegroundColor = Color.BLACK;
                visibility = View.INVISIBLE;
            }
        }
    }


    static class IDList {
        final List<Long> items;

        IDList(List<Long> items) {
            this.items = items;
        }

        IDList() {
            items = new ArrayList<>();
        }


        List<Long> getItems() {
            return items;
        }

    }

}
