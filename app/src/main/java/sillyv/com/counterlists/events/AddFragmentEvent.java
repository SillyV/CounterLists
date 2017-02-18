package sillyv.com.counterlists.events;

import sillyv.com.counterlists.CLFragment;

/**
 * Created by Vasili on 1/28/2017.
 *
 */

public class AddFragmentEvent {

    private CLFragment fragment;

    public AddFragmentEvent(CLFragment fragment) {
        this.fragment = fragment;
    }

    public CLFragment getFragment() {
        return fragment;
    }

    public void setFragment(CLFragment fragment) {
        this.fragment = fragment;
    }
}
