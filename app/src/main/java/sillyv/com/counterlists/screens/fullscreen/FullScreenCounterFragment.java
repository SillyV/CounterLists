package sillyv.com.counterlists.screens.fullscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import sillyv.com.counterlists.R;
import sillyv.com.counterlists.baseline.BasePresenter;
import sillyv.com.counterlists.baseline.CLFragment;

public class FullScreenCounterFragment extends CLFragment {

    public FullScreenCounterFragment() {
        // Required empty public constructor
    }

    public static FullScreenCounterFragment newInstance(long id) {
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

    @Override protected List<BasePresenter> getPresenters() {
        return Collections.emptyList();
    }

    @Override public void setTitle() {

    }
}
