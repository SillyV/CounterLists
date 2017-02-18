package sillyv.com.counterlists.events;

import android.support.v4.app.Fragment;

/**
 * Created by Vasili on 1/28/2017.
 */

public class AddFragmentEvent {

    Fragment fragment;

    public AddFragmentEvent(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
