package facens.worthit.view.product;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import facens.worthit.R;
import facens.worthit.adapter.CategoryOptionsAdapter;
import facens.worthit.adapter.ProductOptionsAdapter;
import facens.worthit.adapter.UserOptionsAdapter;
import facens.worthit.helper.DataHelper;
import facens.worthit.helper.FragmentHelper;
import facens.worthit.model.CategoryOption;
import facens.worthit.model.ProductOption;
import facens.worthit.model.UserOption;
import facens.worthit.view.account.LoginFragment;
import facens.worthit.view.account.ProfileFragment;
import facens.worthit.view.product.ProductFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    private DataHelper mDataHelper;

    public ProductFragment() {
        mDataHelper = new DataHelper();
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_product, container, false);

        ListView listView = (ListView) v.findViewById(R.id.list_category_product);

        //Opções do usuário
        List<CategoryOption> categoryOptions = mDataHelper.getCategoryOptions();

        //Criando um adapter para a lista
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, userOptions);
        CategoryOptionsAdapter adapter = new CategoryOptionsAdapter(getActivity().getBaseContext(),categoryOptions);

        listView.setAdapter(adapter);
        return v;

    }


}
