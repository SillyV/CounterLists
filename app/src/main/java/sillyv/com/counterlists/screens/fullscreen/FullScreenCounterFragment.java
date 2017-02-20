package sillyv.com.counterlists.screens.fullscreen;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sillyv.com.counterlists.R;
public class FullScreenCounterFragment extends Fragment {

    public FullScreenCounterFragment() {
        // Required empty public constructor
    }

    public static FullScreenCounterFragment newInstance(String param1, String param2) {
        FullScreenCounterFragment fragment = new FullScreenCounterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_screen_counter, container, false);
    }

}
