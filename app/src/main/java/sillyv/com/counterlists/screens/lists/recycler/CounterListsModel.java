package sillyv.com.counterlists.screens.lists.recycler;

import android.graphics.Color;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vasili.Fedotov on 2/18/2017.
 *
 */

class CounterListsModel {

    private final List<ListItem> items;

    CounterListsModel(List<ListItem> items) {
        this.items = items;
    }

    List<ListItem> getItems() {
        return items;
    }

    static class ListItem {
        private final String title;
        private final String subtitle;
        private int backgroundColor;
        private int cardBackgroundColor;
        private int cardForegroundColor;
        private final long id;
        private boolean selected = false;
        private int visibility;

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

        ListItem(String title, String subtitle, int backgroundColor, int cardBackgroundColor, int cardForegroundColor, long id) {
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

        public String getSubtitle() {
            return subtitle;
        }

        int getBackgroundColor() {
            return backgroundColor;
        }

        int getCardBackgroundColor() {
            return cardBackgroundColor;
        }

        int getCardForegroundColor() {
            return cardForegroundColor;
        }

        public long getId() {
            return id;
        }
    }


    static class IDList {
        final List<Long>  items;

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
