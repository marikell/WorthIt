package facens.worthit.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import facens.worthit.R;
import facens.worthit.helper.WebHelper;
import facens.worthit.model.ProductOption;

public class SimpleProductOptionsAdapter extends ArrayAdapter<ProductOption> {

private final LayoutInflater mInflater;

public SimpleProductOptionsAdapter(Context context, List<ProductOption> productOptions){
        super(context, android.R.layout.simple_list_item_1);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setData(productOptions);
        }

public void setData(List<ProductOption> productOptions){
        clear();
        if(productOptions != null){
        for(ProductOption categoryOption: productOptions){
        add(categoryOption);
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

    WebHelper webHelper = new WebHelper();

          StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bitmap bitmap = webHelper.LoadImageFromWebOperations(webHelper.getUrl().concat("img/" + productOption.getImage()));

        if(bitmap != null){
            ((ImageView)view.findViewById(R.id.img_product)).setImageBitmap(bitmap);
        }
        else{
    ((ImageView)view.findViewById(R.id.img_product)).setImageResource(R.drawable.asus);
     }


        return view;
        }
        }