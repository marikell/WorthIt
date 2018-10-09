package facens.worthit.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import facens.worthit.R;
import facens.worthit.helper.WebHelper;
import facens.worthit.model.ProductOption;

public class ProductOptionsAdapter  extends ArrayAdapter<ProductOption> {

    private final LayoutInflater mInflater;
    private WebHelper mWebHelper;
    private ProductOptionFilter mFilter;
    private List<ProductOption> mOriginals;
    private List<ProductOption> mFiltered;


    public ProductOptionsAdapter(Context context, List<ProductOption> productOptions){
        super(context, android.R.layout.simple_list_item_1);
        mWebHelper = new WebHelper();
        mOriginals = productOptions;
        mFiltered = productOptions;
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

    class ProductOptionFilter extends Filter{


        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();

            if(charSequence != null && charSequence.length() > 0){
                charSequence = charSequence.toString().toUpperCase();
            }

            List<ProductOption> filtered = new ArrayList<>();

            for(int i = 0; i<mFiltered.size();i++){

                if(mFiltered.get(i).getName().toUpperCase().contains(charSequence)){
                    ProductOption productOption = mFiltered.get(i);
                    ProductOption newProductOption = new ProductOption(productOption.getId(), productOption.getName(), productOption.getImage(), productOption.getPrice(), productOption.getRating());

                    filtered.add(newProductOption);
                }

            }

            results.count = filtered.size();
            results.values = filtered;

            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            mOriginals = (List<ProductOption>)filterResults.values;
            notifyDataSetChanged();
            setData(mOriginals);

        }
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if(mFilter == null){
            mFilter = new ProductOptionFilter();
        }
        return mFilter;
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

        String url = mWebHelper.getUrl().concat("img/" + productOption.getImage());

        ImageLoader imageLoader = ImageLoader.getInstance();

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true).showImageForEmptyUri(R.drawable.asus)
                .showImageOnFail(R.drawable.asus).showImageOnLoading(R.drawable.asus).build();

        imageLoader.displayImage(url, (ImageView)view.findViewById(R.id.img_product),options);

        //((ImageView)view.findViewById(R.id.img_product)).setImageResource(R.drawable.asus);

        /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bitmap bitmap = mWebHelper.LoadImageFromWebOperations(mWebHelper.getUrl().concat("img/" + productOption.getImage()));

        if(bitmap != null){
            ((ImageView)view.findViewById(R.id.img_product)).setImageBitmap(bitmap);
        }
        else{*/

       // }

        return view;
    }
}
