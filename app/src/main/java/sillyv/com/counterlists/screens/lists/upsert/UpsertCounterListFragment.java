package sillyv.com.counterlists.screens.lists.upsert;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sillyv.com.counterlists.CLFragment;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.database.controllers.ListController;
import sillyv.com.counterlists.tools.ValidationTools;

import static android.text.InputType.TYPE_NUMBER_FLAG_SIGNED;

public class UpsertCounterListFragment extends CLFragment implements UpsertCounterListContract.UpsertCounterListView {
    private static final String LIST_IDENTIFIER = "ListIdentifier";
    //region ButterKnife View Binding
    @BindView(R.id.edit_text_name)
    private
    TextView editTextName;
    @BindView(R.id.parent_constraint_layout)
    private
    ViewGroup constraintLayout;
    @BindView(R.id.button_advanced)
    private
    TextView buttonAdvanced;
    @BindView(R.id.edit_text_note)
    private
    TextView editTextNote;
    @BindView(R.id.edit_text_default_value)
    private
    TextView editTextDefaultValue;
    @BindView(R.id.edit_text_default_increment)
    private
    TextView editTextDefaultIncrement;
    @BindView(R.id.edit_text_default_decrement)
    private
    TextView editTextDefaultDecrement;

    @BindView(R.id.button_background)
    private
    View buttonBackground;
    @BindView(R.id.button_default_card_background)
    private
    View buttonDefaultCardBackground;
    @BindView(R.id.button_default_card_foreground)
    private
    View buttonDefaultCardForeground;

    @BindView(R.id.switch_click_sound)
    private
    Switch switchClickSound;
    @BindView(R.id.switch_vibrate)
    private
    Switch switchVibrate;
    @BindView(R.id.switch_speech_output_value)
    private
    Switch switchSpeechOutputValue;
    @BindView(R.id.switch_speech_output_name)
    private
    Switch switchSpeechOutputName;
    @BindView(R.id.switch_keep_awake)
    private
    Switch switchKeepAwake;
    @BindView(R.id.switch_volume_key)
    private
    Switch switchVolumeKey;

    @BindView(R.id.text_view_date_created_stats)
    private
    TextView textViewDateCreatedStats;
    @BindView(R.id.text_view_date_modified_stats)
    private
    TextView textViewDateModifiedStats;
    @BindView(R.id.text_view_date_changed_stats)
    private
    TextView textViewDateChangedStats;

    private boolean foundErrors;

    @BindViews({R.id.text_input_layout_default_decrement, R.id.text_input_layout_default_increment, R.id.text_input_layout_default_value, R.id.text_input_layout_name, R.id.text_input_layout_note})
    private
    List<TextInputLayout> textLayouts;


