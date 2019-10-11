package com.example.dialer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContactsFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contacts, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        RecyclerView contactRecyclerView = view.findViewById(R.id.contactRecyclerView);
        ContactListAdapter contactAdapter = new ContactListAdapter(view.getContext(), MainActivity.DIALER.getContacts());
        contactRecyclerView.setAdapter(contactAdapter);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        contactRecyclerView.addItemDecoration(new DividerItemDecoration(contactRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_contacts, contactRecyclerView, false);
    }
}
