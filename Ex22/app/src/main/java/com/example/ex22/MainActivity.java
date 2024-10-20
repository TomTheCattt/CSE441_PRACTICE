package com.example.ex22;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnParse;
    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    String url = "https://api.androidhive.info/pizza/?format=xml";

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

        btnParse = findViewById(R.id.btnParse);
        lv = findViewById(R.id.lv);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        btnParse.setOnClickListener(v -> {
           LoadExampleTask task = new LoadExampleTask();
           task.execute();
        });
    }
    class LoadExampleTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                XMLParser myParser = new XMLParser();
                String xml = myParser.getXmlFromUrl(url);
                parser.setInput(new java.io.StringReader(xml));
                int eventType = -1;
                String nodeName;
                StringBuilder dataShow = new StringBuilder();
                while(eventType != XmlPullParser.END_DOCUMENT) {
                    eventType = parser.next();
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            nodeName = parser.getName();
                            if (nodeName.equals("id")) {
                                dataShow.append(parser.nextText()).append("-");
                            } else if (nodeName.equals("name")) {
                                dataShow.append(parser.nextText());
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            nodeName = parser.getName();
                            if (nodeName.equals("item")) {
                                list.add(dataShow.toString());
                                dataShow = new StringBuilder();
                            }
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

