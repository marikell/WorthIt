package facens.worthit.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import facens.worthit.ImageType;
import facens.worthit.R;
import facens.worthit.model.UserOption;

public class UserOptionsAdapter extends ArrayAdapter<UserOption>{

    private final LayoutInflater mInflater;

    public UserOptionsAdapter(Context context, List<UserOption> userOptions){
        super(context, android.R.layout.simple_list_item_1);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setData(userOptions);
    }

    public void setData(List<UserOption> userOptions){
        clear();
        if(userOptions != null){
            for(UserOption userOption: userOptions){
                add(userOption);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View view;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.user_option, parent, false);
        } else {
            view = convertView;
        }

        UserOption userOption = getItem(position);
        ((TextView)view.findViewById(R.id.txtUserOption)).setText(userOption.getOption());

        ImageType userImgType = userOption.getImageType();

        int resID = 0;

        switch(userImgType){
            case MY_REVIEWS:
                resID = R.drawable.ic_format_list_bulleted_black_24dp;
                break;
            case LOGOUT:
                resID = R.drawable.ic_exit_to_app_black_24dp;
                break;
        }
        ((ImageView)view.findViewById(R.id.imgUserOption)).setImageResource(resID);

        return view;
    }
}
