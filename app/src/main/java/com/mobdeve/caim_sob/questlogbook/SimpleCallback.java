package com.mobdeve.caim_sob.questlogbook;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;

public class SimpleCallback extends ItemTouchHelper.SimpleCallback{

    private QuestAdapter questAdapter;
    private Boolean isTemplate = false;

    public SimpleCallback(QuestAdapter adapter, Boolean isTemplate) {
        super(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0);
        this.questAdapter = adapter;
        this.isTemplate = isTemplate;
    }

    public SimpleCallback(QuestAdapter adapter) {
        super(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0);
        this.questAdapter = adapter;
        this.isTemplate = isTemplate;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();

        Collections.swap(questAdapter.getData(), fromPosition, toPosition);
        recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
