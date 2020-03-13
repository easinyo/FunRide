package com.dubinostech.rideshareapp.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dubinostech.rideshareapp.R;
import com.dubinostech.rideshareapp.repository.Api.Responses.SearchResponse;

import java.util.List;

public class SearchListAdapter extends ArrayAdapter<SearchResponse> {

    private int layoutResourceId;
    private Context activity;

    private TextView departure_date, departure_city, departure_address, arrival_city, arrival_address, fare, available_spot;

    private List<SearchResponse> rides;
    private static LayoutInflater inflater = null;

    public SearchListAdapter(Context activity, int textViewResourceId, List<SearchResponse> rides) {
        super(activity, textViewResourceId, rides);
        try {
            this.activity = activity;
            this.rides = rides;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return rides.size();
    }

    public SearchResponse getItem(SearchResponse position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        private TextView departure_date, departure_city, departure_address, arrival_city, arrival_address, fare, available_spot;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.searchresult_list_content, null);
                holder = new ViewHolder();

                holder.departure_date = vi.findViewById(R.id.departure_date);
                holder.departure_address = vi.findViewById(R.id.departure_address);
                holder.departure_city = vi.findViewById(R.id.departure_city);
                holder.arrival_city = vi.findViewById(R.id.arrival_city);
                holder.arrival_address = vi.findViewById(R.id.arrival_address);
                holder.fare = vi.findViewById(R.id.fare);
                holder.available_spot = vi.findViewById(R.id.available_spot);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.departure_date.setText(rides.get(position).getDepartureDateTime());
            holder.departure_city.setText(rides.get(position).getDepartureCity());
            holder.departure_address.setText(rides.get(position).getDepartureAddress());
            holder.arrival_city.setText(rides.get(position).getArrivalCity());
            holder.arrival_address.setText(rides.get(position).getArrivalAddress());
            holder.fare.setText(rides.get(position).getFare());
            holder.available_spot.setText(rides.get(position).getDavailableSpot());


        } catch (Exception e) {


        }
        return vi;
    }
}