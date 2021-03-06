package facens.worthit.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.support.annotation.Dimension;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import facens.worthit.ImageType;
import facens.worthit.R;
import facens.worthit.helper.WebHelper;
import facens.worthit.model.CategoryOption;
import facens.worthit.model.UserOption;

public class CategoryOptionsAdapter extends ArrayAdapter<CategoryOption> {

    private final LayoutInflater mInflater;
    private final WebHelper mWebHelper;

    public CategoryOptionsAdapter(Context context, List<CategoryOption> categoryOptions){
        super(context, android.R.layout.simple_list_item_1);
        mWebHelper = new WebHelper();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setData(categoryOptions);
    }

    public void setData(List<CategoryOption> categoryOptions){
        clear();
        if(categoryOptions != null){
            for(CategoryOption categoryOption: categoryOptions){
                add(categoryOption);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View view;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.category_option, parent, false);
        } else {
            view = convertView;
        }

        CategoryOption categoryOption = getItem(position);


        ((TextView)view.findViewById(R.id.product_category_name)).setText(categoryOption.getName());
        ((RatingBar)view.findViewById(R.id.product_rating)).setRating(categoryOption.getRating());

        return view;
    }
}

