package sillyv.com.counterlists.screens.lists.upsert;

import sillyv.com.counterlists.screens.Contracts;
import sillyv.com.counterlists.screens.UpsertModel;

/**
 * Created by Vasili.Fedotov on 2/18/2017.
 *
 */

public abstract class UpsertCounterListModel {

    @SuppressWarnings("SameParameterValue") public static class CounterListSettings
            extends UpsertModel
            implements Contracts.UpsertContract.UpsertModel {
        private String defaultIncrement;
        private String defaultDecrement;
        private int defaultColorCounterBackground;
        private int defaultColorCounterText;
        CounterListSettings(String defaultIncrement, String defaultDecrement, int defaultColorCounterBackground, int defaultColorCounterText) {
            this.defaultIncrement = defaultIncrement;
            this.defaultDecrement = defaultDecrement;
            this.defaultColorCounterBackground = defaultColorCounterBackground;
            this.defaultColorCounterText = defaultColorCounterText;
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

        String getDefaultIncrement() {
            return defaultIncrement;
        }

        void setDefaultIncrement(String defaultIncrement) {
            this.defaultIncrement = defaultIncrement;
        }

        String getDefaultDecrement() {
            return defaultDecrement;
        }

        void setDefaultDecrement(String defaultDecrement) {
            this.defaultDecrement = defaultDecrement;
        }

        int getDefaultColorCounterBackground() {
            return defaultColorCounterBackground;
        }

        void setDefaultColorCounterBackground(int defaultColorCounterBackground) {
            this.defaultColorCounterBackground = defaultColorCounterBackground;
        }

        int getDefaultColorCounterText() {
            return defaultColorCounterText;
        }

        void setDefaultColorCounterText(int defaultColorCounterText) {
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

            Builder withName(String name) {
                this.name = name;
                return this;
            }

            Builder withNote(String note) {
                this.note = note;
                return this;
            }

            Builder withDefaultValue(String defaultValue) {
                this.defaultValue = defaultValue;
                return this;
            }

            Builder withBackgroundColor(int backgroundColor) {
                this.backgroundColor = backgroundColor;
                return this;
            }


            Builder withClickSound(int clickSound) {
                this.clickSound = clickSound;
                return this;
            }

            Builder withVibrate(int vibrate) {
                this.vibrate = vibrate;
                return this;
            }

            Builder withSpeakValue(int speakValue) {
                this.speakValue = speakValue;
                return this;
            }


            Builder withSpeakName(int speakName) {
                this.speakName = speakName;
                return this;
            }


            Builder withKeepAwake(int keepAwake) {
                this.keepAwake = keepAwake;
                return this;
            }

            Builder withUseVolume(int useVolume) {
                this.useVolume = useVolume;
                return this;
            }

            Builder withDateCreated(String dateCreated) {
                this.dateCreated = dateCreated;
                return this;
            }

            Builder withDateModified(String dateModified) {
                this.dateModified = dateModified;
                return this;
            }

            Builder withLastUsed(String lastUsed) {
                this.lastUsed = lastUsed;
                return this;
            }

            Builder withToolbarTitle(String toolbarTitle) {
                this.toolbarTitle = toolbarTitle;
                return this;
            }

            Builder withDefaultIncrement(String val) {
                defaultIncrement = val;
                return this;
            }

            Builder withDefaultDecrement(String val) {
                defaultDecrement = val;
                return this;
            }

            Builder withDefaultColorCounterBackground(int val) {
                defaultColorCounterBackground = val;
                return this;
            }

            Builder withDefaultColorCounterText(int val) {
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
