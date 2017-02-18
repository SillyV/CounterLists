package sillyv.com.counterlists.screens.lists.edit;

import android.graphics.Color;

/**
 * Created by vasil on 2/18/2017.
 */

public abstract class EditCounterListModel implements EditCounterListContract.EditCounterListModel {

    public static class CounterListSettings {
        private String name;
        private String note;
        private String defaultVaule;
        private String defaultIncrement;
        private String defaultDecrement;
        private Color backgroundColor;
        private Color defaultColorCounterBackground;
        private Color defaultColorCounterText;
        private boolean clickSount;
        private boolean vibrate;
        private boolean speakValue;
        private boolean speakName;
        private boolean keepAwake;
        private boolean useVolume;
        private String dateCreated;
        private String dateModified;
        private String lastUsed;

        private CounterListSettings(Builder builder) {
            name = builder.name;
            note = builder.note;
            defaultVaule = builder.defaultVaule;
            defaultIncrement = builder.defaultIncrement;
            defaultDecrement = builder.defaultDecrement;
            backgroundColor = builder.backgroundColor;
            defaultColorCounterBackground = builder.defaultColorCounterBackground;
            defaultColorCounterText = builder.defaultColorCounterText;
            clickSount = builder.clickSount;
            vibrate = builder.vibrate;
            speakValue = builder.speakValue;
            speakName = builder.speakName;
            keepAwake = builder.keepAwake;
            useVolume = builder.useVolume;
            dateCreated = builder.dateCreated;
            dateModified = builder.dateModified;
            lastUsed = builder.lastUsed;
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

        public String getDefaultVaule() {
            return defaultVaule;
        }

        public void setDefaultVaule(String defaultVaule) {
            this.defaultVaule = defaultVaule;
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

        public Color getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(Color backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public Color getDefaultColorCounterBackground() {
            return defaultColorCounterBackground;
        }

        public void setDefaultColorCounterBackground(Color defaultColorCounterBackground) {
            this.defaultColorCounterBackground = defaultColorCounterBackground;
        }

        public Color getDefaultColorCounterText() {
            return defaultColorCounterText;
        }

        public void setDefaultColorCounterText(Color defaultColorCounterText) {
            this.defaultColorCounterText = defaultColorCounterText;
        }

        public boolean isClickSount() {
            return clickSount;
        }

        public void setClickSount(boolean clickSount) {
            this.clickSount = clickSount;
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

        public CounterListSettings() {
        }

        public static final class Builder {
            private String name;
            private String note;
            private String defaultVaule;
            private String defaultIncrement;
            private String defaultDecrement;
            private Color backgroundColor;
            private Color defaultColorCounterBackground;
            private Color defaultColorCounterText;
            private boolean clickSount;
            private boolean vibrate;
            private boolean speakValue;
            private boolean speakName;
            private boolean keepAwake;
            private boolean useVolume;
            private String dateCreated;
            private String dateModified;
            private String lastUsed;

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

            public Builder defaultVaule(String val) {
                defaultVaule = val;
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

            public Builder backgroundColor(Color val) {
                backgroundColor = val;
                return this;
            }

            public Builder defaultColorCounterBackground(Color val) {
                defaultColorCounterBackground = val;
                return this;
            }

            public Builder defaultColorCounterText(Color val) {
                defaultColorCounterText = val;
                return this;
            }

            public Builder clickSount(boolean val) {
                clickSount = val;
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

            public CounterListSettings build() {
                return new CounterListSettings(this);
            }
        }
    }


}
