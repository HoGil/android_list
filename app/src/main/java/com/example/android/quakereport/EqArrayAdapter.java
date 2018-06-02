package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gilho on 2/06/18.
 */

public class EqArrayAdapter extends ArrayAdapter<Earthquake> {


    public EqArrayAdapter(Activity context, ArrayList<Earthquake> earthquakeArrayList) {
        // Here, we initialise the ArrayAdapter's internal storage for the context and the list
        // the second argument is used when the ArrayAdapter is populating a single TextView
        // Because this is a custom adapter for two TextViews and an ImageView, the
        // adapter is not going to use this second argument, so it can be any value.
        super(context, 0, earthquakeArrayList);
    }

    // provides a view for an adapterview (listview, gridview etc)
    // @param position  The position in the list of data that should be displayed in the
    //                  list item view
    // @param convertView The recycled view to populate
    // @param parent The parent ViewGroup that is used for the inflation
    // @return The View for the position of the AdapterView

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // get the earthquake object located at this position in the list
        Earthquake currentEarthquake = getItem(position);

        // find the textview in the list_item.xml layout with the ID version_name
        TextView magTextView = (TextView)listItemView.findViewById(R.id.magnitude_view);
        // get the magnitude from the current eq object and set its name to the textview
        magTextView.setText(currentEarthquake.getMagnitute());

        // repeat the above steps for city and date
        TextView cityTextView = (TextView)listItemView.findViewById(R.id.location_view);
        cityTextView.setText(currentEarthquake.getCity());
        TextView dateTextView = (TextView)listItemView.findViewById(R.id.date_view);
        dateTextView.setText(currentEarthquake.getEventDate());

        return listItemView;



    }





}
