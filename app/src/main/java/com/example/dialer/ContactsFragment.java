package com.example.dialer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContactsFragment extends Fragment {
    private RecyclerView contactRecyclerView;
    private ContactListAdapter contactAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        contactRecyclerView = view.findViewById(R.id.contactRecyclerView);
        contactAdapter = new ContactListAdapter(view.getContext(), MainActivity.DIALER.getContacts());
        contactRecyclerView.setAdapter(contactAdapter);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }

}
