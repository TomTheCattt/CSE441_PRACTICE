package com.example.ex24;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    TextView txtDate;
    ArrayList<TyGia> dsTyGia;
    MyArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lv = findViewById(R.id.lv);
        dsTyGia = new ArrayList<>();
        adapter = new MyArrayAdapter(this, R.layout.item, dsTyGia);
        lv.setAdapter(adapter);

        // Execute the AsyncTask to fetch the data
        new TyGiaTask().execute();
    }

    // AsyncTask class to fetch data
    class TyGiaTask extends AsyncTask<Void, Void, ArrayList<TyGia>> {
        @Override
        protected ArrayList<TyGia> doInBackground(Void... voids) {
            ArrayList<TyGia> dsTyGia = new ArrayList<>();
            try {
                // Fetch and parse JSON data
                URL url = new URL("https://dongabank.com.vn/exchange/export");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json; charset=UTF-8");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible )");
                conn.setRequestProperty("Accept", "*/*");
                InputStream inputStream = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String line = br.readLine();
                StringBuilder builder = new StringBuilder();
                while (line != null) {
                    builder.append(line);
                    line = br.readLine();
                }

                // Convert string to JSON object
                String json = builder.toString();
                json = json.replace("(","");
                json = json.replace(")","");
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("items");

                // Iterate through JSON array and extract data
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject item = jsonArray.getJSONObject(i);
                    TyGia tyGia = new TyGia();
                    tyGia.setType(item.getString("type"));

                    // If image URL is present, fetch and set the bitmap
                    if(item.has("imageurl")) {
                        tyGia.setImageurl(item.getString("imageurl"));
                        URL imageUrl = new URL(item.getString("imageurl"));
                        HttpURLConnection imageConn =(HttpURLConnection) imageUrl.openConnection();
                        imageConn.setRequestMethod("GET");
                        imageConn.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible )");
                        imageConn.setRequestProperty("Accept", "*/*");
                        InputStream imageInputStream = imageConn.getInputStream();
                        tyGia.setBitmap(android.graphics.BitmapFactory.decodeStream(imageInputStream));
                    }

                    // Extract other data
                    if(item.has("muatienmat")) {
                        tyGia.setMuatienmat(item.getString("muatienmat"));
                    }
                    if(item.has("muack")) {
                        tyGia.setMuack(item.getString("muack"));
                    }
                    if(item.has("bantienmat")) {
                        tyGia.setBantienmat(item.getString("bantienmat"));
                    }
                    if(item.has("banck")) {
                        tyGia.setBanck(item.getString("banck"));
                    }

                    // Add to the list
                    dsTyGia.add(tyGia);
                }

                Log.d("JSON", json);

            } catch(Exception e) {
                e.printStackTrace();
            }

            return dsTyGia;
        }

        @Override
        protected void onPostExecute(ArrayList<TyGia> dsTyGia) {
            super.onPostExecute(dsTyGia);
            // Update the adapter with new data
            adapter.clear();
            adapter.addAll(dsTyGia);
            adapter.notifyDataSetChanged();
        }
    }
}
