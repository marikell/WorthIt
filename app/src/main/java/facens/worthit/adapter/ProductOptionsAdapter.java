package facens.worthit.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import facens.worthit.ImageType;
import facens.worthit.R;
import facens.worthit.helper.WebHelper;
import facens.worthit.model.ProductOption;
import facens.worthit.model.UserOption;

public class ProductOptionsAdapter  extends ArrayAdapter<ProductOption> implements Filterable {

    private final LayoutInflater mInflater;
    private WebHelper mWebHelper;


    public ProductOptionsAdapter(Context context, List<ProductOption> productOptions){
        super(context, android.R.layout.simple_list_item_1);
        mWebHelper = new WebHelper();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setData(productOptions);
    }

    public void setData(List<ProductOption> productOptions){
        clear();
        if(productOptions != null){
            for(ProductOption productOption: productOptions){
                add(productOption);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View view;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.product_option, parent, false);
        } else {
            view = convertView;
        }

        ProductOption productOption = getItem(position);

        ((TextView)view.findViewById(R.id.product_name)).setText(productOption.getName());
        ((TextView)view.findViewById(R.id.product_price)).setText(productOption.getPriceToString());
        ((RatingBar)view.findViewById(R.id.ratingBar)).setRating(productOption.getRating());

        Drawable drawable = mWebHelper.LoadImageFromWebOperations(mWebHelper.getUrl().concat("img/" + productOption.getImage()));

        if(drawable != null){
            ((ImageView)view.findViewById(R.id.img_product)).setImageDrawable(drawable);
        }
        else{
            ((ImageView)view.findViewById(R.id.img_product)).setImageResource(R.drawable.asus);
        }

        return view;
    }
}
