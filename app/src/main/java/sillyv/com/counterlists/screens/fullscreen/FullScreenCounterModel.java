package sillyv.com.counterlists.screens.fullscreen;

import java.util.Date;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 *
 */

public abstract class FullScreenCounterModel {

    public static class CounterModel {

        boolean clickSound;
        boolean vibrate;
        boolean speakName;
        boolean speakValue;
        boolean useVolume;
        boolean keepAwake;

        String name;
        String note;
        Date lastUsed;
        Date dateCreated;
        Date dateModified;

        private int value;
        private int defaultValue;
        private int decrement;
        private int increment;

        private int background;
        private int foreground;

        private CounterModel(Builder builder) {
            setClickSound(builder.clickSound);
            setVibrate(builder.vibrate);
            setSpeakName(builder.speakName);
            setSpeakValue(builder.speakValue);
            setUseVolume(builder.useVolume);
            setKeepAwake(builder.keepAwake);
            setName(builder.name);
            setNote(builder.note);
            setLastUsed(builder.lastUsed);
            setDateCreated(builder.dateCreated);
            setDateModified(builder.dateModified);
            setValue(builder.value);
            setDefaultValue(builder.defaultValue);
            setDecrement(builder.decrement);
            setIncrement(builder.increment);
            setBackground(builder.background);
            setForeground(builder.foreground);
        }

        public CounterModel() {
        }

        public CounterModel(boolean clickSound,
                            boolean vibrate,
                            boolean speakName,
                            boolean speakValue,
                            boolean useVolume,
                            boolean keepAwake,
                            String name,
                            String note,
                            Date lastUsed,
                            Date dateCreated,
                            Date dateModified,
                            int value,
                            int defaultValue,
                            int decrement,
                            int increment,
                            int background,
                            int foreground) {
            this.clickSound = clickSound;
            this.vibrate = vibrate;
            this.speakName = speakName;
            this.speakValue = speakValue;
            this.useVolume = useVolume;
            this.keepAwake = keepAwake;
            this.name = name;
            this.note = note;
            this.lastUsed = lastUsed;
            this.dateCreated = dateCreated;
            this.dateModified = dateModified;
            this.value = value;
            this.defaultValue = defaultValue;
            this.decrement = decrement;
            this.increment = increment;
            this.background = background;
            this.foreground = foreground;
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

        public boolean isUseVolume() {
            return useVolume;
        }

        public void setUseVolume(boolean useVolume) {
            this.useVolume = useVolume;
        }

        public boolean isKeepAwake() {
            return keepAwake;
        }

        public void setKeepAwake(boolean keepAwake) {
            this.keepAwake = keepAwake;
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

        public Date getLastUsed() {
            return lastUsed;
        }

        public void setLastUsed(Date lastUsed) {
            this.lastUsed = lastUsed;
        }

        public Date getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(Date dateCreated) {
            this.dateCreated = dateCreated;
        }

        public Date getDateModified() {
            return dateModified;
        }

        public void setDateModified(Date dateModified) {
            this.dateModified = dateModified;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(int defaultValue) {
            this.defaultValue = defaultValue;
        }

        public int getDecrement() {
            return decrement;
        }

        public void setDecrement(int decrement) {
            this.decrement = decrement;
        }

        public int getIncrement() {
            return increment;
        }

        public void setIncrement(int increment) {
            this.increment = increment;
        }

        public int getBackground() {
            return background;
        }

        public void setBackground(int background) {
            this.background = background;
        }

        public int getForeground() {
            return foreground;
        }

        public void setForeground(int foreground) {
            this.foreground = foreground;
        }

        public static final class Builder {
            private boolean clickSound;
            private boolean vibrate;
            private boolean speakName;
            private boolean speakValue;
            private boolean useVolume;
            private boolean keepAwake;
            private String name;
            private String note;
            private Date lastUsed;
            private Date dateCreated;
            private Date dateModified;
            private int value;
            private int defaultValue;
            private int decrement;
            private int increment;
            private int background;
            private int foreground;

            public Builder() {}

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

            public Builder withUseVolume(boolean val) {
                useVolume = val;
                return this;
            }

            public Builder withKeepAwake(boolean val) {
                keepAwake = val;
                return this;
            }

            public Builder withName(String val) {
                name = val;
                return this;
            }

            public Builder withNote(String val) {
                note = val;
                return this;
            }

            public Builder withLastUsed(Date val) {
                lastUsed = val;
                return this;
            }

            public Builder withDateCreated(Date val) {
                dateCreated = val;
                return this;
            }

            public Builder withDateModified(Date val) {
                dateModified = val;
                return this;
            }

            public Builder withValue(int val) {
                value = val;
                return this;
            }

            public Builder withDefaultValue(int val) {
                defaultValue = val;
                return this;
            }

            public Builder withDecrement(int val) {
                decrement = val;
                return this;
            }

            public Builder withIncrement(int val) {
                increment = val;
                return this;
            }

            public Builder withBackground(int val) {
                background = val;
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


