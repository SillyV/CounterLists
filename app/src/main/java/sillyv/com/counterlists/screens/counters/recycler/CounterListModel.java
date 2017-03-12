package sillyv.com.counterlists.screens.counters.recycler;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import sillyv.com.counterlists.database.models.ListModel;
import sillyv.com.counterlists.screens.lists.recycler.CounterListsModel;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 *
 */

class CounterListModel
        extends CounterListsModel.ListItem {

    private List<CounterModel> counterModels;

    CounterListModel(String title,
                     List<String> subtitle,
                     int backgroundColor,
                     int cardBackgroundColor,
                     int cardForegroundColor,
                     long id,
                     ListModel listModel) {
        super(title, subtitle, backgroundColor, cardBackgroundColor, cardForegroundColor, id);

        counterModels = new ArrayList<>();
        for (sillyv.com.counterlists.database.models.CounterModel counterModel : listModel.getCounters()) {
            counterModels.add(new CounterModel(counterModel.getId(),
                    listModel.getId(),
                    counterModel.getName(),
                    counterModel.getValue(),
                    counterModel.getIncrement(),
                    counterModel.getDecrement(),
                    counterModel.getCustomOrder(),
                    counterModel.getClickSound() == 1,
                    counterModel.getVibrate() == 1,
                    counterModel.getSpeechOutputName() == 1,
                    counterModel.getSpeechOutputValue() == 1,
                    counterModel.getBackground(),
                    counterModel.getForeground()));
        }
    }

    List<CounterModel> getCounterModels() {
        return counterModels;
    }

    public static class CounterModel {
        private long id;
        private long parentId;

        private String name;
        private int amount;
        private int increment;
        private int decrement;
        private int customOrder;

        private boolean clickSound;
        private boolean vibrate;
        private boolean speakName;
        private boolean speakValue;

        private int backgroundColor;
        private int foregroundColor;
        private boolean selected;


        public CounterModel() {
        }


        public CounterModel(long id,
                            long parentId,
                            String name,
                            int amount,
                            int increment,
                            int decrement,
                            int customOrder,
                            boolean clickSound,
                            boolean vibrate,
                            boolean speakName,
                            boolean speakValue,
                            int backgroundColor,
                            int foregroundColor) {
            this.id = id;
            this.parentId = parentId;
            this.name = name;
            this.amount = amount;
            this.increment = increment;
            this.decrement = decrement;
            this.customOrder = customOrder;
            this.clickSound = clickSound;
            this.vibrate = vibrate;
            this.speakName = speakName;
            this.speakValue = speakValue;
            this.backgroundColor = backgroundColor;
            this.foregroundColor = foregroundColor;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getParentId() {
            return parentId;
        }

        public void setParentId(long parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        int getAmount() {
            return amount;
        }

        void setAmount(int amount) {
            this.amount = amount;
        }

        public int getIncrement() {
            return increment;
        }

        public void setIncrement(int increment) {
            this.increment = increment;
        }

        public int getDecrement() {
            return decrement;
        }

        public void setDecrement(int decrement) {
            this.decrement = decrement;
        }

        int getCustomOrder() {
            return customOrder;
        }

        void setCustomOrder(int customOrder) {
            this.customOrder = customOrder;
        }

        public boolean isClickSound() {
            return clickSound;
        }

        public void setClickSound(boolean clickSound) {
            this.clickSound = clickSound;
        }

        public boolean isVibrate() {
            return vibrate;
        }

        public void setVibrate(boolean vibrate) {
            this.vibrate = vibrate;
        }

        boolean isSpeakName() {
            return speakName;
        }

        void setSpeakName(boolean speakName) {
            this.speakName = speakName;
        }

        boolean isSpeakValue() {
            return speakValue;
        }

        void setSpeakValue(boolean speakValue) {
            this.speakValue = speakValue;
        }

        public int getBackgroundColor() {
            return backgroundColor;
        }

        void setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        int getForegroundColor() {
            return foregroundColor;
        }

        void setForegroundColor(int foregroundColor) {
            this.foregroundColor = foregroundColor;
        }

        void setSelected(boolean selected) {
            this.selected = selected;
            if (selected) {
                backgroundColor = Color.BLACK;
                foregroundColor = Color.WHITE;
            } else {
                backgroundColor = Color.WHITE;
                foregroundColor = Color.BLACK;
            }
        }

        boolean isSelected() {
            return selected;

        }
    }
}

