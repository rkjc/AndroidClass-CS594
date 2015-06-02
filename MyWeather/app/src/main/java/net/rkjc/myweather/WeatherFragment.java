package net.rkjc.myweather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by rkjcx on 5/25/2015.
 */
public class WeatherFragment extends Fragment implements AdapterView.OnItemClickListener{
    public static final String TAG = " WeatherFragment ";

    List<RowModel> data;
    CustomAdapter adapter;

    String[] dayTemp;
    String cityName = "";
    String CountryName = "";
    private TextView cityNameView;
    private TextView CountryNameView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView onStart called");
        View view = inflater.inflate(R.layout.weather_layout, container, false);

        Button button1 = (Button) view.findViewById(R.id.button1);
        Button button2 = (Button) view.findViewById(R.id.button2);

        cityNameView = (TextView) view.findViewById(R.id.cityTextView);
        CountryNameView = (TextView) view.findViewById(R.id.countryTextView);

        MainActivity activity = (MainActivity)this.getActivity();
        cityName = activity.getCity();
        CountryName = activity.getCountry();
        dayTemp = activity.getDayTemp();

        Log.i(TAG, " onCreateView cityName=" + cityName);

        cityNameView.setText(cityName);
        CountryNameView.setText(CountryName);


        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity currentActivity = (MainActivity) getActivity();
                currentActivity.showLocationFragment();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity currentActivity = (MainActivity) getActivity();
                currentActivity.showWeatherFragment();
            }
        });

        //get a list of weather by days
        //send weather day list to weatherListView
        ListView lv =(ListView)view.findViewById(R.id.weatherListView);

        if(dayTemp != null && dayTemp.length != 0){
            lv.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, dayTemp));
        }

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getActivity(), "onItemClicked", Toast.LENGTH_LONG).show();
        Log.i(TAG, "onItemClick");

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "Fragment onActivityCreated called");
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onStart() {
        Log.i(TAG, "Fragment onStart called");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "Fragment onResume called");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "Fragment onPause called");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "Fragment onStop called");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "Fragment onDestroyView called");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Fragment onDestroy called");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "Fragment onDetach called");
        super.onDetach();
    }

}
