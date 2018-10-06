package facens.worthit.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import facens.worthit.R;

public class FragmentHelper {

    private FragmentManager mFragmentManager;
    private List<Fragment> mFragments;
    private Fragment active;
    private int frameId;

    public FragmentHelper(FragmentManager fragmentManager, List<Fragment> fragments, int activePosition, int frameId, boolean isToBackStack, String backFragment) {

        mFragmentManager = fragmentManager;
        mFragments = fragments;
        active = fragments.get(activePosition);
        this.frameId = frameId;
        setFragment(activePosition, isToBackStack, backFragment);
    }


    public void setFragment(int fragmentPosition, boolean isToBackStack, String backFragment){
        Fragment fragment = mFragments.get(fragmentPosition);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        if(isToBackStack){
            transaction.isAddToBackStackAllowed();
            transaction.addToBackStack(backFragment);
        }
        transaction.commit();
        active = fragment;
    }
}
