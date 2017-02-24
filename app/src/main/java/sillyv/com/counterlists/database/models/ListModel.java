package sillyv.com.counterlists.database.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sillyv.com.counterlists.database.dbitems.Counter;
import sillyv.com.counterlists.database.dbitems.CounterList;

/**
 * Created by Vasili on 1/28/2017.
 */

public class ListModel {

    private long id;
    private int defaultValue;
    private int defaultIncrement;
    private int defaultDecrement;
    private int background;
    private int defaultCardBackgroundColor;
    private int defaultCardForegroundColor;
    private String note;
    private String name;
    private int clickSound;
    private int vibrate;
    private int speechOutputValue;
    private int speechOutputName;
    private int keepAwake;
    private int volumeKey;
    private List<CounterModel> counters;
    private String counterItemsString;
    private Date dateCreated;
    private Date dateModified;
    private Date dateUsed;

    public ListModel(CounterList list) {
        id = list.getId();
        defaultCardBackgroundColor = list.getDefaultCardBackgroundColor();
        defaultValue = list.getDefaultValue();
        defaultIncrement = list.getDefaultIncrement();
        defaultDecrement = list.getDefaultDecrement();
        background = list.getBackground();
        defaultCardForegroundColor = list.getDefaultCardForegroundColor();
        note = list.getNote();
        name = list.getName();
        clickSound = list.getClickSound();
        vibrate = list.getVibrate();
        speechOutputValue = list.getSpeechOutputValue();
        speechOutputName = list.getSpeechOutputName();
        keepAwake = list.getKeepAwake();
        volumeKey = list.getVolumeKey();
        counters = new ArrayList<>();
        counterItemsString = "";
        for (Counter counter : list.getCounters()) {
            counters.add(new CounterModel(counter));
            counterItemsString += counter.getName() + ", ";
        }
        counterItemsString = replaceLastComma(counterItemsString);
        dateCreated = list.getCreated();
        dateModified = list.getEdited();
        dateUsed = list.getValueChanged();
    }

    public ListModel(long id,
                     int defaultValue,
                     int defaultIncrement,
                     int defaultDecrement,
                     int background,
                     int defaultCardBackgroundColor,
                     int defaultCardForegroundColor,
                     String note,
                     int clickSound,
                     int vibrate,
                     int speechOutputValue,
                     int speechOutputName,
                     int keepAwake,
                     int volumeKey,
                     String name) {
        this.id = id;
        this.defaultValue = defaultValue;
        this.defaultIncrement = defaultIncrement;
        this.defaultDecrement = defaultDecrement;
        this.background = background;
        this.defaultCardBackgroundColor = defaultCardBackgroundColor;
        this.defaultCardForegroundColor = defaultCardForegroundColor;
        this.note = note;
        this.clickSound = clickSound;
        this.vibrate = vibrate;
        this.speechOutputValue = speechOutputValue;
        this.speechOutputName = speechOutputName;
        this.keepAwake = keepAwake;
        this.volumeKey = volumeKey;
        this.name = name;
        dateCreated = new Date();
        counters = new ArrayList<>();
        dateModified = new Date();
        dateUsed = new Date();
    }

    public ListModel(int defaultValue,
                     int defaultIncrement,
                     int defaultDecrement,
                     int background,
                     int defaultCardBackgroundColor,
                     int defaultCardForegroundColor,
                     String note,
                     int clickSound,
                     int vibrate,
                     int speechOutputValue,
                     int speechOutputName,
                     int keepAwake,
                     int volumeKey,
                     String name) {
        this.defaultValue = defaultValue;
        this.defaultIncrement = defaultIncrement;
        this.defaultDecrement = defaultDecrement;
        this.background = background;
        this.defaultCardBackgroundColor = defaultCardBackgroundColor;
        this.defaultCardForegroundColor = defaultCardForegroundColor;
        this.note = note;
        this.clickSound = clickSound;
        this.vibrate = vibrate;
        this.speechOutputValue = speechOutputValue;
        this.speechOutputName = speechOutputName;
        this.keepAwake = keepAwake;
        this.volumeKey = volumeKey;
        this.name = name;
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

    public Date getDateUsed() {
        return dateUsed;
    }

    public void setDateUsed(Date dateUsed) {
        this.dateUsed = dateUsed;
    }

    public String getCounterItemsString() {
        return counterItemsString;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getDefaultIncrement() {
        return defaultIncrement;
    }

    public void setDefaultIncrement(int defaultIncrement) {
        this.defaultIncrement = defaultIncrement;
    }

    public int getDefaultDecrement() {
        return defaultDecrement;
    }

    public void setDefaultDecrement(int defaultDecrement) {
        this.defaultDecrement = defaultDecrement;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getDefaultCardBackgroundColor() {
        return defaultCardBackgroundColor;
    }

    public void setDefaultCardBackgroundColor(int defaultCardBackgroundColor) {
        this.defaultCardBackgroundColor = defaultCardBackgroundColor;
    }

    public int getDefaultCardForegroundColor() {
        return defaultCardForegroundColor;
    }

    public void setDefaultCardForegroundColor(int defaultCardForegroundColor) {
        this.defaultCardForegroundColor = defaultCardForegroundColor;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setCounterItemsString(String counterItemsString) {
        this.counterItemsString = counterItemsString;
    }

    public List<CounterModel> getCounters() {
        return counters;
    }

    public void setCounters(List<CounterModel> counters) {
        this.counters = counters;
        setCounterItemsFromString(counters);
    }

    private void setCounterItemsFromString(List<CounterModel> counters) {
        counterItemsString = "";
        for (CounterModel counter : counters) {
            counterItemsString += counter.getName() + ", ";
        }
        counterItemsString =    replaceLastComma(counterItemsString);
    }

    private String replaceLastComma(String str) {
        if (str != null && str.length() > 1) {
            str = str.substring(0, str.length() - 2) + ".";
        }
        return str;

    }
}
