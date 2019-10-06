package com.example.dialer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {
    public final List<Contact> itemsList;
    private LayoutInflater inflater;

    public ContactListAdapter(Context context, List<Contact> items) {
        inflater = LayoutInflater.from(context);
        this.itemsList = items;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.contact_list_item, parent, false);
        return new ContactViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.ContactViewHolder holder, int position) {
        Contact current = itemsList.get(position);
        holder.contactItemView.setText(current.toString());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView contactItemView;
        final ContactListAdapter adapter;

        public ContactViewHolder(@NonNull View itemView, ContactListAdapter adapter) {
            super(itemView);
            contactItemView = itemView.findViewById(R.id.contact);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Contact item = itemsList.get(position);
            Intent intent = new Intent(v.getContext(), ContactDetailsActivity.class);
            intent.putExtra("contact_name", item.getName());
            ArrayList<String> contactNumbers = new ArrayList<>(item.getNumbers());
            intent.putStringArrayListExtra("contact_numbers", contactNumbers);
            v.getContext().startActivity(intent);
        }
    }
}