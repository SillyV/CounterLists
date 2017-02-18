package sillyv.com.counterlists.screens.lists.edit;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.views.AdvancedColorPickerDialog;

public class EditCounterListFragment extends Fragment implements EditCounterListContract.EditCounterListView
{
    @BindView(R.id.parent_constraint_layout)
    ViewGroup constraintLayout;

    @BindView(R.id.button_advanced)
    TextView buttonAdvanced;

    @OnClick(R.id.button_advanced)
    void OnAdvancedClick()
    {
        viewAdvanced = !viewAdvanced;
        buttonAdvanced.setText(viewAdvanced ?
                R.string.hide_advanced_settings :
                R.string.show_advanced_settings);
        setAdvancedVisibility();
    }

    @OnClick({R.id.button_background, R.id.button_default_card_background, R.id.button_default_card_foreground})
    void onButtonColorClick(View view)
    {
        AdvancedColorPickerDialog dialog = new AdvancedColorPickerDialog();
        dialog.show(getActivity().getSupportFragmentManager(), "NoticeDialogFragment");



    }

    boolean viewAdvanced = false;

    public EditCounterListFragment()
    {
        // Required empty public constructor
    }

    public static EditCounterListFragment newInstance(String param1, String param2)
    {
        EditCounterListFragment fragment = new EditCounterListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_counter_list, container, false);
        ButterKnife.bind(this, view);
        setAdvancedVisibility();
        return view;
    }

    private void setAdvancedVisibility()
    {
        for (int i = 0; i < constraintLayout.getChildCount(); i++)
        {
            View v = constraintLayout.getChildAt(i);
            if (v.getTag() == null)
            {
                v.setVisibility(viewAdvanced ? View.VISIBLE : View.GONE);
            }
        }

    }


}
