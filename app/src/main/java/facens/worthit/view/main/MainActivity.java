package facens.worthit.view.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import facens.worthit.helper.FragmentHelper;
import facens.worthit.view.home.HomeFragment;
import facens.worthit.view.account.AccountFragment;
import facens.worthit.R;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, AccountFragment.OnFragmentInteractionListener{

    private TextView mTextMessage;
    private FragmentHelper fragmentHelper;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentHelper.navigate(0);
                    return true;
                case R.id.navigation_profile:
                    fragmentHelper.navigate(1);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentHelper = new FragmentHelper(getSupportFragmentManager(), createFragments(),0);
        fragmentHelper.commit();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private List<Fragment> createFragments(){
        return Arrays.asList(new HomeFragment(),new AccountFragment());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
