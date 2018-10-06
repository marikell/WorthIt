package facens.worthit.helper;

import java.util.ArrayList;

import facens.worthit.ImageType;
import facens.worthit.model.CategoryOption;
import facens.worthit.model.ProductOption;
import facens.worthit.model.UserOption;

public class DataHelper {

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

    public ArrayList<ProductOption> getProductOptions(){
       return new ArrayList<ProductOption>(){
           {
                add(new ProductOption("1", "Asus Zenfone 5","galaxys8_img", 2000, 5));
               add(new ProductOption("2", "Iphone X","galaxys8_img", 3000, 2));
           }
       };
    }

}
