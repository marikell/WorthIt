package facens.worthit.view.product;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import facens.worthit.R;
import facens.worthit.adapter.CategoryOptionsAdapter;
import facens.worthit.adapter.UserOptionsAdapter;
import facens.worthit.helper.DataHelper;
import facens.worthit.helper.WebHelper;
import facens.worthit.interfaces.OnGetDataTaskCompleted;
import facens.worthit.interfaces.OnGetOnlyDataCompleted;
import facens.worthit.model.CategoryOption;
import facens.worthit.model.ReviewOption;
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
        final View v=  inflater.inflate(R.layout.fragment_review, container, false);

        String  reviewId = getArguments().getSerializable("reviewId").toString();

        DataHelper dataHelper = new DataHelper();


        mDataHelper.getReviewOption(getActivity(), reviewId, new OnGetOnlyDataCompleted<ReviewOption>() {
            @Override
            public void onTaskCompleted(ReviewOption object, boolean error, String message) {

                ((TextView) v.findViewById(R.id.review_product_name_text)).setText(object.getName());

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                WebHelper webHelper = new WebHelper();

                Bitmap bitmap = webHelper.LoadImageFromWebOperations(webHelper.getUrl().concat("img/" + object.getImage()));

                if (bitmap != null) {
                    ((ImageView) v.findViewById(R.id.review_image)).setImageBitmap(bitmap);
                } else {
                    ((ImageView) v.findViewById(R.id.review_image)).setImageResource(R.drawable.asus);
                }

                ListView listView = (ListView) v.findViewById(R.id.list_category_product_review);

                List<CategoryOption> categoryOptions = new ArrayList<>();


                for(int i = 0; i<object.getAttributes().size();i++){

                    String attributeName = object.getAttributes().get(i);
                    CategoryOption categoryOption = new CategoryOption((Integer.toString(i+1)), attributeName, 0);
                    categoryOptions.add(categoryOption);
                }
                //mDataHelper.getCategoryOptions();

                //Criando um adapter para a lista
                CategoryOptionsAdapter adapter = new CategoryOptionsAdapter(getActivity().getBaseContext(),categoryOptions);

                listView.setAdapter(adapter);

            }
        });



        return v;
    }

}
