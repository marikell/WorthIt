package facens.worthit.adapter;

import android.app.Activity;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import facens.worthit.R;

public class UserOptionsAdapter extends BaseAdapter{

    private final List<String> userOptions;
    private final Activity activity;


    public UserOptionsAdapter(List<String> userOptions, Activity activity) {
        this.userOptions = userOptions;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return userOptions.size();
    }

    @Override
    public Object getItem(int i) {
        return userOptions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = activity.getLayoutInflater().inflate(R.layout.fragment_profile, viewGroup, false);

        String option = userOptions.get(i);

        //pegando as referencias da view.

        //TextView txtUserOption = (TextView) view.findViewById(R.id.txtUserOption);

        //ImageView iconUserOption = (ImageView) view.findViewById(R.id.txtUserOption);

        //txtUserOption.setText(option);
        //iconUserOption.setImageResource(R.drawable.user_icon);

        return view;
    }
}
