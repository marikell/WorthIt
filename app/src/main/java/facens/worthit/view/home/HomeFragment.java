package facens.worthit.view.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import facens.worthit.R;
import facens.worthit.adapter.ProductOptionsAdapter;
import facens.worthit.adapter.UserOptionsAdapter;
import facens.worthit.helper.DataHelper;
import facens.worthit.helper.FragmentHelper;
import facens.worthit.model.ProductOption;
import facens.worthit.model.UserOption;
import facens.worthit.view.account.LoginFragment;
import facens.worthit.view.account.ProfileFragment;
import facens.worthit.view.product.ProductFragment;
import facens.worthit.view.product.ReviewListFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private FragmentHelper mFragmentHelper;
    private FrameLayout mMainFrame;

    public HomeFragment() {
    }

    protected ArrayList<Fragment> createFragments(){
        return  new ArrayList<Fragment>() {{
            add(new ReviewListFragment());
            add(new ProductFragment());
        }};
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentHelper = new FragmentHelper(getFragmentManager(),createFragments(),0, R.id.frame_home, false, "");
        mFragmentHelper.setFragment(0, false, "");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


}
