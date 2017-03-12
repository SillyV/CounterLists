package sillyv.com.counterlists.tools;

import android.content.Context;
import android.util.AttributeSet;

import it.beppi.tristatetogglebutton_library.TriStateToggleButton;

/**
 * Created by Vasili.Fedotov on 2/22/2017.
 *
 */

public class SillyTriStateSwitch extends TriStateToggleButton {

    public SillyTriStateSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SillyTriStateSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override public void setToggleMid() {
        super.setToggleMid();
    }
}
