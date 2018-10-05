package facens.worthit.view.account;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import facens.worthit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    private ArrayList<String> getUserOptions(){

        return new ArrayList<String>(){{
           add("Teste123");
           add("Oi2");
           add("Oi3");
        }};

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
        List<String> userOptions = getUserOptions();

        //Criando um adapter para a lista
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, userOptions);

        listView.setAdapter(adapter);

        return v;
    }

}
