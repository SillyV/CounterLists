package sillyv.com.counterlists.screens.lists.upsert;

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
import io.reactivex.android.schedulers.AndroidSchedulers;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.database.controllers.ListController;
import sillyv.com.counterlists.screens.Contracts;
import sillyv.com.counterlists.screens.UpsertFragment;

public class UpsertCounterListFragment
        extends UpsertFragment
        implements UpsertCounterListContract.UpsertCounterListView<UpsertCounterListModel.CounterListSettings> {


    @BindView(R.id.edit_text_default_increment) TextView editTextDefaultIncrement;
    @BindView(R.id.edit_text_default_decrement) TextView editTextDefaultDecrement;
    @BindView(R.id.button_default_card_background) View buttonDefaultCardBackground;
    @BindView(R.id.button_default_card_foreground) View buttonDefaultCardForeground;
    @BindViews({R.id.text_input_layout_default_decrement,
            R.id.text_input_layout_default_increment,
            R.id.text_input_layout_default_value,
            R.id.text_input_layout_name}) List<TextInputLayout> requiredFieldsTextLayouts;

    private UpsertCounterListContract.UpsertCounterListPresenter presenter;

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

    @Override protected void saveData() {
        {presenter.saveData((UpsertCounterListModel.CounterListSettings) getModelForPresenter(), getIdentifier());}
    }

    @Override public List<TextInputLayout> getRequiredFieldsTextLayouts() {
        return requiredFieldsTextLayouts;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong(LIST_IDENTIFIER);
        }


    }

    @Override public UpsertCounterListContract.UpsertCounterListPresenter getPresenter() {
        return presenter;
    }


    //usage

    protected Contracts.UpsertContract.UpsertModel getModelForPresenter() {
        return super.getModelForPresenter(new UpsertCounterListModel.CounterListSettings(
                editTextDefaultIncrement.getText().toString(),
                editTextDefaultDecrement.getText().toString(),
                (Integer) colorButtonMap.get(buttonDefaultCardBackground),
                (Integer) colorButtonMap.get(buttonDefaultCardForeground)));
    }

    @OnClick({R.id.button_background, R.id.button_default_card_background, R.id.button_default_card_foreground})
    void onButtonColorClick(final View view) {
        showPickColorDialog(view);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_counter_list, container, false);
        ButterKnife.bind(this, view);
        presenter = new UpsertCounterListPresenter(this, ListController.getInstance(), AndroidSchedulers.mainThread());
        presenter.loadData(getContext(), getDateFormat(), getIdentifier());
        initComponents();
        return view;
    }

    @Override public void onDataReceived(UpsertCounterListModel.CounterListSettings model) {
        HashMap<View, Integer> colorButtonMap = new HashMap<>();
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

    @Override public void onDeleteItemsErrorResponse() {

    }

    @Override public void onSaveDataErrorResponse() {

    }

    @Override public void onSaveDataSuccess() {

    }

    @Override public void setTitle() {
        setTitle(toolbarTitle);
    }
}
