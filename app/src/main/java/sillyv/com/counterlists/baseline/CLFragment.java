package sillyv.com.counterlists.baseline;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import java.util.List;

/**
 * Created by Vasili.Fedotov on 2/18/2017.
 *
 */

public abstract class CLFragment
        extends Fragment {

    private ListenerActivity mListener;

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListenerActivity) {
            mListener = (ListenerActivity) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override public void onStop() {
        for (BasePresenter basePresenter : getPresenters()) {
            basePresenter.unsubscribe();
        }
        super.onStop();
    }

    public abstract boolean volumePressed(int keyCode, KeyEvent event);


    protected abstract List<BasePresenter> getPresenters();


    @Override public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public abstract void setTitle();

    protected void setTitle(String title) {
        mListener.setTitle(title);
    }

    protected void invalidateOptionsMenu() {
        mListener.invalidateOptionsMenu();
    }

    protected void popBackStack() {
        mListener.popBackStack();
    }

    public interface ListenerActivity {
        void setTitle(String title);

        void invalidateOptionsMenu();

        void popBackStack();
    }
}

