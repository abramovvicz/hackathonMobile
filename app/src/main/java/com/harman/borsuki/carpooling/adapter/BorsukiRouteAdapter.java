package com.harman.borsuki.carpooling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.harman.borsuki.carpooling.R;
import com.harman.borsuki.carpooling.models.BorsukiRoute;

import java.util.List;

public class BorsukiRouteAdapter extends ArrayAdapter<BorsukiRoute> {

    public BorsukiRouteAdapter(Context context, List<BorsukiRoute> borsukiRouteList) {
        super(context, 0, borsukiRouteList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        BorsukiRoute borsukiRoute = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_borsuki_route, parent, false);
        }
        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.tvOrigin);
        TextView tvHome = convertView.findViewById(R.id.tvDestination);
        TextView tvDriverName = convertView.findViewById(R.id.text_driver_name);

        // Populate the data into the template view using the data object
        tvName.setText(borsukiRoute.getStartingName());
        tvHome.setText(borsukiRoute.getDestinationName());
        tvDriverName.setText(borsukiRoute.getDriverName());
        // Return the completed view to render on screen
        return convertView;
    }
}
