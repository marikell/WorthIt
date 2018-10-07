package facens.worthit.view.product;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private FloatingActionButton addReviewButton, favoriteButton, moreButton;
    Animation fabOpen, fabClose, fabRClockWise, fabRAntiClockWise;
    private boolean isOpen = false;

    public ProductFragment() {
        mDataHelper = new DataHelper();
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_product, container, false);

        ListView listView = (ListView) v.findViewById(R.id.list_category_product);

        //Floating Buttons
        moreButton = (FloatingActionButton) v.findViewById(R.id.more_button);
        addReviewButton = (FloatingActionButton) v.findViewById(R.id.add_review);
        favoriteButton = (FloatingActionButton) v.findViewById(R.id.like_button);
        fabOpen = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);
        fabRClockWise = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_clockwise);
        fabRAntiClockWise = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_anticlockwise);

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen){
                    addReviewButton.startAnimation(fabClose);
                    favoriteButton.startAnimation(fabClose);
                    moreButton.startAnimation(fabRAntiClockWise);
                    addReviewButton.setClickable(false);
                    favoriteButton.setClickable(false);
                    isOpen = false;
                }
                else{
                    addReviewButton.startAnimation(fabOpen);
                    favoriteButton.startAnimation(fabOpen);
                    moreButton.startAnimation(fabRClockWise);
                    addReviewButton.setClickable(true);
                    favoriteButton.setClickable(true);
                    isOpen = true;
                }
            }
        });

        //Opções do usuário
        List<CategoryOption> categoryOptions = mDataHelper.getCategoryOptions();

        //Criando um adapter para a lista
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, userOptions);
        CategoryOptionsAdapter adapter = new CategoryOptionsAdapter(getActivity().getBaseContext(),categoryOptions);

        listView.setAdapter(adapter);
        return v;

    }


}
