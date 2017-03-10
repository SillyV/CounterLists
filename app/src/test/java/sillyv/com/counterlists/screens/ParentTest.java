package sillyv.com.counterlists.screens;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sillyv.com.counterlists.database.models.CounterModel;
import sillyv.com.counterlists.database.models.ListModel;
import sillyv.com.counterlists.screens.lists.upsert.UpsertCounterListModel;

/**
 * Created by Vasili.Fedotov on 2/24/2017.
 */

public abstract class ParentTest {


    public static final int LIST_ID = 2;
    public static final int LIST_DEFAULT_VALUE = 3;
    public static final int LIST_DEFAULT_INCREMENT = 5;
    public static final int LIST_DEFAULT_DECREMENT = 7;
    public static final int LIST_BACKGROUND = 11;
    public static final int LIST_DEFAULT_CARD_BACKGROUND_COLOR = 13;
    public static final int LIST_DEFAULT_CARD_FOREGROUND_COLOR = 17;
    public static final int LIST_CLICK_SOUND = 0;
    public static final int LIST_VIBRATE = 1;
    public static final int LIST_SPEECH_OUTPUT_VALUE = 2;
    public static final int LIST_SPEECH_OUTPUT_NAME = 0;
    public static final int LIST_KEEP_AWAKE = 1;
    public static final int LIST_VOLUME_KEY = 2;
    public static final String NEW_COUNTER_STRING = "New counter";
    public static final String TEST_NAME = "test name";
    public static final String TEST_NOTE = "test note";
    public static final String VALUE = "5";
    public static final int BACKGROUND_COLOR = 1;
    public static final int CLICK_SOUND = 2;
    public static final int VIBRATE = 3;
    public static final int SPEAK_VALUE = 4;
    public static final int SPEAK_NAME = 5;
    public static final int KEEP_AWAKE = 6;
    public static final int USE_VOLUME = 7;
    public static final String INCREMENT = "27";
    public static final String DECREMENT = "19";
    public static final int FOREGROUND_COLOR = 666;
    public static final String DEFAULT_VALUE = "905";
    protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);
    private static final String LIST_NAME = "Happy Name";
    private static final String LIST_NOTE = "Happy Note";
    private static final int ITEM_WITH_HEFTY_SUBTITLE = 3;
    protected final List<ListModel> MANY_ITEMS = getListModels(ITEM_WITH_HEFTY_SUBTITLE);
    protected List<Long> ID_LIST = getIdList();
    private Date dateCreated;


    private List<ListModel> getListModels(int itemType) {
        List<ListModel> response = new ArrayList<>();

        int items = 4;
        if (itemType == ITEM_WITH_HEFTY_SUBTITLE) {
            ListModel a = new ListModel(20, 4, 2, 8, 100, 200, 300, "A happy note for testing", 0, 1, 2, 0, 1, 2, "Super Test Name");
            ArrayList<CounterModel> aaabbbcccdddCounters = new ArrayList<>();
            CounterModel e1 = new CounterModel();
            e1.setName("AAA");
            CounterModel e2 = new CounterModel();
            e2.setName("BBB");
            CounterModel e3 = new CounterModel();
            e3.setName("CCC");
            CounterModel e4 = new CounterModel();
            e4.setName("DDD");
            aaabbbcccdddCounters.add(e1);
            aaabbbcccdddCounters.add(e2);
            aaabbbcccdddCounters.add(e3);
            aaabbbcccdddCounters.add(e4);
            a.setCounters(aaabbbcccdddCounters);
            response.add(a);

            ListModel b = new ListModel(1, 0, 0, 0, 0, 0, 0, "", 0, 1, 2, 0, 1, 2, "");
            ArrayList<CounterModel> aaaounters = new ArrayList<>();
            aaaounters.add(e1);
            b.setCounters(aaaounters);
            response.add(b);


            ListModel c = new ListModel(2, 0, 0, 0, 0, 0, 0, "", 0, 1, 2, 0, 1, 2, "");
            c.setCounters(Collections.emptyList());
            response.add(c);


            items += 3;
        }

        for (int i = itemType; i < items; i++) {
            ListModel e = new ListModel(i, 0, 0, 0, 0, 0, 0, "", 0, 1, 2, 0, 1, 2, "");
            ArrayList<CounterModel> aaabbbcccdddCounters = new ArrayList<>();
            CounterModel e1 = new CounterModel();
            e1.setName("AAA");
            CounterModel e2 = new CounterModel();
            e2.setName("BBB");
            CounterModel e3 = new CounterModel();
            e3.setName("CCC");
            CounterModel e4 = new CounterModel();
            e4.setName("DDD");
            aaabbbcccdddCounters.add(e1);
            aaabbbcccdddCounters.add(e2);
            aaabbbcccdddCounters.add(e3);
            aaabbbcccdddCounters.add(e4);
            e.setCounters(aaabbbcccdddCounters);
            response.add(e);
        }

        return response;
    }

    @NonNull private List<Long> getIdList() {
        List<Long> items = new ArrayList<>();
        items.add(3L);
        items.add(5L);
        items.add(5L);
        items.add(7L);
        items.add(5L);
        return items;
    }

    @NonNull protected UpsertCounterListModel.CounterListSettings getModel() {
        return new UpsertCounterListModel.CounterListSettings("test",
                "test note",
                "1",
                100,
                0,
                1,
                1,
                0,
                2,
                2,
                null,
                "AS",
                "null",
                "test title",
                "2",
                "3",
                300,
                400);
    }

    protected Date getDateCreated() {return dateCreated;}

    protected ListModel getListModel(boolean addCounters) {
        ListModel e = new ListModel(LIST_ID,
                LIST_DEFAULT_VALUE,
                LIST_DEFAULT_INCREMENT,
                LIST_DEFAULT_DECREMENT,
                LIST_BACKGROUND,
                LIST_DEFAULT_CARD_BACKGROUND_COLOR,
                LIST_DEFAULT_CARD_FOREGROUND_COLOR,
                LIST_NOTE,
                LIST_CLICK_SOUND,
                LIST_VIBRATE,
                LIST_SPEECH_OUTPUT_VALUE,
                LIST_SPEECH_OUTPUT_NAME,
                LIST_KEEP_AWAKE,
                LIST_VOLUME_KEY,
                LIST_NAME);


        dateCreated = e.getDateCreated();

        if (addCounters) {
            ArrayList<CounterModel> counters = new ArrayList<>();
            CounterModel e1 = new CounterModel(1,
                    new Date(),
                    new Date(),
                    new Date(),
                    "test Note",
                    0,
                    1,
                    2,
                    0,
                    1,
                    2,
                    "Test Name 1",
                    0,
                    0,
                    1,
                    1,
                    0,
                    0,
                    1);

            CounterModel e2 = new CounterModel(2,
                    new Date(),
                    new Date(),
                    new Date(),
                    "test Note",
                    0,
                    1,
                    2,
                    0,
                    1,
                    2,
                    "Test Name 2",
                    0,
                    0,
                    1,
                    1,
                    0,
                    0,
                    1);
            e2.setName("BBB");
            CounterModel e3 = new CounterModel(3,
                    new Date(),
                    new Date(),
                    new Date(),
                    "test Note",
                    0,
                    1,
                    2,
                    0,
                    1,
                    2,
                    "Test Name 3",
                    0,
                    0,
                    1,
                    1,
                    0,
                    0,
                    3);
            e3.setName("CCC");
            CounterModel e4 = new CounterModel(4,
                    new Date(),
                    new Date(),
                    new Date(),
                    "test Note",
                    0,
                    1,
                    2,
                    0,
                    1,
                    2,
                    "Test Name 4",
                    0,
                    0,
                    1,
                    1,
                    0,
                    0,
                    4);
            e4.setName("DDD");
            counters.add(e1);
            counters.add(e2);
            counters.add(e3);
            counters.add(e4);
            e.setCounters(counters);
        }
        return e;
    }

    protected ListModel getParentList() {
        return new ListModel(1, 10, 20, 30, 40, 50, 60, "test note", 0, 1, 2, 0, 1, 2, "test name");
    }

    protected CounterModel getCounterModel(int id) {
        return new CounterModel(id, new Date(), new Date(), new Date(), "test note", 0, 1, 0, 1, 2, 2, "test name", 4, 8, 16, 32, 64, 128, 7000);
    }
}
