package facens.worthit.view.product;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.media.Rating;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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

                final ReviewOption r = object;

                final List<CategoryOption> categoryOptions = new ArrayList<>();


                for(int i = 0; i<object.getAttributes().size();i++){

                    String attributeName = object.getAttributes().get(i);
                    CategoryOption categoryOption = new CategoryOption((Integer.toString(i+1)), attributeName, 0);
                    categoryOptions.add(categoryOption);
                }

                for(int i = 0; i<categoryOptions.size();i++){
                    CategoryOption option = categoryOptions.get(i);

                    LinearLayout linearLayout = (LinearLayout)v.findViewById(R.id.review_attributes);
                    linearLayout.addView(createTextView(10000+i, option.getName().toUpperCase()));
                    linearLayout.addView(createRatingBar(2000+i));
                }

                ((FloatingActionButton)v.findViewById(R.id.review_create)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ReviewOption reviewOption = new ReviewOption();

                        String price = ((EditText)v.findViewById(R.id.product_price_text)).getText().toString();
                        reviewOption.setPrice(Float.valueOf(price));

                        float[] ratings = new float[categoryOptions.size()];

                        for(int i = 0; i<categoryOptions.size();i++){

                            RatingBar r = (RatingBar)v.findViewById(2000+i);

                            ratings[i] = r.getRating()*2;
                        }

                        reviewOption.setRatings(ratings);
                        reviewOption.setCategory(r.getCategory());
                        reviewOption.setProductId(r.getProductId());
                        reviewOption.setImage(r.getImage());
                        reviewOption.setName(r.getName());

                        mDataHelper.sendReview(getActivity(), reviewOption, new OnGetOnlyDataCompleted<String>() {
                            @Override
                            public void onTaskCompleted(String object, boolean error, String message) {

                                if(object.equals("")){
                                    Toast.makeText(getActivity(), "Review realizada com sucesso.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                    }
                });


           }});

        return v;
    }

    private RatingBar createRatingBar(int id){

        RatingBar ratingBar = new RatingBar(getActivity());

        ratingBar.setId(id);
        ratingBar.setNumStars(5);
        ratingBar.setMax(5);
        ratingBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ratingBar.setIsIndicator(false);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#FFCC00"), PorterDuff.Mode.SRC_ATOP);

        return ratingBar;

    }

    private TextView createTextView(int id, String text){

        TextView textView = new TextView(getActivity());

        textView.setId(id);
        textView.setText(text);
        textView.setTextAppearance(R.style.optionText);

        return textView;
    }

}
