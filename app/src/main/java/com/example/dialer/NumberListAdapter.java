package com.example.dialer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NumberListAdapter extends RecyclerView.Adapter<NumberListAdapter.NumberViewHolder> {
    public final List<String> itemsList;
    private LayoutInflater inflater;

    public NumberListAdapter(Context context, List<String> items) {
        inflater = LayoutInflater.from(context);
        this.itemsList = items;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = inflater.inflate(R.layout.number_list_item, parent, false);
        return new NumberViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        String current = itemsList.get(position);
        holder.numberItemView.setText(current);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView numberItemView;
        final NumberListAdapter adapter;

        public NumberViewHolder(@NonNull View itemView, NumberListAdapter adapter) {
            super(itemView);
            numberItemView = itemView.findViewById(R.id.number);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            String item = itemsList.get(position);
            Intent intent = new Intent(v.getContext(), InCallActivity.class);
            intent.putExtra("phone_number", item);
            v.getContext().startActivity(intent);
        }
    }
}
