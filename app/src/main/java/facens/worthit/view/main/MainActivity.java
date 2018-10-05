package facens.worthit.view.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

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

    protected ArrayList<Fragment> createFragments(){
        return  new ArrayList<Fragment>() {{
            add(new HomeFragment());
            add(new AccountFragment());
        }};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
