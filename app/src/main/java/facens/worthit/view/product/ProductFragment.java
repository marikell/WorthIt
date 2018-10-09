package facens.worthit.view.product;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import facens.worthit.R;
import facens.worthit.adapter.CategoryOptionsAdapter;
import facens.worthit.adapter.ProductOptionsAdapter;
import facens.worthit.adapter.UserOptionsAdapter;
import facens.worthit.helper.DataHelper;
import facens.worthit.helper.FragmentHelper;
import facens.worthit.helper.WebHelper;
import facens.worthit.interfaces.OnGetDataTaskCompleted;
import facens.worthit.interfaces.OnGetOnlyDataCompleted;
import facens.worthit.model.CategoryOption;
import facens.worthit.model.ProductOption;
import facens.worthit.model.ReviewOption;
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
    private FragmentHelper mFragmentHelper;

    public ProductFragment() {
        mDataHelper = new DataHelper();
        // Required empty public constructor
    }

    protected ArrayList<Fragment> createFragments(){
        return  new ArrayList<Fragment>() {{
            add(new ProductFragment());
            add(new ReviewFragment());
        }};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_product, container, false);

        final ProductOption productOption = (ProductOption)getArguments().getSerializable("product");

        //setando as configurações do que foi clicado.

        ((TextView)v.findViewById(R.id.product_name_text)).setText(productOption.getName());
        ((TextView)v.findViewById(R.id.product_price_text)).setText(productOption.getPriceToString());

        WebHelper webHelper = new WebHelper();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bitmap bitmap = webHelper.LoadImageFromWebOperations(webHelper.getUrl().concat("img/" + productOption.getImage()));

        if(bitmap != null) {
            ((ImageView) v.findViewById(R.id.product_image)).setImageBitmap(bitmap);
        }
        else{
            ((ImageView)v.findViewById(R.id.product_image)).setImageResource(R.drawable.asus);
        }


        final ListView listView = (ListView) v.findViewById(R.id.list_category_product);

        //Floating Buttons
        moreButton = (FloatingActionButton) v.findViewById(R.id.more_button);
        addReviewButton = (FloatingActionButton) v.findViewById(R.id.add_review);
        favoriteButton = (FloatingActionButton) v.findViewById(R.id.like_button);
        fabOpen = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);
        fabRClockWise = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_clockwise);
        fabRAntiClockWise = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_anticlockwise);

        addReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final ReviewFragment reviewFragment = new ReviewFragment();

                String productId = productOption.getId();

                Bundle bundle = new Bundle();
                bundle.putSerializable("reviewId",productId);

                reviewFragment.setArguments(bundle);

                ArrayList fragments =  new ArrayList<Fragment>() {{
                    add(new ProductFragment());
                    add(reviewFragment);
                }};

                mFragmentHelper = new FragmentHelper(getFragmentManager(),fragments,1, R.id.frame_home, false, "");

            }
        });

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

        mDataHelper.getCategoryOptions(getActivity(), new OnGetDataTaskCompleted<CategoryOption>() {
            @Override
            public void onTaskCompleted(ArrayList<CategoryOption> list, boolean error, String message) {

                CategoryOptionsAdapter adapter = new CategoryOptionsAdapter(getActivity().getBaseContext(),list);

                listView.setAdapter(adapter);

            }
        }, productOption.getId());

        //Opções do usuário
        //List<CategoryOption> categoryOptions = mDataHelper.getCategoryOptions();

        //Criando um adapter para a lista
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, userOptions);

        return v;

    }


}
