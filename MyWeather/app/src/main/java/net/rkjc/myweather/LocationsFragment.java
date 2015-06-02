package net.rkjc.myweather;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Created by rkjcx on 6/1/2015.
 */

public class LocationsFragment extends Fragment   {
    @Override
    public View onCreateView(LayoutInflater
                                     inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.locations_layout, container, false);
        Button button = (Button) v.findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity currentActivity = (MainActivity) getActivity();
                currentActivity.showWeatherFragment();
            }
        });
        return v;
    }
}
