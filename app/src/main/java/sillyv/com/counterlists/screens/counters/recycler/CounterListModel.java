package sillyv.com.counterlists.screens.counters.recycler;

import java.util.ArrayList;
import java.util.List;

import sillyv.com.counterlists.database.models.ListModel;
import sillyv.com.counterlists.screens.lists.recycler.CounterListsModel;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 */

public class CounterListModel
        extends CounterListsModel.ListItem {

    private final ListModel listModel;
    List<CounterModel> counterModels;

    CounterListModel(String title,
                     List<String> subtitle,
                     int backgroundColor,
                     int cardBackgroundColor,
                     int cardForegroundColor,
                     long id,
                     ListModel listModel) {
        super(title, subtitle, backgroundColor, cardBackgroundColor, cardForegroundColor, id);
        this.listModel = listModel;

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

    public List<CounterModel> getCounterModels() {
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


        public CounterModel() {
        }

        private CounterModel(Builder builder) {
            setId(builder.id);
            setParentId(builder.parentId);
            setName(builder.name);
            setAmount(builder.amount);
            setIncrement(builder.increment);
            setDecrement(builder.decrement);
            setCustomOrder(builder.customOrder);
            setClickSound(builder.clickSound);
            setVibrate(builder.vibrate);
            setSpeakName(builder.speakName);
            setSpeakValue(builder.speakValue);
            setBackgroundColor(builder.backgroundColor);
            setForegroundColor(builder.foregroundColor);
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

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
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

        public int getCustomOrder() {
            return customOrder;
        }

        public void setCustomOrder(int customOrder) {
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

        public boolean isSpeakName() {
            return speakName;
        }

        public void setSpeakName(boolean speakName) {
            this.speakName = speakName;
        }

        public boolean isSpeakValue() {
            return speakValue;
        }

        public void setSpeakValue(boolean speakValue) {
            this.speakValue = speakValue;
        }

        public int getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public int getForegroundColor() {
            return foregroundColor;
        }

        public void setForegroundColor(int foregroundColor) {
            this.foregroundColor = foregroundColor;
        }

        public static final class Builder {
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

            public Builder() {}

            public Builder withId(long val) {
                id = val;
                return this;
            }

            public Builder withParentId(long val) {
                parentId = val;
                return this;
            }

            public Builder withName(String val) {
                name = val;
                return this;
            }

            public Builder withAmount(int val) {
                amount = val;
                return this;
            }

            public Builder withIncrement(int val) {
                increment = val;
                return this;
            }

            public Builder withDecrement(int val) {
                decrement = val;
                return this;
            }

            public Builder withCustomOrder(int val) {
                customOrder = val;
                return this;
            }

            public Builder withClickSound(boolean val) {
                clickSound = val;
                return this;
            }

            public Builder withVibrate(boolean val) {
                vibrate = val;
                return this;
            }

            public Builder withSpeakName(boolean val) {
                speakName = val;
                return this;
            }

            public Builder withSpeakValue(boolean val) {
                speakValue = val;
                return this;
            }

            public Builder withBackgroundColor(int val) {
                backgroundColor = val;
                return this;
            }

            public Builder withForegroundColor(int val) {
                foregroundColor = val;
                return this;
            }

            public CounterModel build() {return new CounterModel(this);}
        }
    }
}

