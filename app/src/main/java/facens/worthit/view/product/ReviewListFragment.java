package facens.worthit.view.product;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

import facens.worthit.R;
import facens.worthit.adapter.ProductOptionsAdapter;
import facens.worthit.helper.DataHelper;
import facens.worthit.helper.FragmentHelper;
import facens.worthit.model.ProductOption;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewListFragment extends Fragment {

    private DataHelper mDataHelper;
    private SearchView mSearchView;
    private FragmentHelper mFragmentHelper;
    private FrameLayout mMainFrame;
    private ProductOptionsAdapter mAdapter;

    public ReviewListFragment() {
        // Required empty public constructor
        mDataHelper = new DataHelper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_review_list, container, false);

        mSearchView = (SearchView) v.findViewById(R.id.searchView);

        ListView listView = (ListView) v.findViewById(R.id.listReviews);

        //Opções do usuário
        List<ProductOption> productOptions = mDataHelper.getProductOptions();

        //Criando um adapter para a lista
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, userOptions);
        mAdapter = new ProductOptionsAdapter(getActivity().getBaseContext(),productOptions);

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //mFragmentHelper = new FragmentHelper(getFragmentManager(),createFragments(),0, R.id.frame_home);
                //mFragmentHelper.setFragment(1);

            }
        });


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
