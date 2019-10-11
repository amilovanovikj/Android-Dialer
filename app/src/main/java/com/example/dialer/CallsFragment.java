package com.example.dialer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CallsFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calls, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        RecyclerView callRecyclerView = view.findViewById(R.id.callRecyclerView);
        CallListAdapter callAdapter = new CallListAdapter(view.getContext(), MainActivity.DIALER.getAllCalls());
        callRecyclerView.setAdapter(callAdapter);
        callRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        callRecyclerView.addItemDecoration(new DividerItemDecoration(callRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_calls, callRecyclerView, false);
    }
}
