package sillyv.com.counterlists;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import sillyv.com.counterlists.baseline.CLFragment;
import sillyv.com.counterlists.database.controllers.ListController;
import sillyv.com.counterlists.database.models.ListModel;
import sillyv.com.counterlists.events.AddFragmentEvent;
import sillyv.com.counterlists.events.BackPressedEvent;
import sillyv.com.counterlists.events.KeepAwakeConfigurationEvent;
import sillyv.com.counterlists.events.MenuChangedEvent;
import sillyv.com.counterlists.events.ToolbarTitleChangedEvent;
import sillyv.com.counterlists.screens.lists.recycler.CountersListsFragment;

public class MainActivity
        extends AppCompatActivity
        implements CLFragment.ListenerActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Subscribe public void addFragment(AddFragmentEvent event) {
        addFragment(event.getFragment());
    }

    @Subscribe private void addFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_container, fragment, fragment.getClass().getName());
        ft.addToBackStack(fragment.getClass().getName());
        ft.commit();
    }

    @Subscribe public void popBackstack(BackPressedEvent event) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
        fm.executePendingTransactions();
        CLFragment activeFragment = getActiveFragment();
        if (activeFragment != null) {
            invalidateOptionsMenu();
            activeFragment.setTitle();
        }
    }

    private CLFragment getActiveFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        return (CLFragment) getSupportFragmentManager().findFragmentByTag(tag);
    }

    @Subscribe public void onMenuChanged(MenuChangedEvent event) {
        invalidateOptionsMenu();
    }

    @Subscribe public void onToolbarTitleChanged(ToolbarTitleChangedEvent event) {
        CLFragment activeFragment = getActiveFragment();
        if (activeFragment != null) {
            activeFragment.setTitle();
        }
    }

    @Override public void onBackPressed() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        CLFragment activeFragment = getActiveFragment();
        if (activeFragment != null) {
            if (activeFragment instanceof CountersListsFragment) {
                finish();
                return;
            }
        }
        super.onBackPressed();
        invalidateOptionsMenu();
    }

    @Subscribe public void setKeepAwakeMode(KeepAwakeConfigurationEvent event) {
        if (event.isKeepAwake()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            CLFragment activeFragment = getActiveFragment();
            if (activeFragment != null) {
                return activeFragment.volumePressed(keyCode, event);
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        CLFragment activeFragment = getActiveFragment();
        if (activeFragment != null) {
            activeFragment.onCreateOptionsMenu(menu, inflater);
        }
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        CLFragment activeFragment = getActiveFragment();
        if (activeFragment != null) {
            activeFragment.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        addFragment(new CountersListsFragment());
        setTitle("Counter List");
        if (Realm.getDefaultInstance().isEmpty()) {
            generateInitialData();
        }
    }

    @Override protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void setTitle(String title) {
        ((TextView) toolbar.findViewById(R.id.title_text_view)).setText(title);
    }

    private void generateInitialData() {
        ListController.getInstance().addNewList(new ListModel(0,
                1,
                1,
                getIntFromColor(1, 1, 1),
                getIntFromColor(.75f, .75f, .75f),
                getIntFromColor(0, 0, 0),
                "",
                0,
                0,
                0,
                0,
                0,
                0,
                ""));
    }

    private int getIntFromColor(float Red, float Green, float Blue) {
        int R = Math.round(255 * Red);
        int G = Math.round(255 * Green);
        int B = Math.round(255 * Blue);
        R = (R << 16) & 0x00FF0000;
        G = (G << 8) & 0x0000FF00;
        B = B & 0x000000FF;
        return 0xFF000000 | R | G | B;
    }

    @Override public void popBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
        invalidateOptionsMenu();
        CLFragment activeFragment = getActiveFragment();
        if (activeFragment != null) {
            activeFragment.setTitle();
        }

    }
}
