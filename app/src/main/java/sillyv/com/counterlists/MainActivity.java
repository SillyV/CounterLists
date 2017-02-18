package sillyv.com.counterlists;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import sillyv.com.counterlists.database.controllers.ListController;
import sillyv.com.counterlists.database.models.ListModel;
import sillyv.com.counterlists.events.AddFragmentEvent;
import sillyv.com.counterlists.screens.lists.recycler.CountersListsFragment;
import sillyv.com.counterlists.screens.lists.upsert.UpsertCounterListFragment;

public class MainActivity extends AppCompatActivity implements CLFragment.ListenerActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private int currentMenu = R.menu.upsert_list_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        addFragment(new CountersListsFragment());
        setTitle("Counter List");
        if (Realm.getDefaultInstance().isEmpty()) {
            generateInitialData();
        }
    }

    private void generateInitialData() {
        ListController.getInstance().addNewList(new ListModel(0, 1, 1, getIntFromColor(1, 1, 1), getIntFromColor(.75f, .75f, .75f), getIntFromColor(0, 0, 0), "", true, false, false, false, false, false, ""), 0);
    }

    public int getIntFromColor(float Red, float Green, float Blue) {
        int R = Math.round(255 * Red);
        int G = Math.round(255 * Green);
        int B = Math.round(255 * Blue);
        R = (R << 16) & 0x00FF0000;
        G = (G << 8) & 0x0000FF00;
        B = B & 0x000000FF;
        return 0xFF000000 | R | G | B;
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void addFragment(AddFragmentEvent event) {
        addFragment(event.getFragment());
    }


    @Subscribe
    private void addFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_container, fragment, fragment.getClass().getName());
        ft.addToBackStack(fragment.getClass().getName());
        ft.commit();
    }

    public void setTitle(String title) {
        ((TextView) toolbar.findViewById(R.id.title_text_view)).setText(title);
    }

    public void setMenu(int menu) {
        currentMenu = menu;
        invalidateOptionsMenu();
    }

    @Override
    public void popBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
        invalidateOptionsMenu();
        getActiveFragment().setTitle();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getActiveFragment().onCreateOptionsMenu(menu, inflater);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        getActiveFragment().onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    public CLFragment getActiveFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        return (CLFragment) getSupportFragmentManager().findFragmentByTag(tag);
    }

}
