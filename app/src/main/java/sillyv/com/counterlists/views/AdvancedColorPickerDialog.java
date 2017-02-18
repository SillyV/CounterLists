package sillyv.com.counterlists.views;


import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.pavlospt.CircleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sillyv.com.counterlists.R;

/**
 * Created by vasil on 1/28/2017.
 */

public class AdvancedColorPickerDialog extends DialogFragment
{

    public static final String TAG = "ASD";
    boolean moreColors = false;
    @BindView(R.id.more_text_view)
    TextView moreTextView;
    private String selectedColor;

    @OnClick(R.id.more_text_view)
    void onMoreClick()
    {
        moreTextView.setText(moreColors ? R.string.more_colors : R.string.less_colors);
        moreColors = !moreColors;
        displayForm();
    }



    @OnClick({R.id.circleView1,
            R.id.circleView2,
            R.id.circleView3,
            R.id.circleView4,
            R.id.circleView5,
            R.id.circleView6,
            R.id.circleView7,
            R.id.circleView8,
            R.id.circleView9,
            R.id.circleView10,
            R.id.circleView11,
            R.id.circleView12,
            R.id.circleView13,
            R.id.circleView14,
            R.id.circleView15,
            R.id.circleView16,
            R.id.circleView17,
            R.id.circleView18,
            R.id.circleView19,
            R.id.circleView20,
            R.id.circleView21,
            R.id.circleView22,
            R.id.circleView23,
            R.id.circleView24,
            R.id.circleView25})
    void OnColorClick(CircleView circleView)
    {
        Log.d(TAG, "OnColorClick: " + circleView.getFillColor());
    }


    @BindView(R.id.constraint_layout_simple)
    ViewGroup simple;

    @BindView(R.id.constraint_layout_advanced)
    ViewGroup advanced;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.fragment_dialog_choose_color, null);
        ButterKnife.bind(this, view);
        builder.setView(view);
        displayForm();

        // Create the AlertDialog object and return it
        return builder.create();
    }

    private void displayForm()
    {
        advanced.setVisibility(moreColors ? View.VISIBLE : View.GONE);
        simple.setVisibility(moreColors ? View.GONE : View.VISIBLE);
    }

    public String getSelectedColor()
    {
        return selectedColor;
    }
}
