package facens.worthit.interfaces;

import java.util.ArrayList;

public interface OnGetOnlyDataCompleted <T>{
    public void onTaskCompleted(T object, boolean error, String message);

}
