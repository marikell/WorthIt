package facens.worthit.helper;

import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

public class WebHelper {

    public String getUrl(){
        return "http://192.168.0.28:5000/";
    }

    public Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, null);
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
