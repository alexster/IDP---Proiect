package ro.pub.cs.idp.proiect.proiectidp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class SavedActivity extends AppCompatActivity {

    Button btnBack;
    ListView listView;
    SQLiteDatabase db;

    ArrayList<WifiData> data;
    CustomStringAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(SavedActivity.this, MainActivity.class);
                //startActivity(i);
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.savedListView);

        db = openOrCreateDatabase("data.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS data(ssid TEXT, bssid TEXT, level INT, frequency INT, time DATETIME CURRENT_TIMESTAMP)");

        data = new ArrayList<WifiData>();
        adapter = new CustomStringAdapter(data, getApplicationContext());
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Cursor cursor = db.rawQuery("select * from data", null);

        while(cursor.moveToNext()) {
            WifiData mydata = new WifiData();
            mydata.SSID = cursor.getString(0);
            mydata.BSSID = cursor.getString(1);
            mydata.level = cursor.getInt(2);
            mydata.frequency = cursor.getInt(3);
            mydata.time = cursor.getString(4);
            data.add(mydata);

        }
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
