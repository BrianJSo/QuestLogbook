package com.mobdeve.caim_sob.questlogbook;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private QuestAdapter questAdapter;
    private Boolean isTemplate = false;

    public SwipeToDeleteCallback(QuestAdapter adapter, Boolean isTemplate) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.questAdapter = adapter;
        this.isTemplate = isTemplate;
    }

    public SwipeToDeleteCallback(QuestAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.START | ItemTouchHelper.END);
        this.questAdapter = adapter;
        this.isTemplate = isTemplate;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();

        Collections.swap(questAdapter.getData(), fromPosition, toPosition);
        recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        this.questAdapter.deleteQuest(position, isTemplate);
    }
}
