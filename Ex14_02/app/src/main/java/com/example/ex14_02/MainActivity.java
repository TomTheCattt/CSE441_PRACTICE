package com.example.ex14_02;

import android.content.ClipData;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ex14_02.Models.Item;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edttim;
    ListView lv1, lv2, lv3;
    TabHost tabHost;
    ArrayList<Item> list1, list2, list3;
    myarrayAdapter myarray1, myarray2, myarray3;

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

        addControl();
        addEvent();
    }

    private void addEvent() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId.equalsIgnoreCase("tab1")) {
                    list1.clear();
                    list1.add(new Item("52300", "Em la ai toi la ai", 0));
                    list1.add(new Item("52600", "Chen dang", 1));
                    myarray1.notifyDataSetChanged();
                }
                if(tabId.equalsIgnoreCase("tab2")) {
                    list2.clear();
                    list2.add(new Item("57236", "Goi em o cuoi song Hong", 0));
                    list2.add(new Item("51548", "Say tinh", 0));
                    myarray2.notifyDataSetChanged();
                }
                if(tabId.equalsIgnoreCase("tab3")) {
                    list3.clear();
                    list3.add(new Item("57689", "Hat voi dong song", 1));
                    list3.add(new Item("58716", "Say tinh remix", 0));
                    myarray3.notifyDataSetChanged();
                }
            }
        });
    }


    private void addControl() {
        tabHost = findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("", getResources().getDrawable(R.drawable.search));
        tabHost.addTab(tab1);
        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2");
        tab2.setContent(R.id.tab1);
        tab2.setIndicator("", getResources().getDrawable(R.drawable.list));
        tabHost.addTab(tab2);
        TabHost.TabSpec tab3 = tabHost.newTabSpec("tab3");
        tab3.setContent(R.id.tab1);
        tab3.setIndicator("", getResources().getDrawable(R.drawable.favourite));
        tabHost.addTab(tab3);

        edttim = findViewById(R.id.edttim);
        lv1 = findViewById(R.id.lv1);
        lv2 = findViewById(R.id.lv2);
        lv3 = findViewById(R.id.lv3);
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        myarray1 = new myarrayAdapter(this, R.layout.listitem, list1);
        myarray2 = new myarrayAdapter(this, R.layout.listitem, list2);
        myarray3 = new myarrayAdapter(this, R.layout.listitem, list3);
        lv1.setAdapter(myarray1);
        lv2.setAdapter(myarray2);
        lv3.setAdapter(myarray3);
    }
}
