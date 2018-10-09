package facens.worthit.interfaces;

import java.util.ArrayList;

import facens.worthit.model.ProductOption;

public interface OnGetDataTaskCompleted<T> {
    public void onTaskCompleted(ArrayList<T> list, boolean error, String message);
}


