package facens.worthit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import facens.worthit.R;
import facens.worthit.model.CategoryOption;

public class ReviewOptionsAdapter extends ArrayAdapter<CategoryOption> {

    private final LayoutInflater mInflater;

    public ReviewOptionsAdapter(Context context, List<CategoryOption> categoryOptions){
        super(context, android.R.layout.simple_list_item_1);
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
            view = mInflater.inflate(R.layout.category_option_review, parent, false);
        } else {
            view = convertView;
        }

        CategoryOption categoryOption = getItem(position);

        ((TextView)view.findViewById(R.id.product_category_name_review)).setText(categoryOption.getName());
        ((RatingBar)view.findViewById(R.id.product_rating_review)).setRating(categoryOption.getRating());

        return view;
    }
}