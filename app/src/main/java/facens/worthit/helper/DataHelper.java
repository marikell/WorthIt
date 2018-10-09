package facens.worthit.helper;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import facens.worthit.ImageType;
import facens.worthit.interfaces.OnGetDataTaskCompleted;
import facens.worthit.interfaces.OnGetOnlyDataCompleted;
import facens.worthit.model.CategoryOption;
import facens.worthit.model.ProductOption;
import facens.worthit.model.ReviewOption;
import facens.worthit.model.UserOption;

public class DataHelper {



   public boolean isLoggedIn(){
       return true;
   }

   public String formatDataAsJSON(ReviewOption reviewOption) throws JSONException{
       final JSONObject root = new JSONObject();

       try{


           root.put("product_id",reviewOption.getProductId());
           root.put("name", reviewOption.getName());
           root.put("category",reviewOption.getCategory());
           root.put("image_id", reviewOption.getImage());
           root.put("cost", String.valueOf(reviewOption.getPrice()));


           float[] ratings = reviewOption.getRatings();

           for(int i = 0; i<ratings.length;i++){
               float rating = ratings[i];
               root.put("attribute_" + Integer.toString(i+1),String.valueOf(rating));
           }
           if(ratings.length<6){
                        root.put("attribute_6","");
           }

               root.put("likes","0");
                root.put("dislikes","0");

                FirebaseAuth auth = FirebaseAuth.getInstance();

                FirebaseUser user = auth.getCurrentUser();

                if(user != null){
                    root.put("author",user.getEmail());
                }

                return root.toString(1);
       }

       catch(JSONException e){
        Log.d("JWP","Não foi possível formatar o JSON");
       }

       return null;

   }

   public void sendSata(ReviewOption reviewOption) throws JSONException{

       final String jsonData = formatDataAsJSON(reviewOption);

       new AsyncTask<Void,Void,String>(){

           @Override
           protected void onPostExecute(String s) {
               super.onPostExecute(s);
           }

           @Override
           protected String doInBackground(Void... voids) {
               return getServerResponse(jsonData, new WebHelper().getUrl()+"review");
           }
       }.execute();

   }

   private String getServerResponse(String json, String url){

        WebHelper webHelper = new WebHelper();

        try{
            URL URL = new URL(url);

            HttpURLConnection urlConnection = (HttpURLConnection)URL.openConnection();

            urlConnection.setDoInput(true);
            urlConnection.setDoInput(true);

            urlConnection.setRequestProperty("Content-Type", "application/json");

            urlConnection.setRequestMethod("POST");

            if(json != null){

                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());

                writer.write(json);

                writer.flush();

            }

            int statusCode = urlConnection.getResponseCode();

            if(statusCode == 200){

                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                return "success";
                //String response = convertInputStreamToString(inputStream);

                // From here you can convert the string to JSON with whatever JSON parser you like to use
                // After converting the string to JSON, I call my custom callback. You can follow this process too, or you can implement the onPostExecute(Result) method
            } else {

                Log.e("error", "Erro ao realizar post.");
                // Status code is not 200
                // Do something to handle the error
            }

        }
        catch(Exception ex){
            Log.e("error", ex.getMessage());
        }

       return "error";
   }

    public ArrayList<UserOption> getUserOptions(){
        return new ArrayList<UserOption>(){{
            //add(new UserOption("1", "Minhas Reviews", ImageType.MY_REVIEWS));
            add(new UserOption("2", "Logout", ImageType.LOGOUT));
        }};
    }

    public void getReviewsFromAuthor(Activity activity, String author, final OnGetDataTaskCompleted<ProductOption> taskCompleted){

        final ArrayList<ProductOption> productOptions = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(activity);


        WebHelper webHelper = new WebHelper();

        String url = webHelper.getUrl() + "review" + "?author="+author;

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    JSONArray productAttributes = response;

                    for(int i = 0; i<productAttributes.length();i++){

                        ProductOption productOption = new ProductOption();

                        JSONObject jsonObject = productAttributes.getJSONObject(i);

                        productOption.setId(jsonObject.getString("product_id"));
                        productOption.setName(jsonObject.getString("name"));
                        productOption.setImage(jsonObject.getString("image_id"));
                        productOption.setPrice(Float.valueOf(jsonObject.getString("price")));
                        productOption.setRating(Float.valueOf(jsonObject.getString("attributes_avg")));

                        productOptions.add(productOption);
                    }

                    taskCompleted.onTaskCompleted(productOptions,false,null);
                }
                catch(Exception ex){
                    taskCompleted.onTaskCompleted(productOptions,true,ex.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                taskCompleted.onTaskCompleted(new ArrayList<ProductOption>(),true,error.getMessage());

            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public void getReviewOption(Activity activity,String productId, final OnGetOnlyDataCompleted taskCompleted){

    final ReviewOption reviewOption = new ReviewOption();


        RequestQueue requestQueue = Volley.newRequestQueue(activity);


        WebHelper webHelper = new WebHelper();

        String url = webHelper.getUrl() + "review/" + productId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray productAttributes = response.getJSONArray("attributes");
                    JSONObject productDesc = response.getJSONObject("data");

                    reviewOption.setImage(productDesc.getString("image_id"));
                    reviewOption.setProductId(productDesc.getString("product_id"));
                    reviewOption.setPrice((float)productDesc.getDouble("cost"));
                    reviewOption.setName(productDesc.getString("name"));
                    reviewOption.setCategory(productDesc.getString("category"));

                    ArrayList<String> attributes = new ArrayList<>();

                    for (int i=0; i < productAttributes.length(); i++) {
                        String attributeName =  productAttributes.get(i).toString();

                        if(!attributeName.isEmpty()){

                            attributes.add(attributeName);
                        }
                    }
                    reviewOption.setAttributes(attributes);

                    taskCompleted.onTaskCompleted(reviewOption,false,null);
                }
                catch(Exception ex){
                    taskCompleted.onTaskCompleted(new ReviewOption(),true,ex.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                taskCompleted.onTaskCompleted(new ReviewOption(),true,error.getMessage());

            }
        });
        requestQueue.add(jsonObjectRequest);




    }

    public String GetText(ReviewOption reviewOption) throws UnsupportedEncodingException{

       FirebaseAuth auth = FirebaseAuth.getInstance();
       FirebaseUser user= auth.getCurrentUser();

       String data = URLEncoder.encode("product_id","UTF-8") + "=" + URLEncoder.encode(reviewOption.getProductId(), "UTF-8");

        data += "&" + URLEncoder.encode("name", "UTF-8") + "="
                + URLEncoder.encode(reviewOption.getName(), "UTF-8");

        data += "&" + URLEncoder.encode("cost", "UTF-8")
                + "=" + URLEncoder.encode(String.valueOf(reviewOption.getPrice()), "UTF-8");

        data += "&" + URLEncoder.encode("category", "UTF-8")
                + "=" + URLEncoder.encode(reviewOption.getCategory(), "UTF-8");

        data += "&" + URLEncoder.encode("image_id", "UTF-8")
                + "=" + URLEncoder.encode(reviewOption.getImage(), "UTF-8");

        data += "&" + URLEncoder.encode("author", "UTF-8")
                + "=" + URLEncoder.encode(user.getEmail(), "UTF-8");

        data += "&" + URLEncoder.encode("likes", "UTF-8")
                + "=" + URLEncoder.encode("0", "UTF-8");

        data += "&" + URLEncoder.encode("dislikes", "UTF-8")
                + "=" + URLEncoder.encode("0", "UTF-8");


        float[] ratings = reviewOption.getRatings();

        for(int i = 0; i<ratings.length;i++){
            float rating = ratings[i];

            data += "&" + URLEncoder.encode("attribute_"+String.valueOf(i+1), "UTF-8")
                    + "=" + URLEncoder.encode(String.valueOf(rating), "UTF-8");

        }

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(new WebHelper().getUrl()+"review");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        }
        catch(Exception ex)
        {
            String af = ex.getMessage();
        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {
                String af2 = ex.getMessage();
            }
        }

        // Show response on activity
