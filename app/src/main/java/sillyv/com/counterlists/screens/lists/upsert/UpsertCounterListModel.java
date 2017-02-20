package sillyv.com.counterlists.screens.lists.upsert;

/**
 * Created by Vasili on 2/18/2017.
 *
 */

abstract class UpsertCounterListModel  {

    static class CounterListSettings {
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

        CounterListSettings() {
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

        CounterListSettings(String name, String note, String defaultValue, String defaultIncrement, String defaultDecrement, int backgroundColor, int defaultColorCounterBackground, int defaultColorCounterText, boolean clickSound, boolean vibrate, boolean speakValue, boolean speakName, boolean keepAwake, boolean useVolume) {
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

        String getDefaultValue() {
            return defaultValue;
        }

        void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
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

        int getBackgroundColor() {
            return backgroundColor;
        }

        void setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
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

        boolean isClickSound() {
            return clickSound;
        }


        void setClickSound(boolean clickSound) {
            this.clickSound = clickSound;
        }

        boolean isVibrate() {
            return vibrate;
        }

        void setVibrate(boolean vibrate) {
            this.vibrate = vibrate;
        }

        boolean isSpeakValue() {
            return speakValue;
        }

         void setSpeakValue(boolean speakValue) {
            this.speakValue = speakValue;
        }

         boolean isSpeakName() {
            return speakName;
        }

         void setSpeakName(boolean speakName) {
            this.speakName = speakName;
        }

         boolean isKeepAwake() {
            return keepAwake;
        }

         void setKeepAwake(boolean keepAwake) {
            this.keepAwake = keepAwake;
        }

         boolean isUseVolume() {
            return useVolume;
        }

         void setUseVolume(boolean useVolume) {
            this.useVolume = useVolume;
        }

         String getDateCreated() {
            return dateCreated;
        }

         String getDateModified() {
            return dateModified;
        }

         String getLastUsed() {
            return lastUsed;
        }

         String getToolbarTitle() {
            return toolbarTitle;
        }

        static final class Builder {
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

             Builder() {
            }

            public Builder name(String val) {
                name = val;
                return this;
            }

            public Builder note(String val) {
                note = val;
                return this;
            }

             Builder defaultValue(String val) {
                defaultValue = val;
                return this;
            }

             Builder defaultIncrement(String val) {
                defaultIncrement = val;
                return this;
            }

             Builder defaultDecrement(String val) {
                defaultDecrement = val;
                return this;
            }

             Builder backgroundColor(int val) {
                backgroundColor = val;
                return this;
            }

             Builder defaultColorCounterBackground(int val) {
                defaultColorCounterBackground = val;
                return this;
            }

             Builder defaultColorCounterText(int val) {
                defaultColorCounterText = val;
                return this;
            }

             Builder clickSound(boolean val) {
                clickSound = val;
                return this;
            }

             Builder vibrate(boolean val) {
                vibrate = val;
                return this;
            }

             Builder speakValue(boolean val) {
                speakValue = val;
                return this;
            }

             Builder speakName(boolean val) {
                speakName = val;
                return this;
            }

             Builder keepAwake(boolean val) {
                keepAwake = val;
                return this;
            }

             Builder useVolume(boolean val) {
                useVolume = val;
                return this;
            }

             Builder dateCreated(String val) {
                dateCreated = val;
                return this;
            }

             Builder dateModified(String val) {
                dateModified = val;
                return this;
            }

             Builder lastUsed(String val) {
                lastUsed = val;
                return this;
            }

             Builder toolbarTitle(String val) {
                toolbarTitle = val;
                return this;
            }

             CounterListSettings build() {
                return new CounterListSettings(this);
            }
        }
    }

     static class Identifier {
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


         static final class Builder {
            private long id;

             Builder() {
            }

            public Builder id(long val) {
                id = val;
                return this;
            }


             Identifier build() {
                return new Identifier(this);
            }
        }
    }


}
