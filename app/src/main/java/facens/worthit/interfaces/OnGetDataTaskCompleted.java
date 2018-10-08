package facens.worthit.interfaces;

import java.util.ArrayList;

import facens.worthit.model.ProductOption;

public interface OnGetDataTaskCompleted {
    public void onTaskCompleted(ArrayList<ProductOption> list, boolean error, String message);
}