return text;
    }

    public void sendReview(Activity activity, final ReviewOption reviewOption, final OnGetOnlyDataCompleted<String> taskCompleted){

        try{

            sendSata(reviewOption);
            //String text = GetText(reviewOption);
            taskCompleted.onTaskCompleted("",false,"");
        }
        catch(Exception ex){
            taskCompleted.onTaskCompleted(ex.getMessage(),true,ex.getMessage());
        }

       /*RequestQueue requestQueue = Volley.newRequestQueue(activity);

       WebHelper webHelper = new WebHelper();

        String url = webHelper.getUrl() + "review";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new
                Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject jsonResults) {
                        taskCompleted.onTaskCompleted("", true, "");
                    }
                }, new Response.ErrorListener(){
            public void onErrorResponse(VolleyError arg0) {
                taskCompleted.onTaskCompleted("ERROR", true, arg0.getMessage());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("product_id",reviewOption.getProductId());
                params.put("name", reviewOption.getName());
                params.put("cost", String.valueOf(reviewOption.getPrice()));
                params.put("category",reviewOption.getCategory());
                params.put("image_id", reviewOption.getImage());

                float[] ratings = reviewOption.getRatings();

                for(int i = 0; i<ratings.length;i++){
                    float rating = ratings[i];
                    params.put("attribute_" + Integer.toString(i+1),String.valueOf(rating));
                }

                    /*if(ratings.length<6){
                        params.put("attribute_6","");
                    }*/

              /*  params.put("likes","0");
                params.put("dislikes","0");

                FirebaseAuth auth = FirebaseAuth.getInstance();

                FirebaseUser user = auth.getCurrentUser();

                if(user != null){
                    params.put("author",user.getEmail());
                }

                return params;


            }
        };

        int socketTimeout = 500000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        request.setRetryPolicy(policy);

        requestQueue.add(request);*/





    }

    public void getCategoryOptions(Activity activity, final OnGetDataTaskCompleted taskCompleted, String productId){

       final ArrayList<CategoryOption> categoryOptions = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(activity);


        WebHelper webHelper = new WebHelper();

        String url = webHelper.getUrl() + "review/" + productId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray productAttributes = response.getJSONArray("attributes");
                    JSONObject productDesc = response.getJSONObject("data");

                    for (int i=0; i < productAttributes.length(); i++) {
                        String attributeName =  productAttributes.get(i).toString();

                       if(!attributeName.isEmpty()){
                           float rating = (float) productDesc.getDouble("attribute_" + Integer.toString((i+1)))/2;

                           CategoryOption categoryOption = new CategoryOption(Integer.toString(i), attributeName.toUpperCase(),rating);
                           categoryOptions.add(categoryOption);
                       }

                    }

                    taskCompleted.onTaskCompleted(categoryOptions,false,null);
                }
                catch(Exception ex){
                    taskCompleted.onTaskCompleted(categoryOptions,true,ex.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                taskCompleted.onTaskCompleted(new ArrayList<ProductOption>(),true,error.getMessage());

            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public void getProductOptions(Activity activity, final OnGetDataTaskCompleted taskCompleted,int page){

        final ArrayList<ProductOption> productOptions = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(activity);


        WebHelper webHelper = new WebHelper();

        String url = webHelper.getUrl() + "review";
        if(page>0){

            url += "?page="+page;

        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
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
