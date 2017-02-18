package sillyv.com.counterlists;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import sillyv.com.counterlists.events.AddFragmentEvent;
import sillyv.com.counterlists.screens.lists.edit.EditCounterListFragment;
import sillyv.com.counterlists.screens.lists.recycler.CountersListsFragment;
import sillyv.com.counterlists.views.AdvancedColorPickerDialog;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((TextView) toolbar.findViewById(R.id.title_text_view)).setText("aaaa");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        addFragment(new EditCounterListFragment());

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
//        if (fm.getBackStackEntryCount() == 0) {
//            updateNotificationVisibility(false);
//            setHomeAsUp();
//        }
    }

}
