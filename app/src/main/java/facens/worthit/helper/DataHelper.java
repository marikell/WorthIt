package facens.worthit.helper;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import facens.worthit.ImageType;
import facens.worthit.interfaces.OnGetDataTaskCompleted;
import facens.worthit.model.CategoryOption;
import facens.worthit.model.ProductOption;
import facens.worthit.model.UserOption;

public class DataHelper {

    private final String url = "http://192.168.0.102:5000/";

   public boolean isLoggedIn(){
       return true;
   }

    public ArrayList<UserOption> getUserOptions(){
        return new ArrayList<UserOption>(){{
            add(new UserOption("1", "Minhas Reviews", ImageType.MY_REVIEWS));
            add(new UserOption("2", "Logout", ImageType.LOGOUT));
        }};
    }

    public ArrayList<CategoryOption> getCategoryOptions(){
       return new ArrayList<CategoryOption>(){
           {
                add(new CategoryOption("1","DESEMPENHO", 4));
                add(new CategoryOption("2", "HARDWARE", 5));
                add(new CategoryOption("3", "CUSTO",2));
           }
       };
    }

    public void getProductOptions(Activity activity, final OnGetDataTaskCompleted taskCompleted){

        final ArrayList<ProductOption> productOptions = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + "review", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               try{
                   JSONArray jsonArray = response.getJSONArray("data");

                   for (int i=0; i < jsonArray.length(); i++) {
                       JSONObject jsonObject =  jsonArray.getJSONObject(i);

                       ProductOption productOption = new ProductOption(jsonObject.getString("product_id"),
                               jsonObject.getString("name"), jsonObject.getString("image_id"),(float) jsonObject.getDouble("cost"),
                               (float)jsonObject.getDouble("attributes_avg")/2);
                   productOptions.add(productOption);
                   }

                   taskCompleted.onTaskCompleted(productOptions,false,null);
               }
               catch(Exception ex){
                   taskCompleted.onTaskCompleted(new ArrayList<ProductOption>(),true,ex.getMessage());
               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                taskCompleted.onTaskCompleted(new ArrayList<ProductOption>(),true,error.getMessage());

            }
        });
            requestQueue.add(jsonObjectRequest);


      /* return new ArrayList<ProductOption>(){
           {
               add(new ProductOption("1", "Asus Zenfone 5","galaxys8_img", 2000, 5));
               add(new ProductOption("2", "Iphone X","galaxys8_img", 3000, 2));
               add(new ProductOption("3", "Asus Zenfone 5","galaxys8_img", 2000, 5));
               add(new ProductOption("4", "Iphone X","galaxys8_img", 3000, 2));
               add(new ProductOption("1", "Asus Zenfone 5","galaxys8_img", 2000, 5));
               add(new ProductOption("2", "Iphone X","galaxys8_img", 3000, 2));
               add(new ProductOption("3", "Asus Zenfone 5","galaxys8_img", 2000, 5));
               add(new ProductOption("4", "Iphone X","galaxys8_img", 3000, 2));
               //add(new ProductOption("7", "Asus Zenfone 5","galaxys8_img", 2000, 5));
           }
       };*/
    }

}
