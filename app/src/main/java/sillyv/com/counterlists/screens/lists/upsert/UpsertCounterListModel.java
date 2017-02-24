package sillyv.com.counterlists.screens.lists.upsert;

import sillyv.com.counterlists.screens.Contracts;
import sillyv.com.counterlists.screens.UpsertModel;

/**
 * Created by Vasili on 2/18/2017.
 */

public abstract class UpsertCounterListModel {

    public static class CounterListSettings
            extends UpsertModel
            implements Contracts.UpsertContract.UpsertModel {
        private String defaultIncrement;
        private String defaultDecrement;
        private int defaultColorCounterBackground;
        private int defaultColorCounterText;
        public CounterListSettings(String defaultIncrement,
                                   String defaultDecrement,
                                   int defaultColorCounterBackground,
                                   int defaultColorCounterText) {
            this.defaultIncrement = defaultIncrement;
            this.defaultDecrement = defaultDecrement;
            this.defaultColorCounterBackground = defaultColorCounterBackground;
            this.defaultColorCounterText = defaultColorCounterText;
        }

        public CounterListSettings() {
        }

        public CounterListSettings(String name,
                                   String note,
                                   String defaultValue,
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
                                   String defaultIncrement,
                                   String defaultDecrement,
                                   int defaultColorCounterBackground,
                                   int defaultColorCounterText) {
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
                    toolbarTitle,
                    defaultValue);
            this.defaultIncrement = defaultIncrement;
            this.defaultDecrement = defaultDecrement;
            this.defaultColorCounterBackground = defaultColorCounterBackground;
            this.defaultColorCounterText = defaultColorCounterText;
        }

        private CounterListSettings(Builder builder) {
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
            setDefaultIncrement(builder.defaultIncrement);
            setDefaultDecrement(builder.defaultDecrement);
            setDefaultColorCounterBackground(builder.defaultColorCounterBackground);
            setDefaultColorCounterText(builder.defaultColorCounterText);
        }

        public String getDefaultIncrement() {
            return defaultIncrement;
        }

        public void setDefaultIncrement(String defaultIncrement) {
            this.defaultIncrement = defaultIncrement;
        }

        public String getDefaultDecrement() {
            return defaultDecrement;
        }

        public void setDefaultDecrement(String defaultDecrement) {
            this.defaultDecrement = defaultDecrement;
        }

        public int getDefaultColorCounterBackground() {
            return defaultColorCounterBackground;
        }

        public void setDefaultColorCounterBackground(int defaultColorCounterBackground) {
            this.defaultColorCounterBackground = defaultColorCounterBackground;
        }

        public int getDefaultColorCounterText() {
            return defaultColorCounterText;
        }

        public void setDefaultColorCounterText(int defaultColorCounterText) {
            this.defaultColorCounterText = defaultColorCounterText;
        }


        public static final class Builder {
            private String defaultIncrement;
            private String defaultDecrement;
            private int defaultColorCounterBackground;
            private int defaultColorCounterText;
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


            public Builder() {}

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

            public Builder withDefaultIncrement(String val) {
                defaultIncrement = val;
                return this;
            }

            public Builder withDefaultDecrement(String val) {
                defaultDecrement = val;
                return this;
            }

            public Builder withDefaultColorCounterBackground(int val) {
                defaultColorCounterBackground = val;
                return this;
            }

            public Builder withDefaultColorCounterText(int val) {
                defaultColorCounterText = val;
                return this;
            }

            public CounterListSettings build() {return new CounterListSettings(this);}
        }
    }

    public static class Identifier {
        long id;

        Identifier(long id) {
            this.id = id;
        }

        private Identifier(Builder builder) {
            setId(builder.id);
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }


        public static final class Builder {
            private long id;

            public Builder() {
            }

            public Builder id(long val) {
                id = val;
                return this;
            }


            public Identifier build() {
                return new Identifier(this);
            }
        }
    }


}
