package facens.worthit.view.account;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import facens.worthit.R;
import facens.worthit.helper.DataHelper;
import facens.worthit.helper.FragmentHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private FragmentHelper mFragmentHelper;
    private DataHelper mDataHelper;
    private FrameLayout mMainFrame;
    private FirebaseAuth mAuth;

    public AccountFragment() {
        // Required empty public constructor
        mAuth = FirebaseAuth.getInstance();

    }

    protected ArrayList<Fragment> createFragments(){
        return  new ArrayList<Fragment>() {{
            add(new LoginFragment());
            add(new ProfileFragment());
        }};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        mDataHelper = new DataHelper();

        if(firebaseUser != null){
            mFragmentHelper = new FragmentHelper(getFragmentManager(),createFragments(),1, R.id.frame_account, false, "");
        }
        else{
            mFragmentHelper = new FragmentHelper(getFragmentManager(),createFragments(),0, R.id.frame_account, false, "");

        }

        //mFragmentHelper.setFragment(0, false, "");
        return inflater.inflate(R.layout.fragment_account, container, false);
    }
}
