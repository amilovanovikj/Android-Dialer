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

public class CallListAdapter extends RecyclerView.Adapter<CallListAdapter.CallViewHolder> {
    public final List<String> itemsList;
    private LayoutInflater inflater;

    public CallListAdapter(Context context, List<String> items) {
        inflater = LayoutInflater.from(context);
        this.itemsList = items;
    }

    @NonNull
    @Override
    public CallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = inflater.inflate(R.layout.number_list_item, parent, false);
        return new CallViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CallListAdapter.CallViewHolder holder, int position) {
        String current = itemsList.get(position);
        holder.callItemView.setText(current);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    class CallViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView callItemView;
        final CallListAdapter adapter;

        public CallViewHolder(@NonNull View itemView, CallListAdapter adapter) {
            super(itemView);
            callItemView = itemView.findViewById(R.id.call);
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
