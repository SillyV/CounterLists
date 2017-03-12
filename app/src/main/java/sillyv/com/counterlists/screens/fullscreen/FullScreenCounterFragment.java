package sillyv.com.counterlists.screens.fullscreen;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.baseline.BasePresenter;
import sillyv.com.counterlists.baseline.CLFragment;
import sillyv.com.counterlists.database.controllers.CounterController;
import sillyv.com.counterlists.database.controllers.ListController;
import sillyv.com.counterlists.events.AddFragmentEvent;
import sillyv.com.counterlists.events.BackPressedEvent;
import sillyv.com.counterlists.events.KeepAwakeConfigurationEvent;
import sillyv.com.counterlists.screens.counters.upsert.UpsertCounterFragment;
import sillyv.com.counterlists.tools.CLStringUtils;

public class FullScreenCounterFragment
        extends CLFragment
        implements FullScreenCounterContract.fullScreenView {

    private final static String EXTRA_ID = "EXTRA_ID";
    private final static String EXTRA_PARENT_ID = "EXTRA_PRENT_ID";

    @BindView(R.id.parent_constraint_layout) ConstraintLayout parent;
    @BindView(R.id.counter) TextView counter;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.subtitle) TextView subtitle;
    @BindView(R.id.value_changed) TextView valueChanged;
    @BindView(R.id.current_session) TextView currentSession;
    @BindView(R.id.date_modified) TextView dateModified;
    @BindView(R.id.date_created) TextView dateCreated;
    @BindView(R.id.fab) FloatingActionButton fab;
    //    @BindView(R.id.minus_one) View minusOneOnFab;

    @BindViews({R.id.counter,
            R.id.title,
            R.id.subtitle,
            R.id.value_changed,
            R.id.current_session,
            R.id.date_modified,
            R.id.date_created,
            R.id.date_created_label,
            R.id.current_session_label,
            R.id.date_modified_label,
            R.id.value_changed_label}) List<TextView> textViews;
    Handler handler = new Handler();
    private FullScreenCounterPresenter presenter;
    private long parentId;
    private long id;
    private FullScreenCounterModel.CounterModel data;
    private Date sessionStart;
    Runnable runnable = this::updateClocks;
    private TextToSpeech tts;

    public FullScreenCounterFragment() {
        // Required empty public constructor
    }

    public static FullScreenCounterFragment newInstance(long id, long parentID) {
        FullScreenCounterFragment fragment = new FullScreenCounterFragment();
        Bundle args = new Bundle();
        args.putLong(EXTRA_ID, id);
        args.putLong(EXTRA_PARENT_ID, parentID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong(EXTRA_ID);
            parentId = getArguments().getLong(EXTRA_PARENT_ID);
        }

    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_full_screen_counter, container, false);
        ButterKnife.bind(this, view);
        presenter = new FullScreenCounterPresenter(this,
                CounterController.getInstance(),
                ListController.getInstance(),
                AndroidSchedulers.mainThread());
        presenter.getData(id, parentId);
        sessionStart = new Date();
        invalidateOptionsMenu();
        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.full_screen_menu, menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                startEditing();
                return true;
            case R.id.delete:
                createAndShowAlertDialogForDeleting();
                return true;
            case R.id.restart:
                resetItems();
                return true;

        }
        return false;
    }

    private void startEditing() {
        EventBus.getDefault().post(new AddFragmentEvent(UpsertCounterFragment.newInstance(id, parentId)));
    }

    private void createAndShowAlertDialogForDeleting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(CLStringUtils.getDeleteMessage(getContext(), data.getName(), 1));
        builder.setMessage(R.string.consider);
        builder.setPositiveButton(android.R.string.yes, (dialog, id) -> {
            presenter.deleteCounter(this.id);
            dialog.dismiss();
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    private void resetItems() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(CLStringUtils.getResetMessage(getContext(), data.getName(), 1, true));
        builder.setMessage(R.string.consider);
        builder.setPositiveButton(android.R.string.yes, (dialog, id) -> {
            presenter.resetItem(this.id);

            dialog.dismiss();
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override public void onDataReceived(FullScreenCounterModel.CounterModel data) {
        this.data = data;
        title.setText(this.data.getName());
        subtitle.setText(this.data.getNote());
        setValue();
        //        setTimeToTextViews();
        setColors();
        setSounds();
        runnable.run();
        setKeepAwake();


    }

    private void setValue() {
        counter.setText(String.valueOf(this.data.getValue()));
        counter.requestLayout();
    }

    private void setColors() {
        parent.setBackgroundColor(data.getBackground());
        for (TextView textView : textViews) {
            textView.setTextColor(data.getForeground());
        }
    }

    private void setSounds() {
        counter.setSoundEffectsEnabled(data.isClickSound());
        fab.setSoundEffectsEnabled(data.isClickSound());
    }

    private void setKeepAwake() {
        EventBus.getDefault().post(new KeepAwakeConfigurationEvent(data.isKeepAwake()));
    }

    @Override public void onGetDataErrorResponse() {
        EventBus.getDefault().post(new BackPressedEvent());
    }

    @Override public void onSaveInteractionSuccess() {

    }

    @Override public void onSaveInteractionError() {
        presenter.getData(id, parentId);
        Toast.makeText(getContext(), "Something went wrong, make sure your data is correct", Toast.LENGTH_SHORT).show();

    }

    @Override public void onResetSuccess() {
        data.setValue(data.getDefaultValue());
        setValue();
    }

    @Override public void onResetError() {
        Toast.makeText(getContext(), "Error resetting counter", Toast.LENGTH_SHORT).show();
    }

    @Override public void onDeleteItemSuccess() {
        EventBus.getDefault().post(new BackPressedEvent());
    }

    @Override public void onDeleteItemError() {
        Toast.makeText(getContext(), "Error deleting counter", Toast.LENGTH_SHORT).show();

    }

    public void updateClocks() {
        setTimeToTextViews();
        handler.postDelayed(runnable, 500);
    }

    private void setTimeToTextViews() {
        dateCreated.setText(CLStringUtils.getDifferenceBetweenDates(data.getDateCreated(), new Date(), true));
        dateModified.setText(CLStringUtils.getDifferenceBetweenDates(data.getDateModified(), new Date(), true));
        valueChanged.setText(CLStringUtils.getDifferenceBetweenDates(data.getLastUsed(), new Date(), true));
        currentSession.setText(CLStringUtils.getDifferenceBetweenDates(sessionStart, new Date(), false));
    }

    @Override public boolean volumePressed(int keyCode, KeyEvent event) {

        if (data.isUseVolume()) {

            if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                onIncrement();
            } else {
                onDecrement();
            }
            return true;
        }
return false;

    }

    @Override protected List<BasePresenter> getPresenters() {
        return Collections.emptyList();
    }

    @Override public void setTitle() {

    }

    private void vibrate() {
        if (data.isVibrate()) {
            Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);
        }
    }

    private void speak() {
        tts = new TextToSpeech(getContext().getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.getDefault());
                tts.setPitch(1.3f);
                tts.setSpeechRate(.8f);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tts.speak(getSpeechString(), TextToSpeech.QUEUE_FLUSH, null, null);
                } else {
                    tts.speak(getSpeechString(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });


    }

    @NonNull private String getSpeechString() {
        String response = "";

        if (data.isSpeakName()) {
            response += data.getName();
        }
        if (data.isSpeakValue()) {
            response += " " + data.getValue();
        }
        return response;
    }

    @OnClick(R.id.counter) void onIncrement() {
        data.setValue(data.getValue() + data.getIncrement());
        setValue();
        presenter.saveInteraction(id, parentId, data.getValue());
        vibrate();
        speak();
    }

    @OnClick(R.id.fab) void onDecrement() {
        data.setValue(data.getValue() - data.getDecrement());
        setValue();
        presenter.saveInteraction(id, parentId, data.getValue());
        vibrate();
        speak();
    }

}
