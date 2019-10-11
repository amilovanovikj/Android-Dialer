package com.example.dialer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecentsFragment extends Fragment {
    private View view;
    private Contact contact;

    public RecentsFragment(Contact c){
        this.contact = c;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recents, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        RecyclerView recentRecyclerView = view.findViewById(R.id.recentRecyclerView);
        CallListAdapter callAdapter = new CallListAdapter(view.getContext(), MainActivity.DIALER.getCallsFromContact(contact));
        recentRecyclerView.setAdapter(callAdapter);
        recentRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recentRecyclerView.addItemDecoration(new DividerItemDecoration(recentRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_calls, recentRecyclerView, false);
    }
}
