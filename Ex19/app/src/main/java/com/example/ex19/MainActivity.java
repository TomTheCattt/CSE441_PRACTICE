package com.example.ex19;

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
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnParse;
    ListView lv;
    ArrayList<String> list;
    ArrayAdapter adapter;

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
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        btnParse.setOnClickListener(v -> {
            try {
                InputStream inputStream = getAssets().open("employee.xml");
                XmlPullParserFactory fc = XmlPullParserFactory.newInstance();
                XmlPullParser parser = fc.newPullParser();
                parser.setInput(inputStream,null);
                int eventType = -1;
                String nodeName;
                StringBuilder dataShow = new StringBuilder();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    eventType = parser.next();
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            nodeName = parser.getName();
                            switch (nodeName) {
                                case "employee":
                                    dataShow.append(parser.getAttributeValue(0)).append("-");
                                    dataShow.append(parser.getAttributeValue(1)).append("-");
                                    break;
                                case "name":
                                    parser.next();
                                    dataShow.append(parser.getText()).append("-");
                                    break;
                                case "phone":
                                    parser.nextText();
                                    break;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            nodeName = parser.getName();
                            if (nodeName.equals("employee")) {
                                list.add(dataShow.toString());
                                adapter.notifyDataSetChanged();
                                dataShow = new StringBuilder();
                            }
                            break;
                    }
                    adapter.notifyDataSetChanged();
                }
            } catch (IOException | XmlPullParserException e1) {
                throw new RuntimeException(e1);
            }
        });
    }
}