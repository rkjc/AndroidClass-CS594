package net.rkjc.myweather;

/**
 * Created by rkjcx on 6/1/2015.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private List<RowModel> data;
    private static LayoutInflater inflater=null;
    Holder holder;

    public CustomAdapter(Context context, List<RowModel> data){
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View rowView = null;
        if(view == null){
            rowView = inflater.inflate(R.layout.row, null);
            holder = new Holder();

            holder.tv = (TextView)rowView.findViewById(R.id.rowLabel);
            rowView.setTag(holder);

        }else{
            rowView = view;
        }
        //fill row with data
        RowModel rm = data.get(i);
        holder = (Holder)rowView.getTag();

        holder.tv.setText(rm.getText());
        holder.iv.setImageDrawable(rm.getImage());

        return rowView;
    }

    private class Holder{
        TextView tv;  //this needs to include a few data items
        ImageView iv;  //for the weather icon
    }


    @Override
    public int getCount() {
        if(data.size()<=0)
            return 1;
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
