package sillyv.com.counterlists.screens.counters.upsert;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.database.controllers.CounterController;
import sillyv.com.counterlists.database.controllers.ListController;
import sillyv.com.counterlists.screens.Contracts;
import sillyv.com.counterlists.screens.UpsertFragment;
import sillyv.com.counterlists.screens.lists.upsert.UpsertCounterListContract;

import static sillyv.com.counterlists.database.controllers.CounterController.DEFAULT_COUNTER;

public class UpsertCounterFragment
        extends UpsertFragment
        implements UpsertCounterContract.UpsertCounterView<UpsertCounterModel.CounterModel> {


    public static final String COUNTER_IDENTIFIER = "COUNTER_IDENTIFIER";
    UpsertCounterContract.UpsertCounterPresenter presenter;
    @BindViews({R.id.text_input_layout_decrement,
            R.id.text_input_layout_increment,
            R.id.text_input_layout_default_value,
            R.id.text_input_layout_name}) List<TextInputLayout> requiredFieldsTextLayouts;
    @BindView(R.id.edit_text_increment) TextView editTextIncrement;
    @BindView(R.id.edit_text_decrement) TextView editTextDecrement;
    @BindView(R.id.button_foreground) View buttonForeground;
    @BindView(R.id.edit_text_value) TextView editTextValue;
    private boolean viewAdvanced = false;
    private long parentId;

    public UpsertCounterFragment() {
        // Required empty public constructor
    }

    public static UpsertCounterFragment newInstance(long parentId) {
        UpsertCounterFragment fragment = new UpsertCounterFragment();
        Bundle args = new Bundle();
        args.putLong(LIST_IDENTIFIER, parentId);
        args.putLong(COUNTER_IDENTIFIER, DEFAULT_COUNTER);
        fragment.setArguments(args);
        return fragment;
    }

    public static UpsertCounterFragment newInstance(long id, long parentId) {
        UpsertCounterFragment fragment = new UpsertCounterFragment();
        Bundle args = new Bundle();
        args.putLong(LIST_IDENTIFIER, parentId);
        args.putLong(COUNTER_IDENTIFIER, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong(COUNTER_IDENTIFIER);
            parentId = getArguments().getLong(LIST_IDENTIFIER);
        }
    }

    @Override public List<TextInputLayout> getRequiredFieldsTextLayouts() {
        return requiredFieldsTextLayouts;
    }

    @Override protected void saveData() {
        presenter.saveData((UpsertCounterModel.CounterModel) getModelForPresenter(), id, parentId);
    }

    protected Contracts.UpsertContract.UpsertModel getModelForPresenter() {

        return super.getModelForPresenter(new UpsertCounterModel.CounterModel(editTextValue.getText().toString(),
                editTextIncrement.getText().toString(),
                editTextDecrement.getText().toString(),
                (Integer) colorButtonMap.get(buttonForeground)));
    }

    @Override public UpsertCounterListContract.UpsertCounterListPresenter getPresenter() {
        return null; //// TODO: 2/24/2017 IMPLEMENT
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upsert_counter, container, false);
        ButterKnife.bind(this, view);
        presenter = new UpsertCounterPresenter(this, CounterController.getInstance(), ListController.getInstance());
        presenter.loadData(getContext(), getDateFormat(), id, parentId);
        initComponents();
        return view;
    }

    @Override public void setTitle() {

    }

    @Override public void onDataReceived(UpsertCounterModel.CounterModel model) {
        HashMap<View, Integer> colorButtonMap = new HashMap<>();
        editTextName.setText(model.getName());
        editTextNote.setText(model.getNote());
        editTextDefaultValue.setText(model.getDefaultValue());
        editTextValue.setText(model.getValue());
        editTextIncrement.setText(model.getIncrement());
        editTextDecrement.setText(model.getDecrement());
        Integer backgroundColor = model.getBackgroundColor();
        buttonBackground.getBackground().setColorFilter(backgroundColor, PorterDuff.Mode.MULTIPLY);
        Integer foregroundColor = model.getForegroundColor();
        buttonForeground.getBackground().setColorFilter(foregroundColor, PorterDuff.Mode.MULTIPLY);
        colorButtonMap.put(buttonBackground, backgroundColor);
        colorButtonMap.put(buttonForeground, foregroundColor);
        switchClickSound.setToggleStatus(getToggleStatus(model.getClickSound()), true);
        switchVibrate.setToggleStatus(getToggleStatus(model.getVibrate()), true);
        switchSpeechOutputValue.setToggleStatus(getToggleStatus(model.getSpeakValue()), true);
        switchSpeechOutputName.setToggleStatus(getToggleStatus(model.getSpeakName()), true);
        switchKeepAwake.setToggleStatus(getToggleStatus(model.getKeepAwake()), true);
        switchVolumeKey.setToggleStatus(getToggleStatus(model.getUseVolume()), true);
        textViewDateCreatedStats.setText(model.getDateCreated());
        textViewDateModifiedStats.setText(model.getDateModified());
        textViewDateChangedStats.setText(model.getLastUsed());
        toolbarTitle = model.getToolbarTitle();
        setTitle(toolbarTitle);
        this.colorButtonMap = colorButtonMap;
    }

    @Override public void onGetDataErrorResponse() {

    }

    @Override public void onDeleteBooksErrorResponse() {

    }

    @Override public void onSaveDataErrorResponse() {

    }

    @Override public void onSaveDataSuccess() {

    }

    @OnClick({R.id.button_background, R.id.button_foreground}) void onButtonColorClick(final View view) {
        showPickColorDialog(view);
    }
}
