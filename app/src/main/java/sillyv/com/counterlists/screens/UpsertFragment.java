package sillyv.com.counterlists.screens;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.TextView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.beppi.tristatetogglebutton_library.TriStateToggleButton;
import sillyv.com.counterlists.CLFragment;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.screens.lists.upsert.UpsertCounterListContract;
import sillyv.com.counterlists.screens.lists.upsert.UpsertCounterListModel;
import sillyv.com.counterlists.tools.ValidationTools;

import static android.text.InputType.TYPE_NUMBER_FLAG_SIGNED;


public abstract class UpsertFragment<D extends UpsertModel>
        extends CLFragment {
    public static final ButterKnife.Action<View> INVISIBLE = (view, index) -> view.setVisibility(View.INVISIBLE);
    public static final ButterKnife.Action<TriStateToggleButton> SET_STATE_AGAIN = (view, index) -> {
        if (view.getToggleStatus() == TriStateToggleButton.ToggleStatus.mid) {
            view.setToggleMid();
            view.setBackgroundColor(view.getMidColor());
        }
    };
    public static final ButterKnife.Action<TriStateToggleButton>
            TEMPORARY_BROKEN_TOGGLE_FIX =
            (view, index) -> view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                int oldVis = View.GONE;

                @Override public void onGlobalLayout() {
                    int newVis = view.getVisibility();
                    if (oldVis != newVis) {
                        oldVis = (view.getVisibility());
                        if (view.getToggleStatus() == TriStateToggleButton.ToggleStatus.mid) {
                            view.setToggleOn(false);
                            view.setToggleMid(false);
                        }
                        if (oldVis == View.VISIBLE) {
                            view.callOnClick();
                            view.callOnClick();
                            view.callOnClick();
                        }
                    }
                }
            });
    protected static final String LIST_IDENTIFIER = "ListIdentifier";
    public HashMap<View, Integer> colorButtonMap;
    @BindView(R.id.edit_text_name) protected TextView editTextName;
    @BindView(R.id.parent_constraint_layout) protected ViewGroup constraintLayout;
    @BindView(R.id.button_advanced) protected TextView buttonAdvanced;
    @BindView(R.id.edit_text_note) protected TextView editTextNote;
    @BindView(R.id.edit_text_default_value) protected TextView editTextDefaultValue;
    @BindView(R.id.button_background) protected View buttonBackground;
    @BindView(R.id.switch_click_sound) protected TriStateToggleButton switchClickSound;
    @BindView(R.id.switch_vibrate) protected TriStateToggleButton switchVibrate;
    @BindView(R.id.switch_speech_output_value) protected TriStateToggleButton switchSpeechOutputValue;
    @BindView(R.id.switch_speech_output_name) protected TriStateToggleButton switchSpeechOutputName;
    @BindView(R.id.switch_keep_awake) protected TriStateToggleButton switchKeepAwake;
    @BindView(R.id.switch_volume_key) protected TriStateToggleButton switchVolumeKey;
    @BindView(R.id.text_view_date_created_stats) protected TextView textViewDateCreatedStats;
    @BindView(R.id.switch_click_sound_hint) protected TextView switchClickSoundHint;
    @BindView(R.id.switch_keep_awake_hint) protected TextView switchKeepAwakeHint;
    @BindView(R.id.switch_speech_output_name_hint) protected TextView switchSpeechOutputNameHint;
    @BindView(R.id.switch_speech_output_value_hint) protected TextView switchSpeechOutputValueHint;
    @BindView(R.id.switch_vibrate_hint) protected TextView switchVibrateHint;
    @BindView(R.id.switch_volume_key_hint) protected TextView switchVolumeKeyHint;
    @BindView(R.id.text_view_date_modified_stats) protected TextView textViewDateModifiedStats;
    @BindView(R.id.text_view_date_changed_stats) protected TextView textViewDateChangedStats;
    @BindViews({R.id.switch_click_sound_hint,
            R.id.switch_keep_awake_hint,
            R.id.switch_speech_output_name_hint,
            R.id.switch_speech_output_value_hint,
            R.id.switch_vibrate_hint,
            R.id.switch_volume_key_hint}) protected List<View> hints;
    @BindViews({R.id.switch_click_sound,
            R.id.switch_keep_awake,
            R.id.switch_speech_output_name,
            R.id.switch_speech_output_value,
            R.id.switch_vibrate,
            R.id.switch_volume_key}) protected List<TriStateToggleButton> toggles;
    protected boolean viewAdvanced = false;
    //endregion
    protected long id;
    protected String toolbarTitle;
    //region ButterKnife View Binding
    private boolean foundErrors;
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
                CharSequence hint = view.getHint();
                if (hint != null) {
                    view.setError(getString(R.string.you_need_to_enter_a) + hint.toString());
                } else {
                    view.setError(getString(R.string.required_field));

                }
                return;
            }
            if (editText.getInputType() == TYPE_NUMBER_FLAG_SIGNED && !ValidationTools.getInstance().isStringNumeric(content)) {
                foundErrors = true;
                view.setError(getString(R.string.you_need_to_enter_a_number));
            }
        }
    };

    public UpsertFragment() {
        // Required empty public constructor
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.upsert_list_menu, menu);
        MenuItem show = menu.findItem(R.id.show_advanced);
        MenuItem hide = menu.findItem(R.id.hide_advanced);
        show.setVisible(!viewAdvanced);
        hide.setVisible(viewAdvanced);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                foundErrors = false;
                ButterKnife.apply(getRequiredFieldsTextLayouts(), CHECK_FOR_ERRORS);
                if (!foundErrors) {
                    saveData();
                    popBackStack();
                }
                break;
            case R.id.show_advanced:
            case R.id.hide_advanced:
                onAdvancedClick();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public abstract List<TextInputLayout> getRequiredFieldsTextLayouts();

    protected abstract void saveData();

    @OnClick(R.id.button_advanced) protected void onAdvancedClick() {
        viewAdvanced = !viewAdvanced;
        buttonAdvanced.setText(viewAdvanced ? R.string.hide_advanced_settings : R.string.show_advanced_settings);
        setAdvancedVisibility();

    }

    protected void setAdvancedVisibility() {
        for (int i = 0; i < constraintLayout.getChildCount(); i++) {
            View view = constraintLayout.getChildAt(i);
            if (view.getTag() == null) {

                view.setVisibility(viewAdvanced ? View.VISIBLE : View.GONE);
            }
            //
        }
        ButterKnife.apply(hints, INVISIBLE);
        ButterKnife.apply(toggles, SET_STATE_AGAIN);

    }

    public abstract UpsertCounterListContract.UpsertCounterListPresenter getPresenter();

    protected void showPickColorDialog(View view) {
        ColorPickerDialogBuilder.with(getContext())
                .setTitle("Choose color")
                .initialColor(colorButtonMap.get(view))
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .showAlphaSlider(false)
                .density(12)
                .setOnColorSelectedListener(selectedColor -> {
                    //                        Toast.makeText(getContext(), "onColorSelected: 0x" + Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show();
                })
                .setPositiveButton("ok", (dialog, selectedColor, allColors) -> {
                    view.getBackground().setColorFilter(selectedColor, PorterDuff.Mode.MULTIPLY);
                    Integer selected = selectedColor;
                    colorButtonMap.put(view, selected);
                })
                .setNegativeButton("cancel", (dialog, which) -> {
                })
                .build()
                .show();
    }

    @NonNull protected UpsertCounterListModel.Identifier getIdentifier() {
        return new UpsertCounterListModel.Identifier.Builder().id(id).build();
    }

    protected void initComponents() {
        bindSwitchesToHints();
        setAdvancedVisibility();
        invalidateOptionsMenu();
        ButterKnife.apply(toggles, TEMPORARY_BROKEN_TOGGLE_FIX); //// FIXME: 2/24/2017 Check if fixed in library and remove this.

    }

    protected void bindSwitchesToHints() {
        List<Pair<TriStateToggleButton, TextView>> pairList = getSwitchHintPairs();
        for (Pair<TriStateToggleButton, TextView> triStateToggleButtonTextViewPair : pairList) {
            bindSwitchToHint(triStateToggleButtonTextViewPair.first, triStateToggleButtonTextViewPair.second);
        }
    }

    @NonNull private List<Pair<TriStateToggleButton, TextView>> getSwitchHintPairs() {
        List<Pair<TriStateToggleButton, TextView>> pairList = new ArrayList<>();
        pairList.add(new Pair<>(switchClickSound, switchClickSoundHint));
        pairList.add(new Pair<>(switchKeepAwake, switchKeepAwakeHint));
        pairList.add(new Pair<>(switchSpeechOutputName, switchSpeechOutputNameHint));
        pairList.add(new Pair<>(switchSpeechOutputValue, switchSpeechOutputValueHint));
        pairList.add(new Pair<>(switchVibrate, switchVibrateHint));
        pairList.add(new Pair<>(switchVolumeKey, switchVolumeKeyHint));
        return pairList;
    }

    private void bindSwitchToHint(TriStateToggleButton toggle, TextView hint) {
        toggle.setOnToggleChanged((toggleStatus, b, i) -> {
            switch (toggleStatus) {
                case mid:
                    hint.setText(R.string.on);
                    break;
                case off:
                    hint.setText(R.string.default_label);
                    break;
                case on:
                    hint.setText(R.string.off);
            }
            activateHintAnimation(hint);
        });
    }

    private void activateHintAnimation(TextView hint) {
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        hint.startAnimation(fadeIn);
        hint.startAnimation(fadeOut);
        fadeIn.setDuration(1000);
        fadeIn.setFillAfter(true);
        fadeOut.setDuration(1000);
        fadeOut.setFillAfter(true);
        fadeOut.setStartOffset(500 + fadeIn.getStartOffset());
    }

    protected DateFormat getDateFormat() {return android.text.format.DateFormat.getDateFormat(getContext());}

    protected Contracts.UpsertContract.UpsertModel getModelForPresenter(Contracts.UpsertContract.UpsertModel model) {
        model.setName(editTextName.getText().toString());
        model.setNote(editTextNote.getText().toString());
        model.setDefaultValue(editTextDefaultValue.getText().toString());
        model.setBackgroundColor(colorButtonMap.get(buttonBackground));
        model.setClickSound(getIntFromToggle(switchClickSound.getToggleStatus()));
        model.setVibrate(getIntFromToggle(switchVibrate.getToggleStatus()));
        model.setSpeakValue(getIntFromToggle(switchSpeechOutputValue.getToggleStatus()));
        model.setSpeakName(getIntFromToggle(switchSpeechOutputName.getToggleStatus()));
        model.setKeepAwake(getIntFromToggle(switchKeepAwake.getToggleStatus()));
        model.setUseVolume(getIntFromToggle(switchVolumeKey.getToggleStatus()));
        textViewDateCreatedStats.setText(model.getDateCreated());
        textViewDateModifiedStats.setText(model.getDateModified());
        textViewDateChangedStats.setText(model.getLastUsed());
        setTitle(model.getToolbarTitle());
        return model;
    }

    private int getIntFromToggle(TriStateToggleButton.ToggleStatus toggleStatus) {
        switch (toggleStatus) {
            case mid:
                return 1;
            case off:
                return 0;
        }
        return 2;
    }

    protected TriStateToggleButton.ToggleStatus getToggleStatus(int state) {
        switch (state) {
            case 1:
                return TriStateToggleButton.ToggleStatus.mid;
            case 0:
                return TriStateToggleButton.ToggleStatus.off;


        }
        return TriStateToggleButton.ToggleStatus.on;
    }
}
