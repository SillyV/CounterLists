package sillyv.com.counterlists.screens.counters.recycler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sillyv.com.counterlists.R;

public class CounterListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_counter_list, container, false);

        return view;
    }

    public CounterListFragment() {
        // Required empty public constructor
    }
    public static CounterListFragment newInstance(String param1, String param2) {
        CounterListFragment fragment = new CounterListFragment();
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


}
