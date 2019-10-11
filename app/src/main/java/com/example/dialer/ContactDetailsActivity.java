package com.example.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactDetailsActivity extends AppCompatActivity {
    private static final String CONTACT_NAME = "contact_name";
    private static final String CONTACT_NUMBERS = "contact_numbers";
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        String name = getIntent().getStringExtra(CONTACT_NAME);
        Set<String> numbers = new HashSet<>(getIntent().getStringArrayListExtra(CONTACT_NUMBERS));
        contact = new Contact(name, numbers);
        createTabLayout();
        TextView contactName = findViewById(R.id.contactName);
        contactName.setText(name);
    }

    private void createTabLayout() {
        TabLayout tabLayout = findViewById(R.id.contact_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.numbers));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.recents));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = findViewById(R.id.contact_pager);
        final ContactPagerAdapter adapter = new ContactPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),contact);
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
}
