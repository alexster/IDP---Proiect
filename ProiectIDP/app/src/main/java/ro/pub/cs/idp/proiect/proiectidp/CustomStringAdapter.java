package ro.pub.cs.idp.proiect.proiectidp;

import android.content.Context;
import android.net.Network;
import android.net.wifi.ScanResult;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VIRTUAL STEPS on 27.03.2017.
 */


public class CustomStringAdapter extends ArrayAdapter<WifiData> {

    private ArrayList<WifiData> dataSet;
    private Context context;

    private static class ViewHolder {
        TextView ssid, bssid, level, freq, time;
    }

    public CustomStringAdapter(ArrayList<WifiData> data, Context c) {
        super(c,R.layout.main_listview, data);
        this.dataSet = data;
        this.context = c;

    }
    private int lastPosition = -1;

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        WifiData dataModel = dataSet.get(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.main_listview, parent, false);
            viewHolder.ssid = (TextView) convertView.findViewById(R.id.ssid);
            viewHolder.bssid = (TextView) convertView.findViewById(R.id.bssid);
            viewHolder.level = (TextView) convertView.findViewById(R.id.level);
            viewHolder.freq = (TextView) convertView.findViewById(R.id.freq);
            viewHolder.time= (TextView) convertView.findViewById(R.id.time);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        //Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);
        lastPosition = position;

        viewHolder.ssid.setText(dataModel.SSID);
        viewHolder.bssid.setText(dataModel.BSSID);
        viewHolder.level.setText(String.valueOf(dataModel.level) + " dB");
        viewHolder.freq.setText(String.valueOf(dataModel.frequency) + " MHz");
        viewHolder.time.setText(dataModel.time);
        //Log.d("adapter", dataModel.SSID + " " + position);
        // Return the completed view to render on screen
        return convertView;
    }
}