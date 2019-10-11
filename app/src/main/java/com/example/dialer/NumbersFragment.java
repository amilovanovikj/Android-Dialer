package com.example.dialer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Set;

public class NumbersFragment extends Fragment {
    private View view;
    private Set<String> numbers;

    public NumbersFragment(Set<String> numbers){
        this.numbers = numbers;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_numbers, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        RecyclerView numberRecyclerView = view.findViewById(R.id.numberRecyclerView);
        NumberListAdapter numberAdapter = new NumberListAdapter(view.getContext(), numbers);
        numberRecyclerView.setAdapter(numberAdapter);
        numberRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        numberRecyclerView.addItemDecoration(new DividerItemDecoration(numberRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_numbers, numberRecyclerView, false);
    }
}
