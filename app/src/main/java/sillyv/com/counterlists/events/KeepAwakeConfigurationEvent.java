package sillyv.com.counterlists.events;

/**
 * Created by Vasili.Fedotov on 3/12/2017.
 *
 */

public class KeepAwakeConfigurationEvent {

    private boolean keepAwake;

    public KeepAwakeConfigurationEvent(boolean keepAwake) {
        this.keepAwake = keepAwake;
    }

    public boolean getKeepAwake() {
        return keepAwake;
    }

    public boolean isKeepAwake() {
        return keepAwake;
    }

    public void setKeepAwake(boolean keepAwake) {
        this.keepAwake = keepAwake;
    }
}
