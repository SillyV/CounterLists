package sillyv.com.counterlists.database.models;

import java.util.Date;

import sillyv.com.counterlists.database.dbitems.Counter;

/**
 * Created by Vasili.Fedotov on 2/18/2017.
 *
 */

public class CounterModel {

    private long parentId;
    private long id;
    private Date created;
    private Date edited;
    private Date valueChanged;
    private String note;
    private int clickSound;
    private int vibrate;
    private int speechOutputValue;
    private int speechOutputName;
    private int keepAwake;
    private int volumeKey;
    private String name;
    private int value;
    private int defaultValue;
    private int Increment;
    private int Decrement;
    private int background;
    private int foreground;
    private int customOrder;

    public CounterModel(long id,
                        Date created,
                        Date edited,
                        Date valueChanged,
                        String note,
                        int clickSound,
                        int vibrate,
                        int speechOutputValue,
                        int speechOutputName,
                        int keepAwake,
                        int volumeKey,
                        String name,
                        int value,
                        int defaultValue,
                        int increment,
                        int decrement,
                        int background,
                        int foreground,
                        int customOrder) {
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
        this.customOrder = customOrder;
    }

    public CounterModel() {
    }

    public CounterModel(Counter counter) {
        this.id = counter.getId();
        this.created = counter.getCreated();
        this.edited = counter.getEdited();
        this.valueChanged = counter.getValueChanged();
        this.note = counter.getNote();
        this.clickSound = counter.getClickSound();
        this.vibrate = counter.getVibrate();
        this.speechOutputValue = counter.getSpeechOutputValue();
        this.speechOutputName = counter.getSpeechOutputName();
        this.keepAwake = counter.getKeepAwake();
        this.volumeKey = counter.getVolumeKey();
        this.name = counter.getName();
        this.value = counter.getValue();
        this.defaultValue = counter.getDefaultValue();
        this.Increment = counter.getIncrement();
        this.Decrement = counter.getDecrement();
        this.background = counter.getBackground();
        this.foreground = counter.getForeground();
        this.customOrder = counter.getCustomOrder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getClickSound() {
        return clickSound;
    }

    public void setClickSound(int clickSound) {
        this.clickSound = clickSound;
    }

    public int getVibrate() {
        return vibrate;
    }

    public void setVibrate(int vibrate) {
        this.vibrate = vibrate;
    }

    public int getSpeechOutputValue() {
        return speechOutputValue;
    }

    public void setSpeechOutputValue(int speechOutputValue) {
        this.speechOutputValue = speechOutputValue;
    }

    public int getSpeechOutputName() {
        return speechOutputName;
    }

    public void setSpeechOutputName(int speechOutputName) {
        this.speechOutputName = speechOutputName;
    }

    public int getKeepAwake() {
        return keepAwake;
    }

    public void setKeepAwake(int keepAwake) {
        this.keepAwake = keepAwake;
    }

    public int getVolumeKey() {
        return volumeKey;
    }

    public void setVolumeKey(int volumeKey) {
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

    public int getCustomOrder() {
        return customOrder;
    }
}
