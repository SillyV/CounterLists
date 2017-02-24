package sillyv.com.counterlists.screens.counters.upsert;

import sillyv.com.counterlists.screens.UpsertModel;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 *
 */

public abstract class UpsertCounterModel {

     static class CounterModel extends UpsertModel
    {
        private String value;
        private String increment;
        private String decrement;
        private int foregroundColor;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public CounterModel() {
        }

        public CounterModel(String name,
                            String note,
                            String value,
                            int backgroundColor,
                            int clickSound,
                            int vibrate,
                            int speakValue,
                            int speakName,
                            int keepAwake,
                            int useVolume,
                            String dateCreated,
                            String dateModified,
                            String lastUsed,
                            String toolbarTitle,
                            String increment,
                            String decrement,
                            int foregroundColor,String defaultValue) {
            super(name,
                    note,
                    backgroundColor,
                    clickSound,
                    vibrate,
                    speakValue,
                    speakName,
                    keepAwake,
                    useVolume,
                    dateCreated,
                    dateModified,
                    lastUsed,
                    toolbarTitle,defaultValue);
            this.value = value;
            this.increment = increment;
            this.decrement = decrement;
            this.foregroundColor = foregroundColor;
        }

        private CounterModel(Builder builder) {
            super(builder.name,
                    builder.note,
                    builder.backgroundColor,
                    builder.clickSound,
                    builder.vibrate,
                    builder.speakValue,
                    builder.speakName,
                    builder.keepAwake,
                    builder.useVolume,
                    builder.dateCreated,
                    builder.dateModified,
                    builder.lastUsed,
                    builder.toolbarTitle,
                    builder.defaultValue);
            setValue(builder.value);
            setIncrement(builder.increment);
            setDecrement(builder.decrement);
            setForegroundColor(builder.foreground);
        }

        public String getIncrement() {
            return increment;
        }

        public void setIncrement(String increment) {
            this.increment = increment;
        }

        public String getDecrement() {
            return decrement;
        }

        public void setDecrement(String decrement) {
            this.decrement = decrement;
        }

        public int getForegroundColor() {
            return foregroundColor;
        }

        public CounterModel(String value, String increment, String decrement, Integer foregroundColor) {
            this.value = value;
            this.increment = increment;
            this.decrement = decrement;
            this.foregroundColor = foregroundColor;
        }

        public void setForegroundColor(int foregroundColor) {
            this.foregroundColor = foregroundColor;
        }


        public static final class Builder {
            private String increment;
            private String decrement;
            private int foreground;
            private String name;
            private String note;
            private String defaultValue;
            private int backgroundColor;
            private int clickSound;
            private int vibrate;
            private int speakValue;
            private int speakName;
            private int keepAwake;
            private int useVolume;
            private String dateCreated;
            private String dateModified;
            private String lastUsed;
            private String toolbarTitle;
            public String value;



            public Builder withValue(String value) {
                this.value = value;
                return this;
            }

            public Builder withName(String name) {
                this.name = name;
                return this;
            }

            public Builder withNote(String note) {
                this.note = note;
                return this;
            }

            public Builder withDefaultValue(String defaultValue) {
                this.defaultValue = defaultValue;
                return this;
            }

            public Builder withBackgroundColor(int backgroundColor) {
                this.backgroundColor = backgroundColor;
                return this;
            }

            public Builder withClickSound(int clickSound) {
                this.clickSound = clickSound;
                return this;
            }

            public Builder withVibrate(int vibrate) {
                this.vibrate = vibrate;
                return this;
            }

            public Builder withSpeakValue(int speakValue) {
                this.speakValue = speakValue;
                return this;
            }

            public Builder withSpeakName(int speakName) {
                this.speakName = speakName;
                return this;
            }

            public Builder withKeepAwake(int keepAwake) {
                this.keepAwake = keepAwake;
                return this;
            }

            public Builder withUseVolume(int useVolume) {
                this.useVolume = useVolume;
                return this;
            }

            public Builder withDateCreated(String dateCreated) {
                this.dateCreated = dateCreated;
                return this;
            }

            public Builder withDateModified(String dateModified) {
                this.dateModified = dateModified;
                return this;
            }

            public Builder withLastUsed(String lastUsed) {
                this.lastUsed = lastUsed;
                return this;
            }

            public Builder withToolbarTitle(String toolbarTitle) {
                this.toolbarTitle = toolbarTitle;
                return this;
            }
            public Builder() {}

            public Builder withIncrement(String val) {
                increment = val;
                return this;
            }

            public Builder withDecrement(String val) {
                decrement = val;
                return this;
            }

            public Builder withForeground(int val) {
                foreground = val;
                return this;
            }

            public CounterModel build() {return new CounterModel(this);}
        }
    }

}
