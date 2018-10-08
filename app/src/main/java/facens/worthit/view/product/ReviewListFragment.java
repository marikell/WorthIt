package facens.worthit.view.product;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import facens.worthit.R;
import facens.worthit.adapter.ProductOptionsAdapter;
import facens.worthit.helper.DataHelper;
import facens.worthit.helper.FragmentHelper;
import facens.worthit.interfaces.OnGetDataTaskCompleted;
import facens.worthit.model.ProductOption;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewListFragment extends Fragment {

    private DataHelper mDataHelper;
    private SearchView mSearchView;
    private FragmentHelper mFragmentHelper;
    private FrameLayout mMainFrame;
    private ArrayList<ProductOption> allProducts;
    private ProductOptionsAdapter mAdapter;
    private int page = 0;

    public ReviewListFragment() {
        // Required empty public constructor
        mDataHelper = new DataHelper();
        allProducts = new ArrayList<>();
    }

    protected ArrayList<Fragment> createFragments(){
        return  new ArrayList<Fragment>() {{
            add(new ReviewListFragment());
            add(new ProductFragment());
        }};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_review_list, container, false);

        mSearchView = (SearchView) v.findViewById(R.id.searchView);

        final ListView listView = (ListView) v.findViewById(R.id.listReviews);

        ((Button)v.findViewById(R.id.loadMore)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                page++;

                mDataHelper.getProductOptions(getActivity(), new OnGetDataTaskCompleted<ProductOption>() {
                    @Override
                    public void onTaskCompleted(ArrayList<ProductOption> list, boolean error, String message) {

                        allProducts.addAll(list);
                        //Criando um adapter para a lista
                        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, userOptions);
                        mAdapter = new ProductOptionsAdapter(getActivity().getBaseContext(),allProducts);

                        listView.setAdapter(mAdapter);

                    }
                },page);



            }
        });


        //Opções do usuário
        mDataHelper.getProductOptions(getActivity(), new OnGetDataTaskCompleted<ProductOption>() {
            @Override
            public void onTaskCompleted(ArrayList<ProductOption> list, boolean error, String message) {

                allProducts.addAll(list);
                //Criando um adapter para a lista
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, userOptions);
                mAdapter = new ProductOptionsAdapter(getActivity().getBaseContext(),allProducts);

                listView.setAdapter(mAdapter);

            }
        },0);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ProductOption productOption = (ProductOption) adapterView.getAdapter().getItem(i);

                Bundle bundle = new Bundle();

                bundle.putSerializable("product", productOption);
                final ProductFragment productFragment = new ProductFragment();
                productFragment.setArguments(bundle);

                ArrayList<Fragment> fragments = new ArrayList<Fragment>() {{
                    add(new ReviewListFragment());
                    add(productFragment);
                }};

                mFragmentHelper = new FragmentHelper(getFragmentManager(),fragments,1, R.id.frame_home, false, "");
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
