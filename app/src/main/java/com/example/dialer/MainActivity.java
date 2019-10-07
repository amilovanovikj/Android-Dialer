package com.example.dialer;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    public static Dialer DIALER;
    private RecyclerView contactRecyclerView;
    private ContactListAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Contact> contacts = new ArrayList<>();
        fillContactList(contacts);
        DIALER = new Dialer(contacts);
        contactRecyclerView = findViewById(R.id.contactRecyclerView);
        contactAdapter = new ContactListAdapter(this, DIALER.getContacts());
        contactRecyclerView.setAdapter(contactAdapter);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(this));


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
