package facens.worthit.view.account;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import facens.worthit.ImageType;
import facens.worthit.R;
import facens.worthit.adapter.UserOptionsAdapter;
import facens.worthit.helper.AccountHelper;
import facens.worthit.model.UserOption;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private AccountHelper accountHelper;

    public ProfileFragment() {
        // Required empty public constructor
        accountHelper = new AccountHelper();
    }


    private void createUserOptionsAdapter(){

        // Inflate the layout for this fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_profile, container, false);

        ListView listView = (ListView) v.findViewById(R.id.listUserOptions);

        //Opções do usuário
        List<UserOption> userOptions = accountHelper.getUserOptions();

        //Criando um adapter para a lista
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, userOptions);
        UserOptionsAdapter adapter = new UserOptionsAdapter(getActivity().getBaseContext(),userOptions);

        listView.setAdapter(adapter);

        return v;
    }

}
