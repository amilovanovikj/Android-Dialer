package com.example.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class ContactDetailsActivity extends AppCompatActivity {
    private RecyclerView numberRecyclerView;
    private CallListAdapter numbersAdapter;
    private static final String CONTACT_NAME = "contact_name";
    private static final String CONTACT_NUMBERS = "contact_numbers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        String name = getIntent().getStringExtra(CONTACT_NAME);
        List<String> numbers = getIntent().getStringArrayListExtra(CONTACT_NUMBERS);
        numberRecyclerView = findViewById(R.id.numberRecyclerView);
        numbersAdapter = new CallListAdapter(this,numbers);
        numberRecyclerView.setAdapter(numbersAdapter);
        numberRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        TextView contactName = findViewById(R.id.contactName);
        contactName.setText(name);
    }
}
