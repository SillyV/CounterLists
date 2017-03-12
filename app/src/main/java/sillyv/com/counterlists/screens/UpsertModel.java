package sillyv.com.counterlists.screens;

/**
 * Created by Vasili.Fedotov on 2/24/2017.
 *
 */

public abstract class UpsertModel
        implements Contracts.UpsertContract.UpsertModel {
    private String name;
    private String note;
    private int backgroundColor;
    private int clickSound;
    private int vibrate;
    private int speakValue;
    private int speakName;
    private int keepAwake;
    private int useVolume;
    private String dateCreated;
    private String dateModified;
    private String lastUsed;
    private String toolbarTitle;
    private String defaultValue;

    public UpsertModel() {
    }

    public UpsertModel(String name,
                       String note,
                       int backgroundColor,
                       int clickSound,
                       int vibrate,
                       int speakValue,
                       int speakName,
                       int keepAwake,
                       int useVolume,
                       String dateCreated,
                       String dateModified,
                       String lastUsed,
                       String toolbarTitle,
                       String defaultValue) {
        this.name = name;
        this.note = note;
        this.backgroundColor = backgroundColor;
        this.clickSound = clickSound;
        this.vibrate = vibrate;
        this.speakValue = speakValue;
        this.speakName = speakName;
        this.keepAwake = keepAwake;
        this.useVolume = useVolume;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.lastUsed = lastUsed;
        this.toolbarTitle = toolbarTitle;
        this.defaultValue = defaultValue;
    }

    @Override public String getDefaultValue() {
        return defaultValue;
    }

    @Override public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override public String getName() {
        return name;
    }

    @Override public void setName(String name) {
        this.name = name;
    }

    @Override public String getNote() {
        return note;
    }

    @Override public void setNote(String note) {
        this.note = note;
    }

    @Override public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override public int getClickSound() {
        return clickSound;
    }

    @Override public void setClickSound(int clickSound) {
        this.clickSound = clickSound;
    }

    @Override public int getVibrate() {
        return vibrate;
    }

    @Override public void setVibrate(int vibrate) {
        this.vibrate = vibrate;
    }

    @Override public int getSpeakValue() {
        return speakValue;
    }

    @Override public void setSpeakValue(int speakValue) {
        this.speakValue = speakValue;
    }

    @Override public int getSpeakName() {
        return speakName;
    }

    @Override public void setSpeakName(int speakName) {
        this.speakName = speakName;
    }

    @Override public int getKeepAwake() {
        return keepAwake;
    }

    @Override public void setKeepAwake(int keepAwake) {
        this.keepAwake = keepAwake;
    }

    @Override public int getUseVolume() {
        return useVolume;
    }

    @Override public void setUseVolume(int useVolume) {
        this.useVolume = useVolume;
    }

    @Override public String getDateCreated() {
        return dateCreated;
    }

    @Override public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override public String getDateModified() {
        return dateModified;
    }

    @Override public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    @Override public String getLastUsed() {
        return lastUsed;
    }

    @Override public void setLastUsed(String lastUsed) {
        this.lastUsed = lastUsed;
    }

    @Override public String getToolbarTitle() {
        return toolbarTitle;
    }

    @Override public void setToolbarTitle(String toolbarTitle) {
        this.toolbarTitle = toolbarTitle;
    }


}
