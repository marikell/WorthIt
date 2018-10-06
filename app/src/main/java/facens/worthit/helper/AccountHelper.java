package facens.worthit.helper;

import java.util.ArrayList;

import facens.worthit.ImageType;
import facens.worthit.model.UserOption;

public class AccountHelper {

   public boolean isLoggedIn(){
       return true;
   }

    public static ArrayList<UserOption> getUserOptions(){
        return new ArrayList<UserOption>(){{
            add(new UserOption(1, "Minhas Reviews", ImageType.MY_REVIEWS));
            add(new UserOption(2, "Logout", ImageType.LOGOUT));
        }};
    }

}
