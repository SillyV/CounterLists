package sillyv.com.counterlists.database.models;

import java.util.Date;

import sillyv.com.counterlists.database.dbitems.Counter;

/**
 * Created by Vasili on 2/18/2017.
 */

public class CounterModel {

    private long parentId;
    private long id;
    private Date created;
    private Date edited;
    private Date valueChanged;
    private String note;
    private boolean clickSound;
    private boolean vibrate;
    private boolean speechOutputValue;
    private boolean speechOutputName;
    private boolean keepAwake;
    private boolean volumeKey;
    private String name;
    private int value;
    private int defaultValue;
    private int Increment;
    private int Decrement;
    private int background;
    private int foreground;

    public CounterModel(long id, Date created, Date edited, Date valueChanged, String note, boolean clickSound, boolean vibrate, boolean speechOutputValue, boolean speechOutputName, boolean keepAwake, boolean volumeKey, String name, int value, int defaultValue, int increment, int decrement, int background, int foreground) {
        this.id = id;
        this.created = created;
        this.edited = edited;
        this.valueChanged = valueChanged;
        this.note = note;
        this.clickSound = clickSound;
        this.vibrate = vibrate;
        this.speechOutputValue = speechOutputValue;
        this.speechOutputName = speechOutputName;
        this.keepAwake = keepAwake;
        this.volumeKey = volumeKey;
        this.name = name;
        this.value = value;
        this.defaultValue = defaultValue;
        Increment = increment;
        Decrement = decrement;
        this.background = background;
        this.foreground = foreground;
    }

    public CounterModel() {
    }

    public CounterModel(Counter counter) {
        this.created = counter.getCreated();
        this.edited = counter.getEdited();
        this.valueChanged = counter.getValueChanged();
        this.note = counter.getNote();
        this.clickSound = counter.isClickSound();
        this.vibrate = counter.isVibrate();
        this.speechOutputValue = counter.isSpeechOutputValue();
        this.speechOutputName = counter.isSpeechOutputName();
        this.keepAwake = counter.isKeepAwake();
        this.volumeKey = counter.isVolumeKey();
        this.name = counter.getName();
        this.value = counter.getValue();
        this.defaultValue = counter.getDefaultValue();
        this.Increment = counter.getIncrement();
        this.Decrement = counter.getDecrement();
        this.background = counter.getBackground();
        this.foreground = counter.getForeground();
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getEdited() {
        return edited;
    }

    public void setEdited(Date edited) {
        this.edited = edited;
    }

    public Date getValueChanged() {
        return valueChanged;
    }

    public void setValueChanged(Date valueChanged) {
        this.valueChanged = valueChanged;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public boolean isSpeechOutputValue() {
        return speechOutputValue;
    }

    public void setSpeechOutputValue(boolean speechOutputValue) {
        this.speechOutputValue = speechOutputValue;
    }

    public boolean isSpeechOutputName() {
        return speechOutputName;
    }

    public void setSpeechOutputName(boolean speechOutputName) {
        this.speechOutputName = speechOutputName;
    }

    public boolean isKeepAwake() {
        return keepAwake;
    }

    public void setKeepAwake(boolean keepAwake) {
        this.keepAwake = keepAwake;
    }

    public boolean isVolumeKey() {
        return volumeKey;
    }

    public void setVolumeKey(boolean volumeKey) {
        this.volumeKey = volumeKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getIncrement() {
        return Increment;
    }

    public void setIncrement(int increment) {
        Increment = increment;
    }

    public int getDecrement() {
        return Decrement;
    }

    public void setDecrement(int decrement) {
        Decrement = decrement;
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
}
