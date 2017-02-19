package sillyv.com.counterlists.screens.lists.recycler;

import android.graphics.Color;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vasili on 2/18/2017.
 */

public class CounterListsModel {

    List<ListItem> items;

    public CounterListsModel(List<ListItem> items) {
        this.items = items;
    }

    public CounterListsModel() {
    }

    public List<ListItem> getItems() {
        return items;
    }

    public void setItems(List<ListItem> items) {
        this.items = items;
    }

    public static class ListItem {
        private String title;
        private String subtitle;
        private int backgroundColor;
        private int cardBackgroundColor;
        private int cardForgroundColor;
        private long id;
        private boolean selected = false;
        private int visibility;

        public int getVisibility() {
            return visibility;
        }


        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
            if (selected) {
                backgroundColor = Color.BLACK;
                cardBackgroundColor = Color.BLACK;
                cardForgroundColor = Color.WHITE;
                visibility = View.VISIBLE;
            } else {
                backgroundColor = Color.WHITE;
                cardBackgroundColor = Color.WHITE;
                cardForgroundColor = Color.BLACK;
                visibility = View.INVISIBLE;
            }
        }

        public ListItem() {
        }

        public ListItem(String title, String subtitle, int backgroundColor, int cardBackgroundColor, int cardForgroundColor, long id) {
            this.title = title;
            this.subtitle = subtitle;
            this.backgroundColor = backgroundColor;
            this.cardBackgroundColor = cardBackgroundColor;
            this.cardForgroundColor = cardForgroundColor;
            this.id = id;
            visibility = View.INVISIBLE;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public int getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public int getCardBackgroundColor() {
            return cardBackgroundColor;
        }

        public void setCardBackgroundColor(int cardBackgroundColor) {
            this.cardBackgroundColor = cardBackgroundColor;
        }

        public int getCardForgroundColor() {
            return cardForgroundColor;
        }

        public void setCardForgroundColor(int cardForgroundColor) {
            this.cardForgroundColor = cardForgroundColor;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }


    public static class IDList {
        List<Long> items;

        public IDList(List<Long> items) {
            this.items = items;
        }

        public IDList() {
            items = new ArrayList<>();
        }


        public List<Long> getItems() {
            return items;
        }

        public void setItems(List<Long> items) {
            this.items = items;
        }
    }

}
