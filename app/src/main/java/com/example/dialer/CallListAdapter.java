package com.example.dialer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CallListAdapter extends RecyclerView.Adapter<CallListAdapter.CallViewHolder> {
    public final List<Call> itemsList;
    private LayoutInflater inflater;

    public CallListAdapter(Context context, List<Call> itemsList) {
        this.itemsList = itemsList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CallListAdapter.CallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.call_list_item, parent, false);
        return new CallViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CallListAdapter.CallViewHolder holder, int position) {
        Call call = itemsList.get(position);
        Contact contact = MainActivity.DIALER.findContactInMap(call.getNumber());
        holder.callItemView.setText(call.toString());
        holder.contactNameItemView.setText(contact.toString());
        switch (call.getType()){
            case "D":
                holder.callType.setImageResource(R.drawable.ic_call_dialed); break;
            case "M":
                holder.callType.setImageResource(R.drawable.ic_call_missed); break;
            case "B":
                holder.callType.setImageResource(R.drawable.ic_call_busy); break;
            default:
        }
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class CallViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView callItemView;
        public final TextView contactNameItemView;
        public final ImageView callType;
        final CallListAdapter adapter;

        public CallViewHolder(@NonNull View itemView, CallListAdapter adapter) {
            super(itemView);
            this.callItemView = itemView.findViewById(R.id.call);
            this.contactNameItemView = itemView.findViewById(R.id.contactNameWithNumber);
            this.callType = itemView.findViewById(R.id.ivCallType);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Call item = itemsList.get(position);
            Intent intent = new Intent(v.getContext(), InCallActivity.class);
            intent.putExtra("phone_number", item.getNumber());
            v.getContext().startActivity(intent);
        }
    }
}
