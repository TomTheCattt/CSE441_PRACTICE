package com.example.ex23;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ListView lv;
    ArrayList<List> list;
    MyArrayAdapter adapter;
    String nodeName, title = "", link = "", description = "", des = "", urlStr = "", URL = "https://vnexpress.net/rss/the-gioi.rss";
    Bitmap mIcon_Val = null;

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
        list = new ArrayList<>();
        adapter = new MyArrayAdapter(this, R.layout.layout_listview, list);
        lv.setAdapter(adapter);
        LoadExampleTask task = new LoadExampleTask();
        task.execute();
    }

    class LoadExampleTask extends AsyncTask<Void, Void, ArrayList<List>> {
        @Override
        protected ArrayList<List> doInBackground(Void... voids) {
            ArrayList<List> arr = new ArrayList<>();
            try {
                XmlPullParserFactory fc = XmlPullParserFactory.newInstance();
                XmlPullParser parser = fc.newPullParser();
                XMLParser myParser = new XMLParser();
                String xml = myParser.getXmlFromUrl(URL);
                parser.setInput(new StringReader(xml));
                int eventType = -1;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    eventType = parser.next();
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            nodeName = parser.getName();
                            if (nodeName.equals("title")) {
                                title = parser.nextText();
                            } else if (nodeName.equals("link")) {
                                link = parser.nextText();
                            } else if (nodeName.equals("description")) {
                                description = parser.nextText();
                                try {
                                    // Extract URL of the image
                                    int start = description.indexOf("src=\"") + 5;
                                    int end = description.indexOf("\"", start);
                                    if (start != -1 && end != -1) {
                                        urlStr = description.substring(start, end);
                                        des = description.substring(description.indexOf("</br>") + 5);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    if (!urlStr.isEmpty()) {
                                        mIcon_Val = downloadBitmap(urlStr);  // Load bitmap from URL
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            nodeName = parser.getName();
                            if (nodeName.equals("item")) {
                                arr.add(new List(mIcon_Val, title, des, link));
                                mIcon_Val = null; // Reset the icon for the next item
                            }
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return arr;
        }

        @Override
        protected void onPostExecute(ArrayList<List> lists) {
            super.onPostExecute(lists);
            adapter.clear();
            adapter.addAll(lists);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapter.clear();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        // Helper function to download the bitmap asynchronously
        private Bitmap downloadBitmap(String imageUrl) {
            try {
                URL url = new URL(imageUrl);
                return BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
