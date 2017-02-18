package sillyv.com.counterlists.screens.lists.upsert;

/**
 * Created by Vasili on 2/18/2017.
 *
 */

public abstract class UpsertCounterListModel  {

    private static final long NEW_LIST_IDENTIFIER = 0L;

    public static class CounterListSettings {
        private String name;
        private String note;
        private String defaultValue;
        private String defaultIncrement;
        private String defaultDecrement;
        private int backgroundColor;
        private int defaultColorCounterBackground;
        private int defaultColorCounterText;
        private boolean clickSound;
        private boolean vibrate;
        private boolean speakValue;
        private boolean speakName;
        private boolean keepAwake;
        private boolean useVolume;
        private String dateCreated;
        private String dateModified;
        private String lastUsed;
        private String toolbarTitle;

        public CounterListSettings() {
        }

        public CounterListSettings(String name, String note, String defaultValue, String defaultIncrement, String defaultDecrement, int backgroundColor, int defaultColorCounterBackground, int defaultColorCounterText, boolean clickSound, boolean vibrate, boolean speakValue, boolean speakName, boolean keepAwake, boolean useVolume, String dateCreated, String dateModified, String lastUsed, String toolbarTitle) {
            this.name = name;
            this.note = note;
            this.defaultValue = defaultValue;
            this.defaultIncrement = defaultIncrement;
            this.defaultDecrement = defaultDecrement;
            this.backgroundColor = backgroundColor;
            this.defaultColorCounterBackground = defaultColorCounterBackground;
            this.defaultColorCounterText = defaultColorCounterText;
            this.clickSound = clickSound;
            this.vibrate = vibrate;
            this.speakValue = speakValue;
            this.speakName = speakName;
            this.keepAwake = keepAwake;
            this.useVolume = useVolume;
            this.dateCreated = dateCreated;
            this.dateModified = dateModified;
            this.lastUsed = lastUsed;
            this.toolbarTitle = toolbarTitle;
        }

        private CounterListSettings(Builder builder) {
            name = builder.name;
            note = builder.note;
            defaultValue = builder.defaultValue;
            defaultIncrement = builder.defaultIncrement;
            defaultDecrement = builder.defaultDecrement;
            backgroundColor = builder.backgroundColor;
            defaultColorCounterBackground = builder.defaultColorCounterBackground;
            defaultColorCounterText = builder.defaultColorCounterText;
            clickSound = builder.clickSound;
            vibrate = builder.vibrate;
            speakValue = builder.speakValue;
            speakName = builder.speakName;
            keepAwake = builder.keepAwake;
            useVolume = builder.useVolume;
            dateCreated = builder.dateCreated;
            dateModified = builder.dateModified;
            lastUsed = builder.lastUsed;
            toolbarTitle = builder.toolbarTitle;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
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

        public int getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
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

        public boolean isSpeakValue() {
            return speakValue;
        }

        public void setSpeakValue(boolean speakValue) {
            this.speakValue = speakValue;
        }

        public boolean isSpeakName() {
            return speakName;
        }

        public void setSpeakName(boolean speakName) {
            this.speakName = speakName;
        }

        public boolean isKeepAwake() {
            return keepAwake;
        }

        public void setKeepAwake(boolean keepAwake) {
            this.keepAwake = keepAwake;
        }

        public boolean isUseVolume() {
            return useVolume;
        }

        public void setUseVolume(boolean useVolume) {
            this.useVolume = useVolume;
        }

        public String getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(String dateCreated) {
            this.dateCreated = dateCreated;
        }

        public String getDateModified() {
            return dateModified;
        }

        public void setDateModified(String dateModified) {
            this.dateModified = dateModified;
        }

        public String getLastUsed() {
            return lastUsed;
        }

        public void setLastUsed(String lastUsed) {
            this.lastUsed = lastUsed;
        }

        public String getToolbarTitle() {
            return toolbarTitle;
        }

        public void setToolbarTitle(String toolbarTitle) {
            this.toolbarTitle = toolbarTitle;
        }

        public static final class Builder {
            private String name;
            private String note;
            private String defaultValue;
            private String defaultIncrement;
            private String defaultDecrement;
            private int backgroundColor;
            private int defaultColorCounterBackground;
            private int defaultColorCounterText;
            private boolean clickSound;
            private boolean vibrate;
            private boolean speakValue;
            private boolean speakName;
            private boolean keepAwake;
            private boolean useVolume;
            private String dateCreated;
            private String dateModified;
            private String lastUsed;
            private String toolbarTitle;

            public Builder() {
            }

            public Builder name(String val) {
                name = val;
                return this;
            }

            public Builder note(String val) {
                note = val;
                return this;
            }

            public Builder defaultValue(String val) {
                defaultValue = val;
                return this;
            }

            public Builder defaultIncrement(String val) {
                defaultIncrement = val;
                return this;
            }

            public Builder defaultDecrement(String val) {
                defaultDecrement = val;
                return this;
            }

            public Builder backgroundColor(int val) {
                backgroundColor = val;
                return this;
            }

            public Builder defaultColorCounterBackground(int val) {
                defaultColorCounterBackground = val;
                return this;
            }

            public Builder defaultColorCounterText(int val) {
                defaultColorCounterText = val;
                return this;
            }

            public Builder clickSound(boolean val) {
                clickSound = val;
                return this;
            }

            public Builder vibrate(boolean val) {
                vibrate = val;
                return this;
            }

            public Builder speakValue(boolean val) {
                speakValue = val;
                return this;
            }

            public Builder speakName(boolean val) {
                speakName = val;
                return this;
            }

            public Builder keepAwake(boolean val) {
                keepAwake = val;
                return this;
            }

            public Builder useVolume(boolean val) {
                useVolume = val;
                return this;
            }

            public Builder dateCreated(String val) {
                dateCreated = val;
                return this;
            }

            public Builder dateModified(String val) {
                dateModified = val;
                return this;
            }

            public Builder lastUsed(String val) {
                lastUsed = val;
                return this;
            }

            public Builder toolbarTitle(String val) {
                toolbarTitle = val;
                return this;
            }

            public CounterListSettings build() {
                return new CounterListSettings(this);
            }
        }
    }

    public static class Identifier {
        long id;

        public Identifier(long id) {
            this.id = id;
        }

        public Identifier() {
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

            public Builder blank(){
                id = NEW_LIST_IDENTIFIER;
                return this;
            }

            public Identifier build() {
                return new Identifier(this);
            }
        }
    }


}
