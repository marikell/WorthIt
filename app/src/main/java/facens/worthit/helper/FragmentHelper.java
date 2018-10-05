package facens.worthit.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

import facens.worthit.R;

public class FragmentHelper {

    private FragmentManager mFragmentManager;
    private List<Fragment> mFragments;
    private Fragment active;
    private int frameId;

    public FragmentHelper(FragmentManager fragmentManager, List<Fragment> fragments, int activePosition, int frameId) {

        mFragmentManager = fragmentManager;
        mFragments = fragments;
        active = fragments.get(activePosition);
        this.frameId = frameId;
        setFragment(activePosition);
    }

    public void setFragment(int fragmentPosition){
        Fragment fragment = mFragments.get(fragmentPosition);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();
        active = fragment;
    }
}
