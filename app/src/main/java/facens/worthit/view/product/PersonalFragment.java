package facens.worthit.view.product;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import facens.worthit.R;
import facens.worthit.adapter.ProductOptionsAdapter;
import facens.worthit.adapter.SimpleProductOptionsAdapter;
import facens.worthit.helper.DataHelper;
import facens.worthit.interfaces.OnGetDataTaskCompleted;
import facens.worthit.model.ProductOption;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {


    public PersonalFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_personal, container, false);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){

            String author = firebaseUser.getEmail();

           final ListView listView = (ListView)v.findViewById(R.id.list_personal);

            DataHelper dataHelper = new DataHelper();

            dataHelper.getReviewsFromAuthor(getActivity(), author, new OnGetDataTaskCompleted<ProductOption>() {
                @Override
                public void onTaskCompleted(ArrayList<ProductOption> list, boolean error, String message) {
                    SimpleProductOptionsAdapter simpleProductOptionsAdapter = new SimpleProductOptionsAdapter(getActivity(),list);

                    listView.setAdapter(simpleProductOptionsAdapter);
                }
            });

        }
        return v;
    }

}
