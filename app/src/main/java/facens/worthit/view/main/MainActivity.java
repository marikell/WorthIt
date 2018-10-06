package facens.worthit.view.main;

import android.app.ActionBar;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.SearchView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import facens.worthit.R;
import facens.worthit.helper.FragmentHelper;
import facens.worthit.view.account.AccountFragment;
import facens.worthit.view.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private FrameLayout mMainFrame;
    private FragmentHelper mFragmentHelper;
    private android.support.v7.widget.Toolbar mToolbar;

    protected ArrayList<Fragment> createFragments(){
        return  new ArrayList<Fragment>() {{
            add(new HomeFragment());
            add(new AccountFragment());
        }};
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        mMainFrame = (FrameLayout) findViewById(R.id.frame);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mFragmentHelper = new FragmentHelper(getSupportFragmentManager(),createFragments(),0, R.id.frame);
        mFragmentHelper.setFragment(0);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navigation_home:
                        mFragmentHelper.setFragment(0);
                        return true;

                    case R.id.navigation_profile:
                        mFragmentHelper.setFragment(1);
                        return true;
                    default:
                        return false;
                }

            }

        });
    }
}
