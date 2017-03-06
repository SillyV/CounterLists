package sillyv.com.counterlists.database.dbitems;

import java.util.Date;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import sillyv.com.counterlists.database.models.CounterModel;

/**
 * Created by Vasili on 1/28/2017.
 */

public class Counter
        extends RealmObject
        implements RealmModel {
    @PrimaryKey private long id;
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

    public Counter(CounterModel model, long nextID) {
        this.id = nextID;
        this.created = model.getCreated();
        this.edited = model.getEdited();
        this.valueChanged = model.getValueChanged();
        this.note = model.getNote();
        this.clickSound = model.getClickSound();
        this.vibrate = model.getVibrate();
        this.speechOutputValue = model.getSpeechOutputValue();
        this.speechOutputName = model.getSpeechOutputName();
        this.keepAwake = model.getKeepAwake();
        this.volumeKey = model.getVolumeKey();
        this.name = model.getName();
        this.value = model.getValue();
        this.defaultValue = model.getDefaultValue();
        this.Increment = model.getIncrement();
        this.Decrement = model.getDecrement();
        this.background = model.getBackground();
        this.foreground = model.getForeground();
        this.customOrder = model.getCustomOrder();
    }

    public Counter() {
    }

    public void setData(CounterModel model) {
        this.created = model.getCreated();
        this.edited = model.getEdited();
        this.valueChanged = model.getValueChanged();
        this.note = model.getNote();
        this.clickSound = model.getClickSound();
        this.vibrate = model.getVibrate();
        this.speechOutputValue = model.getSpeechOutputValue();
        this.speechOutputName = model.getSpeechOutputName();
        this.keepAwake = model.getKeepAwake();
        this.volumeKey = model.getVolumeKey();
        this.name = model.getName();
        this.value = model.getValue();
        this.defaultValue = model.getDefaultValue();
        this.Increment = model.getIncrement();
        this.Decrement = model.getDecrement();
        this.background = model.getBackground();
        this.foreground = model.getForeground();
        this.customOrder = model.getCustomOrder();
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

    public void update(CounterModel model) {
        this.setName(model.getName());
        this.setNote(model.getNote());
        this.setClickSound(model.getClickSound());
        this.setVibrate(model.getVibrate());
        this.setSpeechOutputName(model.getSpeechOutputName());
        this.setSpeechOutputValue(model.getSpeechOutputValue());
        this.setKeepAwake(model.getKeepAwake());
        this.setVolumeKey(model.getVolumeKey());
        this.setValue(model.getValue());
        this.setDefaultValue(model.getDefaultValue());
        this.setIncrement(model.getIncrement());
        this.setDecrement(model.getDecrement());
        this.setBackground(model.getBackground());
        this.setForeground(model.getForeground());
        this.setCustomOrder(model.getCustomOrder());
        this.setEdited(new Date());
    }

    public void incremented() {
        this.setValueChanged(new Date());
    }

    public int getCustomOrder() {
        return customOrder;
    }

    public void setCustomOrder(int customOrder) {
        this.customOrder = customOrder;
    }


}
