package sillyv.com.counterlists.events;

/**
 * Created by Vasili.Fedotov on 2/24/2017.
 */

public class NotifyValueChangedEvent {
    private long id;
    private int value;
    private long parentId;

    public NotifyValueChangedEvent(long id, int value, long parentId) {
        this.id = id;
        this.value = value;
        this.parentId = parentId;
    }

    public long getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public long getParentId() {
        return parentId;
    }
}
