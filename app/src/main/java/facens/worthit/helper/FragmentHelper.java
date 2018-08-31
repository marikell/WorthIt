package facens.worthit.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

import facens.worthit.R;

public class FragmentHelper {

    private FragmentManager mFragmentManager;
    private List<Fragment> mFragments;
    private Fragment active;

    public FragmentHelper(FragmentManager fragmentManager, List<Fragment> fragments, int activePosition){

    mFragmentManager = fragmentManager;
    mFragments = fragments;
    active = fragments.get(activePosition);

    }

    public void commit(){
        for(int i = 0; i<mFragments.size();i++){
            Fragment fragment = mFragments.get(i);
            if(active.getId() == fragment.getId()){
                mFragmentManager.beginTransaction().add(R.id.frame, fragment, String.valueOf(i)).commit();
            }
            else{
                mFragmentManager.beginTransaction().add(R.id.frame, fragment, String.valueOf(i)).hide(fragment).commit();
            }
        }
    }

    public void navigate(int position){
        mFragmentManager.beginTransaction().hide(active).show(mFragments.get(position)).commit();
        active = mFragments.get(position);
    }

}
