package sillyv.com.counterlists.screens.counters.upsert;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.text.SimpleDateFormat;
import java.util.Locale;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import sillyv.com.counterlists.R;
import sillyv.com.counterlists.database.controllers.RealmRepository;
import sillyv.com.counterlists.database.models.CounterModel;
import sillyv.com.counterlists.database.models.ListModel;
import sillyv.com.counterlists.screens.Contracts;
import sillyv.com.counterlists.screens.ParentTest;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Vasili.Fedotov on 2/24/2017.
 *
 */
public class CounterUpsertPresenterTest
        extends ParentTest {
    private static final UpsertCounterModel.CounterModel MODEL = getNewCounterModel();
    private static final long PARENT_ID = 1L;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);
    private static final long EXISTING_ID = 1L;
    private final CounterModel NEW_COUNTER = getCounterModel(0);
    private final CounterModel EXISTING_COUNTER = getCounterModel(1);
    private final ListModel PARENT_LIST = getListModel(true);
    @Rule public MockitoRule rule = MockitoJUnit.rule();
    @Mock RealmRepository<CounterModel, Object> repo;
    @Mock RealmRepository<ListModel, CounterModel> parentRepo;
    @Mock UpsertCounterContract.UpsertCounterView<UpsertCounterModel.CounterModel> view;
    @Mock Context context;
    private UpsertCounterPresenter presenter;

    private static UpsertCounterModel.CounterModel getNewCounterModel() {
        Contracts.UpsertContract.UpsertModel model = new UpsertCounterModel.CounterModel(TEST_NAME,
                TEST_NOTE,
                VALUE,
                BACKGROUND_COLOR,
                CLICK_SOUND,
                VIBRATE,
                SPEAK_VALUE,
                SPEAK_NAME,
                KEEP_AWAKE,
                USE_VOLUME,
                null,
                null,
                null,
                null,
                INCREMENT,
                DECREMENT,
                FOREGROUND_COLOR,
                DEFAULT_VALUE);
        return (UpsertCounterModel.CounterModel) model;
    }

    @Before public void setUp() throws Exception {
        presenter = new UpsertCounterPresenter(view, repo, parentRepo, Schedulers.trampoline());
        when(context.getString(R.string.new_counter)).thenReturn(NEW_COUNTER_STRING);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());


    }

    @Test public void loadNewList() throws Exception {
        when(parentRepo.getItem(1)).thenReturn(Single.just(PARENT_LIST));

        presenter.loadNewList(context, DATE_FORMAT, 1L);

        verify(view, only()).onDataReceived(any(UpsertCounterModel.CounterModel.class));
    }

    @Test public void loadNewList_testForFields() throws Exception {
        when(parentRepo.getItem(1)).thenReturn(Single.just(PARENT_LIST));

        presenter.loadNewList(context, DATE_FORMAT, 1L);

        ArgumentCaptor<UpsertCounterModel.CounterModel> argument = ArgumentCaptor.forClass(UpsertCounterModel.CounterModel.class);
        verify(view).onDataReceived(argument.capture());
        assertEquals(argument.getValue().getToolbarTitle(), NEW_COUNTER_STRING);
        assertEquals(argument.getValue().getName(), null);
        assertEquals(argument.getValue().getNote(), null);
        assertEquals(argument.getValue().getDefaultValue(), LIST_DEFAULT_VALUE + "");
        assertEquals(argument.getValue().getValue(), LIST_DEFAULT_VALUE + "");
        assertEquals(argument.getValue().getIncrement(), LIST_DEFAULT_INCREMENT + "");
        assertEquals(argument.getValue().getDecrement(), LIST_DEFAULT_DECREMENT + "");
        assertEquals(argument.getValue().getBackgroundColor(), LIST_DEFAULT_CARD_BACKGROUND_COLOR);
        assertEquals(argument.getValue().getForegroundColor(), LIST_DEFAULT_CARD_FOREGROUND_COLOR);
        assertEquals(argument.getValue().getClickSound(), 0);
        assertEquals(argument.getValue().getVibrate(), 0);
        assertEquals(argument.getValue().getSpeakName(), 0);
        assertEquals(argument.getValue().getSpeakValue(), 0);
        assertEquals(argument.getValue().getKeepAwake(), 0);
        assertEquals(argument.getValue().getUseVolume(), 0);
        assertEquals(argument.getValue().getDateModified(), NEW_COUNTER_STRING);
        assertEquals(argument.getValue().getDateCreated(), NEW_COUNTER_STRING);
        assertEquals(argument.getValue().getLastUsed(), NEW_COUNTER_STRING);


    }

    @Test public void loadNewList_whenRepoThrowsException() throws Exception {
        when(parentRepo.getItem(1)).thenReturn(Single.error(new RuntimeException("test")));

        presenter.loadNewList(context, DATE_FORMAT, 1L);

        verify(view, only()).onGetDataErrorResponse();
    }


    @Test public void loadData_existingList() throws Exception {
        when(repo.getItem(1)).thenReturn(Single.just(EXISTING_COUNTER));
        when(parentRepo.getItem(2)).thenReturn(Single.just(PARENT_LIST));

        presenter.loadData(context, DATE_FORMAT, 1L, 2L);

        verify(view).onDataReceived(any(UpsertCounterModel.CounterModel.class));
    }

    @Test public void loadData_whenRepoThrowsException() throws Exception {
        when(repo.getItem(1)).thenReturn(Single.just(EXISTING_COUNTER));
        when(parentRepo.getItem(2)).thenReturn(Single.error(new RuntimeException("test")));
        //when
        presenter.loadData(context, DATE_FORMAT, 2L, 2L);
        //then
        verify(view, only()).onGetDataErrorResponse();

    }


    @Test public void loadData_existingList_testForFields() throws Exception {
        //        when(repo.getItem(1)).thenReturn(Single.just(EXISTING_COUNTER));
        when(parentRepo.getItem(2)).thenReturn(Single.just(PARENT_LIST));

        presenter.loadData(context, DATE_FORMAT, 4L, 2L);

        ArgumentCaptor<UpsertCounterModel.CounterModel> argument = ArgumentCaptor.forClass(UpsertCounterModel.CounterModel.class);
        verify(view).onDataReceived(argument.capture());

        CounterModel counterModel = PARENT_LIST.getCounters().get(3);
        UpsertCounterModel.CounterModel value = argument.getValue();
        assertEquals(value.getDateCreated(), DATE_FORMAT.format(counterModel.getCreated()));
        assertEquals(value.getDateModified(), DATE_FORMAT.format(counterModel.getEdited()));
        assertEquals(value.getLastUsed(), DATE_FORMAT.format(counterModel.getValueChanged()));
        assertEquals(value.getNote(), counterModel.getNote());
        assertEquals(value.getName(), counterModel.getName());
        assertEquals(value.getVibrate(), counterModel.getVibrate());
        assertEquals(value.getSpeakName(), counterModel.getSpeechOutputName());
        assertEquals(value.getSpeakValue(), counterModel.getSpeechOutputValue());
        assertEquals(value.getClickSound(), counterModel.getClickSound());
        assertEquals(value.getKeepAwake(), counterModel.getKeepAwake());
        assertEquals(value.getUseVolume(), counterModel.getVolumeKey());
        assertEquals(value.getValue(), String.valueOf(counterModel.getValue()));
        assertEquals(value.getDefaultValue(), String.valueOf(counterModel.getDefaultValue()));
        assertEquals(value.getIncrement(), String.valueOf(counterModel.getIncrement()));
        assertEquals(value.getDecrement(), String.valueOf(counterModel.getDecrement()));

        assertEquals(value.getBackgroundColor(), counterModel.getForeground());
/*
    private long id;
    private int customOrder;*/
    }

    @Test public void saveData_newItem() throws Exception {
        when(parentRepo.insertNewChildItem(anyLong(), any())).thenReturn(Completable.complete());

        presenter.saveData(MODEL, 0L, PARENT_ID);
        //then
        verify(parentRepo).insertNewChildItem(anyLong(), any(CounterModel.class));
    }

    @Test public void saveData_newItem_testForFields() throws Exception {
        when(parentRepo.insertNewChildItem(anyLong(), any())).thenReturn(Completable.complete());

        presenter.saveData(MODEL, 0L, PARENT_ID);
        //then
        ArgumentCaptor<Long> longArgument = ArgumentCaptor.forClass(Long.class);

        ArgumentCaptor<CounterModel> argument = ArgumentCaptor.forClass(CounterModel.class);

        verify(parentRepo).insertNewChildItem(longArgument.capture(), argument.capture());
        assertEquals(longArgument.getValue().longValue(), PARENT_ID);
        assertEquals(argument.getValue().getNote(), TEST_NOTE);
        assertEquals(String.valueOf(argument.getValue().getValue()), VALUE);
        assertEquals(argument.getValue().getBackground(), BACKGROUND_COLOR);
        assertEquals(argument.getValue().getClickSound(), CLICK_SOUND);
        assertEquals(argument.getValue().getVibrate(), VIBRATE);
        assertEquals(argument.getValue().getSpeechOutputValue(), SPEAK_VALUE);
        assertEquals(argument.getValue().getSpeechOutputName(), SPEAK_NAME);
        assertEquals(argument.getValue().getKeepAwake(), KEEP_AWAKE);
        assertEquals(argument.getValue().getVolumeKey(), USE_VOLUME);
        assertEquals(String.valueOf(argument.getValue().getIncrement()), INCREMENT);
        assertEquals(String.valueOf(argument.getValue().getDecrement()), DECREMENT);
        assertEquals(argument.getValue().getForeground(), FOREGROUND_COLOR);
        assertEquals(String.valueOf(argument.getValue().getDefaultValue()), DEFAULT_VALUE);
    }

    @Test public void saveData_existingItem() throws Exception {
        when(repo.updateItem(any())).thenReturn(Completable.complete());

        presenter.saveData(MODEL, EXISTING_ID, PARENT_ID);
        //then
        verify(repo).updateItem(any(CounterModel.class));
        verify(view, only()).onSaveDataSuccess();

    }

    @Test public void saveData_existingItem_testForFields() throws Exception {
        when(repo.updateItem(any())).thenReturn(Completable.complete());


        presenter.saveData(MODEL, EXISTING_ID, PARENT_ID);
        //then
        ArgumentCaptor<CounterModel> argument = ArgumentCaptor.forClass(CounterModel.class);

        verify(repo).updateItem(argument.capture());
        assertEquals(argument.getValue().getId(), EXISTING_ID);
        assertEquals(argument.getValue().getNote(), TEST_NOTE);
        assertEquals(String.valueOf(argument.getValue().getValue()), VALUE);
        assertEquals(argument.getValue().getBackground(), BACKGROUND_COLOR);
        assertEquals(argument.getValue().getClickSound(), CLICK_SOUND);
        assertEquals(argument.getValue().getVibrate(), VIBRATE);
        assertEquals(argument.getValue().getSpeechOutputValue(), SPEAK_VALUE);
        assertEquals(argument.getValue().getSpeechOutputName(), SPEAK_NAME);
        assertEquals(argument.getValue().getKeepAwake(), KEEP_AWAKE);
        assertEquals(argument.getValue().getVolumeKey(), USE_VOLUME);
        assertEquals(String.valueOf(argument.getValue().getIncrement()), INCREMENT);
        assertEquals(String.valueOf(argument.getValue().getDecrement()), DECREMENT);
        assertEquals(argument.getValue().getForeground(), FOREGROUND_COLOR);
        assertEquals(String.valueOf(argument.getValue().getDefaultValue()), DEFAULT_VALUE);

    }

    @Test public void saveData_existingItem_whenRepoThrowsException() throws Exception {
        when(repo.updateItem(any())).thenReturn(Completable.error(new RuntimeException("Test")));

        presenter.saveData(MODEL, EXISTING_ID, PARENT_ID);

        verify(view, only()).onSaveDataErrorResponse();
    }

    @After public void tearDown() throws Exception {
        RxJavaPlugins.reset();

    }

    /*







*/

}