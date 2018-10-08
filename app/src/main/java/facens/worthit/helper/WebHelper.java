package facens.worthit.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class WebHelper {

    public String getUrl(){
        return "http://192.168.0.102:5000/";
    }

    public Bitmap LoadImageFromWebOperations(String url) {
        try {
            URL u = new URL(url);
            return BitmapFactory.decodeStream((InputStream)u.getContent());
        } catch (IOException e) {
            return null;
        }
    }
}
