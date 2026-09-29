package com.example.ryannaidoo_mobileappdevelopment.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ryannaidoo_mobileappdevelopment.R;
import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder>{
    public interface OnItemActionListener {
        void onItemClick(PantryItem item);

        void onDeleteClick(PantryItem item);
    }
    public List<PantryItem> items;
    public OnItemActionListener listener;

    public PantryAdapter(List<PantryItem> items, OnItemActionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public void updateData(List<PantryItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pantry, parent, false);
        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        PantryItem item = items.get(position);
        holder.name.setText(item.name);
        double q = item.quantity;
        String qStr = (q == Math.floor(q)) ? String.valueOf((long) q) : String.valueOf(q);
        String qtyText = qStr + (item.unit != null && !item.unit.isEmpty() ? " " + item.unit : "");
        holder.quantity.setText(qtyText);

        if (item.expiryDate != null && !item.expiryDate.isEmpty()) {
            holder.expiry.setText("Expires: " + item.expiryDate);
            holder.expiry.setVisibility(View.VISIBLE);
        }
        else {
            holder.expiry.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
        holder.deleteButton.setOnClickListener(v -> listener.onDeleteClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class PantryViewHolder extends RecyclerView.ViewHolder {
        public TextView name, quantity, expiry;
        public ImageButton deleteButton;

    PantryViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.text_item_name);
        quantity = itemView.findViewById(R.id.text_item_quantity);
        expiry = itemView.findViewById(R.id.text_item_expiry);
        deleteButton = itemView.findViewById(R.id.button_delete);
    }
}
}




