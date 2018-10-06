package facens.worthit.view.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

import facens.worthit.R;
import facens.worthit.adapter.ProductOptionsAdapter;
import facens.worthit.adapter.UserOptionsAdapter;
import facens.worthit.helper.DataHelper;
import facens.worthit.model.ProductOption;
import facens.worthit.model.UserOption;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private DataHelper mDataHelper;
    private SearchView mSearchView;
    private ProductOptionsAdapter mAdapter;

    public HomeFragment() {
        // Required empty public constructor
        mDataHelper = new DataHelper();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_home, container, false);

        mSearchView = (SearchView) v.findViewById(R.id.searchView);

        ListView listView = (ListView) v.findViewById(R.id.listReviews);

        //Opções do usuário
        List<ProductOption> productOptions = mDataHelper.getProductOptions();

        //Criando um adapter para a lista
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, userOptions);
        mAdapter = new ProductOptionsAdapter(getActivity().getBaseContext(),productOptions);

        listView.setAdapter(mAdapter);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if(mAdapter != null){
                    mAdapter.getFilter().filter(s);
                }
                    return false;
            }
        });

        // Inflate the layout for this fragment
        return v;
    }


}
