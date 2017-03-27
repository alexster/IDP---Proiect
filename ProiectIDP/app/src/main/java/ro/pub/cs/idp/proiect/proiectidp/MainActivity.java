package ro.pub.cs.idp.proiect.proiectidp;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    Button btnSave, btnSettings;
    ListView listView;


    CustomAdapter adapter;

    WifiManager mainWifi;
    WifiReceiver receiverWifi;
    List<ScanResult> wifiList = new ArrayList<>();
    ArrayList<ScanResult> networks = new ArrayList<ScanResult>();

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSettings = (Button) findViewById(R.id.btnSettings);
        listView = (ListView) findViewById(R.id.listView);
        //networks = new ArrayList<String>();

        //if(adapter) Toast.makeText(getApplicationContext(),"ada[ter plm", Toast.LENGTH_LONG).show();


        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        }

        db = openOrCreateDatabase("data.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS data(ssid TEXT, bssid TEXT, level INT, frequency INT, time TEXT)");

        mainWifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (mainWifi.isWifiEnabled() == false)
        {
            // If wifi disabled then enable it
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled",
                    Toast.LENGTH_LONG).show();

            mainWifi.setWifiEnabled(true);
        }
        receiverWifi = new WifiReceiver();
        registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        mainWifi.startScan();

        adapter = new CustomAdapter(networks, getApplicationContext());
        listView.setAdapter(adapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SavedActivity.class);
                startActivity(i);

            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiverWifi);
        db.close();
    }

    class WifiReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            wifiList = mainWifi.getScanResults();
            //Toast.makeText(MainActivity.this, "found " + wifiList.size() + " networks", Toast.LENGTH_LONG).show();

            networks.clear();

            for(int i = 0 ; i < wifiList.size() ; i++) {
                networks.add(wifiList.get(i));

                ContentValues row = new ContentValues();
                row.put("ssid", wifiList.get(i).SSID);
                row.put("bssid", wifiList.get(i).BSSID);
                row.put("level", wifiList.get(i).level);
                row.put("frequency", wifiList.get(i).frequency);
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+2:00"));
                Date curr = cal.getTime();
                DateFormat d = new SimpleDateFormat("HH:mm:ss a - d/M");
                row.put("time", d.format(new Date()));
                db.insert("data",null,row);
            }

            adapter.notifyDataSetChanged();
            mainWifi.startScan();
        }
    }


}