    private final ButterKnife.Action<TextInputLayout> CHECK_FOR_ERRORS = new ButterKnife.Action<TextInputLayout>() {
        @Override

        public void apply(@NonNull TextInputLayout view, int index) {
            EditText editText = view.getEditText();
            view.setError(null);
            view.setErrorEnabled(false);
            if (editText == null) {
                foundErrors = true;
                return;
            }
            String content = editText.getText().toString();
            if (content.equals("")) {
                foundErrors = true;
                view.setError("You need to enter a name");
                return;
            }
            if (editText.getInputType() == TYPE_NUMBER_FLAG_SIGNED &&
                    !ValidationTools.getInstance().isStringNumeric(content)) {
                foundErrors = true;
                view.setError("You need to enter a number");
            }
        }
    };
    private UpsertCounterListContract.UpsertCounterListPresenter presenter;
    private String toolbarTitle;


//endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_counter_list, container, false);
        ButterKnife.bind(this, view);
        setAdvancedVisibility();
        invalidateOptionsMenu();
        presenter = new UpsertCounterListPresenter();
        presenter.getData(getContext().getApplicationContext(), this, getIdentifier());
        return view;
    }

    @NonNull
    private UpsertCounterListModel.Identifier getIdentifier() {
        return new UpsertCounterListModel.Identifier.Builder().id(id).build();
    }

    private long id;


    private HashMap<View, Integer> colorButtonMap;

    @OnClick(R.id.button_advanced)
    private void onAdvancedClick() {
        viewAdvanced = !viewAdvanced;
        buttonAdvanced.setText(viewAdvanced ?
                R.string.hide_advanced_settings :
                R.string.show_advanced_settings);
        setAdvancedVisibility();
        invalidateOptionsMenu();

    }

    @OnClick({R.id.button_background, R.id.button_default_card_background, R.id.button_default_card_foreground})
    void onButtonColorClick(final View view) {


        ColorPickerDialogBuilder
                .with(getContext())
                .setTitle("Choose color")
                .initialColor(colorButtonMap.get(view))
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
//                        Toast.makeText(getContext(), "onColorSelected: 0x" + Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        view.getBackground().setColorFilter(selectedColor, PorterDuff.Mode.MULTIPLY);
                        Integer selected = selectedColor;
                        colorButtonMap.put(view, selected);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();


    }

    private boolean viewAdvanced = false;

    public UpsertCounterListFragment() {
        // Required empty public constructor
    }

    public static UpsertCounterListFragment newInstance() {
        UpsertCounterListFragment fragment = new UpsertCounterListFragment();
        Bundle args = new Bundle();
        args.putLong(LIST_IDENTIFIER, ListController.DEFAULT_LIST);
        fragment.setArguments(args);
        return fragment;
    }

    public static UpsertCounterListFragment newInstance(long id) {
        UpsertCounterListFragment fragment = new UpsertCounterListFragment();
        Bundle args = new Bundle();
        args.putLong(LIST_IDENTIFIER, id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong(LIST_IDENTIFIER);
        }


    }


    private void setAdvancedVisibility() {
        for (int i = 0; i < constraintLayout.getChildCount(); i++) {
            View v = constraintLayout.getChildAt(i);
            if (v.getTag() == null) {
                v.setVisibility(viewAdvanced ? View.VISIBLE : View.GONE);
            }
        }
    }

    @Override
    public void onDataReceived(UpsertCounterListModel.CounterListSettings model) {
        colorButtonMap = new HashMap<>();
        editTextName.setText(model.getName());
        editTextNote.setText(model.getNote());
        editTextDefaultValue.setText(model.getDefaultValue());
        editTextDefaultIncrement.setText(model.getDefaultIncrement());
        editTextDefaultDecrement.setText(model.getDefaultDecrement());
        Integer backgroundColor = model.getBackgroundColor();
        buttonBackground.getBackground().setColorFilter(backgroundColor, PorterDuff.Mode.MULTIPLY);
        Integer defaultColorCounterBackground = model.getDefaultColorCounterBackground();
        buttonDefaultCardBackground.getBackground().setColorFilter(defaultColorCounterBackground, PorterDuff.Mode.MULTIPLY);
        Integer defaultColorCounterText = model.getDefaultColorCounterText();
        buttonDefaultCardForeground.getBackground().setColorFilter(defaultColorCounterText, PorterDuff.Mode.MULTIPLY);
        colorButtonMap.put(buttonBackground, backgroundColor);
        colorButtonMap.put(buttonDefaultCardBackground, defaultColorCounterBackground);
        colorButtonMap.put(buttonDefaultCardForeground, defaultColorCounterText);
        switchClickSound.setChecked(model.isClickSound());
        switchVibrate.setChecked(model.isVibrate());
        switchSpeechOutputValue.setChecked(model.isSpeakValue());
        switchSpeechOutputName.setChecked(model.isSpeakName());
        switchKeepAwake.setChecked(model.isKeepAwake());
        switchVolumeKey.setChecked(model.isUseVolume());
        textViewDateCreatedStats.setText(model.getDateCreated());
        textViewDateModifiedStats.setText(model.getDateModified());
        textViewDateChangedStats.setText(model.getLastUsed());
        toolbarTitle = model.getToolbarTitle();
        setTitle(toolbarTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                foundErrors = false;
                ButterKnife.apply(textLayouts, CHECK_FOR_ERRORS);
                if (!foundErrors) {
                    presenter.saveData(getModelForPresenter(), getIdentifier());
                    popBackStack ();
                }
                break;
            case R.id.show_advanced:
            case R.id.hide_advanced:
                onAdvancedClick();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private UpsertCounterListModel.CounterListSettings getModelForPresenter() {


        UpsertCounterListModel.CounterListSettings model = new UpsertCounterListModel.CounterListSettings();
        model.setName(editTextName.getText().toString());
        model.setNote(editTextNote.getText().toString());
        model.setDefaultValue(editTextDefaultValue.getText().toString());
        model.setDefaultIncrement(editTextDefaultIncrement.getText().toString());
        model.setDefaultDecrement(editTextDefaultDecrement.getText().toString());
        model.setBackgroundColor(colorButtonMap.get(buttonBackground));
        model.setDefaultColorCounterBackground(colorButtonMap.get(buttonDefaultCardBackground));
        model.setDefaultColorCounterText(colorButtonMap.get(buttonDefaultCardForeground));

        model.setClickSound(switchClickSound.isChecked());
        model.setVibrate(switchVibrate.isChecked());
        model.setSpeakValue(switchSpeechOutputValue.isChecked());
        model.setSpeakName(switchSpeechOutputName.isChecked());
        model.setKeepAwake(switchKeepAwake.isChecked());
        model.setUseVolume(switchVolumeKey.isChecked());


        textViewDateCreatedStats.setText(model.getDateCreated());
        textViewDateModifiedStats.setText(model.getDateModified());
        textViewDateChangedStats.setText(model.getLastUsed());
        setTitle(model.getToolbarTitle());
        return model;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.upsert_list_menu, menu);
        MenuItem show = menu.findItem(R.id.show_advanced);
        MenuItem hide = menu.findItem(R.id.hide_advanced);
        show.setVisible(!viewAdvanced);
        hide.setVisible(viewAdvanced);
    }

    @Override
    public void setTitle() {
        setTitle(toolbarTitle);
    }
}
