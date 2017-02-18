package sillyv.com.counterlists;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.widget.Toast;

/**
 * Created by vasil on 2/18/2017.
 */

public abstract class CLFragment extends Fragment {

    private ListenerActivity mListener;

    protected void setTitle(String title) {
        mListener.setTitle(title);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListenerActivity) {
            mListener = (ListenerActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    protected void invalidateOptionsMenu() {
        mListener.invalidateOptionsMenu();
    }

    protected void popBackStack() {
        mListener.popBackStack();
    }

    public abstract void setTitle();

    public interface ListenerActivity {
        void setTitle(String title);

        void invalidateOptionsMenu();

        void setMenu(int menu);

        void popBackStack();
    }


}

