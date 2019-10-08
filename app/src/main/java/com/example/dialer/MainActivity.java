package com.example.dialer;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import java.text.ParseException;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    public static Dialer DIALER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDialer();
        createTabLayout();

        // TODO: Add functionality to the fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add_for_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void createDialer() {
        List<Contact> contacts = new ArrayList<>();
        fillContactList(contacts);
        DIALER = new Dialer(contacts);
        fillCallList();
    }

    private void fillCallList() {
        List<String> calls = new ArrayList<>();
        calls.add("070876500 13/07/2019 10:58 1:04:23 D");
        calls.add("070876501 14/07/2019 09:33 0:00:00 M");
        calls.add("070876510 13/07/2019 14:25 0:00:00 B");
        calls.add("070567890 15/07/2019 15:41 0:02:31 D");
        calls.add("070876530 16/07/2019 16:53 0:00:00 M");
        calls.add("070876540 13/07/2019 19:19 0:00:00 B");
        calls.add("070876550 12/07/2019 22:05 0:32:21 D");
        calls.add("070876560 13/07/2019 01:03 0:00:00 M");
        calls.add("070876570 14/07/2019 07:59 0:00:00 B");
        calls.add("070876580 11/07/2019 23:54 0:03:35 D");
        calls.add("070876590 12/07/2019 20:20 0:00:00 B");
        calls.add("070567810 11/07/2019 12:37 0:00:00 M");
        calls.add("070876550 10/07/2019 12:53 0:07:01 D");
        calls.add("070567820 10/07/2019 12:56 0:00:00 B");
        calls.add("070543654 10/07/2019 12:48 0:00:00 M");
        calls.add("070567822 14/07/2019 22:18 0:12:47 D");
        calls.add("070567851 11/07/2019 20:35 0:00:00 M");
        calls.add("070567870 12/07/2019 14:58 0:00:00 B");
        calls.add("070567891 11/07/2019 08:43 0:00:21 D");
        calls.add("070567840 12/07/2019 02:01 0:00:00 M");
        calls.add("070567801 15/07/2019 17:24 0:00:00 B");
        try {
            DIALER.readCalls(calls);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void createTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.contacts));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.calls));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void fillContactList(List<Contact> contacts) {
        for (int i = 0; i < 10; i++) {
            ArrayList<String> numbers1 = new ArrayList<>();
            ArrayList<String> numbers2 = new ArrayList<>();
            Random rand = new Random();
            int numbersCount = rand.nextInt(4);
            for (int j = 0; j <= numbersCount; j++) {
                numbers1.add(("0708765" + i) + j);
                numbers2.add(("0785678" + i) + j);
            }
            Contact c = new Contact(String.format("Contact %02d", i + 1), numbers1);
            contacts.add(c);
            c = new Contact("Contact " + (i + 11), numbers2);
            contacts.add(c);
        }
    }
}