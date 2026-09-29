package com.example.ryannaidoo_mobileappdevelopment.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.ryannaidoo_mobileappdevelopment.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PantryListFragment extends Fragment {
    public DatabaseHelper dbHelper;
    public PantryAdapter adapter;
    public RecyclerView recyclerView;
    public TextView emptyText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pantry_list, container, false);

        dbHelper = new DatabaseHelper(requireContext());
        recyclerView = view.findViewById(R.id.recycler_pantry);
        emptyText = view.findViewById(R.id.text_empty);
        ImageButton fab = view.findViewById(R.id.fab_add_ingredient);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new PantryAdapter(dbHelper.getAllPantryItems(), new PantryAdapter.OnItemActionListener() {
            @Override
            public void onItemClick(PantryItem item) {
                Intent intent = new Intent(requireContext(), AddEditIngredientActivity.class);
                intent.putExtra("item_id", item.id);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(PantryItem item) {
                dbHelper.deletePantryItem(item.id);
                refreshList();
            }
        });
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(v -> startActivity(new Intent(requireContext(), AddEditIngredientActivity.class)));
        refreshList();
        return view;
    }

    @Override
    public void onResume() {
    super.onResume();
    refreshList();
    }
    private void refreshList() {
        List<PantryItem> items = dbHelper.getAllPantryItems();
        adapter.updateData(items);
        if (items.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
