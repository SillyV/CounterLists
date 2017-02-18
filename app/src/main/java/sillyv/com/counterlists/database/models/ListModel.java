package sillyv.com.counterlists.database.models;

import java.util.ArrayList;
import java.util.List;

import sillyv.com.counterlists.database.dbitems.Counter;
import sillyv.com.counterlists.database.dbitems.CounterList;

/**
 * Created by Vasili on 1/28/2017.
 *
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
    private boolean clickSound;
    private boolean vibrate;
    private boolean speechOutputValue;
    private boolean speechOutputName;
    private boolean keepAwake;
    private boolean volumeKey;
    private List<String> counterNames;
    private List<Long> counterIds;
    private String counterItemsString;

    public String getCounterItemsString() {
        return counterItemsString;
    }

    public void setCounterItemsString(String counterItemsString) {
        this.counterItemsString = counterItemsString;
    }

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
        clickSound = list.isClickSound();
        vibrate = list.isVibrate();
        speechOutputValue = list.isSpeechOutputValue();
        speechOutputName = list.isSpeechOutputName();
        keepAwake = list.isKeepAwake();
        volumeKey = list.isVolumeKey();
        counterNames = new ArrayList<>();
        counterIds = new ArrayList<>();
        counterItemsString = "";
        for (Counter counter :
                list.getCounters()) {
            counterNames.add(counter.getName());
            counterIds.add(counter.getId());
            counterItemsString += counter.getName();
        }

    }

    public List<String> getCounterNames() {
        return counterNames;
    }

    public void setCounterNames(List<String> counterNames) {
        this.counterNames = counterNames;
    }

    public List<Long> getCounterIds() {
        return counterIds;
    }

    public void setCounterIds(List<Long> counterIds) {
        this.counterIds = counterIds;
    }

    public ListModel(long id, int defaultValue, int defaultIncrement, int defaultDecrement,
                     int background, int defaultCardBackgroundColor, int defaultCardForegroundColor,
                     String note, boolean clickSound, boolean vibrate, boolean speechOutputValue,
                     boolean speechOutputName, boolean keepAwake, boolean volumeKey, String name) {
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
    }

    public ListModel(int defaultValue, int defaultIncrement, int defaultDecrement, int background,
                     int defaultCardBackgroundColor, int defaultCardForegroundColor, String note,
                     boolean clickSound, boolean vibrate, boolean speechOutputValue,
                     boolean speechOutputName, boolean keepAwake, boolean volumeKey, String name) {
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
}
