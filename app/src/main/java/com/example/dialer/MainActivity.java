package com.example.dialer;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

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
        List<String> numbers = Collections.singletonList("070226615");
        contacts.add(new Contact("Andrej Milovanovikj", numbers));
    }
}