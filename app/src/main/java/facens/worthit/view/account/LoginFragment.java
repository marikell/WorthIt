package facens.worthit.view.account;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import facens.worthit.R;
import facens.worthit.helper.FragmentHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private FragmentHelper mFragmentHelper;

    public LoginFragment() {
        // Required empty public constructor
    }

    protected ArrayList<Fragment> createFragments(){
        return  new ArrayList<Fragment>() {{
            add(new LoginFragment());
            add(new RegisterFragment());
        }};
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);


        ((Button)v.findViewById(R.id.login_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        ((Button)v.findViewById(R.id.register_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFragmentHelper = new FragmentHelper(getFragmentManager(),createFragments(),1, R.id.frame_account, false, "");

            }
        });

        return v;
    }

}
