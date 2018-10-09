package facens.worthit.view.account;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import facens.worthit.R;
import facens.worthit.adapter.UserOptionsAdapter;
import facens.worthit.helper.DataHelper;
import facens.worthit.helper.FragmentHelper;
import facens.worthit.model.ProductOption;
import facens.worthit.model.UserOption;
import facens.worthit.view.product.PersonalFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private DataHelper mDataHelper;
    private FirebaseAuth mAuth;

    public ProfileFragment() {
        // Required empty public constructor
        mDataHelper = new DataHelper();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_profile, container, false);

        FirebaseUser user = mAuth.getCurrentUser();

        if(user!= null){
            ((TextView)v.findViewById(R.id.txtUserLogged)).setText(user.getEmail());
        }

        ListView listView = (ListView) v.findViewById(R.id.listUserOptions);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                UserOption option =  (UserOption) parent.getAdapter().getItem(position);

                //MINHAS REVIEWS
                if(option.getId() == "1"){

                    final ArrayList<Fragment> fragments = new ArrayList<Fragment>() {{
                        add(new LoginFragment());
                        add(new RegisterFragment());
                        add(new ProfileFragment());
                        add(new PersonalFragment());
                    }};

                    FragmentHelper mFragmentHelper = new FragmentHelper(getFragmentManager(),fragments ,3, R.id.frame_account, false, "");

                }

                //LOGOUT
                else if(option.getId() == "2"){

                    FirebaseUser firebaseUser = mAuth.getCurrentUser();

                    if(firebaseUser != null){

                        mAuth.signOut();

                        Toast.makeText(getActivity(), "Logout realizado com sucesso!", Toast.LENGTH_SHORT).show();

                        Handler handler = new Handler();

                        final ArrayList<Fragment> fragments = new ArrayList<Fragment>() {{
                            add(new LoginFragment());
                            add(new RegisterFragment());
                            add(new ProfileFragment());
                        }};

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                FragmentHelper mFragmentHelper = new FragmentHelper(getFragmentManager(),fragments ,0, R.id.frame_account, false, "");


                            }
                        },1000);

                    }


                }


            }
        });

        //Opções do usuário
        List<UserOption> userOptions = mDataHelper.getUserOptions();

        //Criando um adapter para a lista
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, userOptions);
        UserOptionsAdapter adapter = new UserOptionsAdapter(getActivity().getBaseContext(),userOptions);

        listView.setAdapter(adapter);

        return v;
    }

}
