package facens.worthit.view.product;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import facens.worthit.R;
import facens.worthit.adapter.CategoryOptionsAdapter;
import facens.worthit.adapter.UserOptionsAdapter;
import facens.worthit.helper.DataHelper;
import facens.worthit.model.CategoryOption;
import facens.worthit.model.UserOption;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {

    private DataHelper mDataHelper;

    public ReviewFragment() {
        // Required empty public constructor
        mDataHelper = new DataHelper();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_review, container, false);

        ListView listView = (ListView) v.findViewById(R.id.list_category_product_review);

        //Opções do usuário
        List<CategoryOption> categoryOptions = mDataHelper.getCategoryOptions();

        //Criando um adapter para a lista
        CategoryOptionsAdapter adapter = new CategoryOptionsAdapter(getActivity().getBaseContext(),categoryOptions);

        listView.setAdapter(adapter);

        return v;
    }

}
