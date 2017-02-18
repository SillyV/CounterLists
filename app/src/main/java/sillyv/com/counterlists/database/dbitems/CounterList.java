package sillyv.com.counterlists.database.dbitems;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import sillyv.com.counterlists.database.models.ListModel;

/**
 * Created by vasil on 1/28/2017.
 */

public class CounterList extends RealmObject {
    @PrimaryKey
    private long id;

    private int defaultValue;
    private int defaultIncrement;
    private int defaultDecrement;
    private int background;
    private int defaultCardBackgroundColor;
    private int defaultCardForegroundColor;
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
    private RealmList<Counter> counters;

    public CounterList() {
    }

    public CounterList(ListModel model, long id) {
        this.id =id;
        defaultCardBackgroundColor = model.getDefaultCardBackgroundColor();
        defaultValue = model.getDefaultValue();
        defaultIncrement = model.getDefaultIncrement();
        defaultDecrement = model.getDefaultDecrement();
        background = model.getBackground();
        defaultCardForegroundColor = model.getDefaultCardForegroundColor();
        created = new Date();
        edited = new Date();
        valueChanged = new Date();
        note = model.getNote();
        clickSount = model.isClickSound();
        vibrate = model.isVibrate();
        speechOutputValue = model.isSpeechOutputValue();
        speechOutputName = model.isSpeechOutputName();
        keepAwake = model.isKeepAwake();
        volumeKey = model.isVolumeKey();
        name = model.getName();
        counters = new RealmList<>();
    }

    public void update(ListModel model) {
        defaultCardBackgroundColor = model.getDefaultCardBackgroundColor();
        defaultValue = model.getDefaultValue();
        defaultIncrement = model.getDefaultIncrement();
        defaultDecrement = model.getDefaultDecrement();
        background = model.getBackground();
        defaultCardForegroundColor = model.getDefaultCardForegroundColor();
        edited = new Date();
        note = model.getNote();
        clickSount = model.isClickSound();
        vibrate = model.isVibrate();
        speechOutputValue = model.isSpeechOutputValue();
        speechOutputName = model.isSpeechOutputName();
        keepAwake = model.isKeepAwake();
        volumeKey = model.isVolumeKey();
        name = model.getName();
        counters = new RealmList<>();
    }


    public RealmList<Counter> getCounters() {
        return counters;
    }

    public void setCounters(RealmList<Counter> counters) {
        this.counters = counters;
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

    public void incremented() {
        valueChanged = new Date();

    }
}
