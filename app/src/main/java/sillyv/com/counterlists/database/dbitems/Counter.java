package sillyv.com.counterlists.database.dbitems;

import java.util.Date;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Vasili on 1/28/2017.
 */

public class Counter extends RealmObject implements RealmModel {
    @PrimaryKey
    private long id;
    private Date created;
    private Date edited;
    private Date valueChanged;
    private String note;
    private boolean clickSount;
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
