package com.example.mediaplayerproject;

import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MyItemTouchHelper extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter itemTouchHelperAdapter;

    public MyItemTouchHelper(ItemTouchHelperAdapter itemTouchHelperAdapter) {
        this.itemTouchHelperAdapter = itemTouchHelperAdapter;
    }


    @Override
    public boolean isLongPressDragEnabled() {
        Log.d("Lifecycle: ", "isLongPressDragEnabled MyItemTouchHelper");
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        Log.d("Lifecycle: ", "isItemViewSwipeEnabled MyItemTouchHelper");
        return true;
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        Log.d("Lifecycle: ", "clearView MyItemTouchHelper");
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.white));// returns to normal color after press release
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        Log.d("Lifecycle: ", "onSelectedChanged MyItemTouchHelper");
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState == ItemTouchHelper.ACTION_STATE_DRAG){
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY); // when pressing item color is light grey
        }
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        Log.d("Lifecycle: ", "getMovementFlags MyItemTouchHelper");
        final int dragFlags  = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags , swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        Log.d("Lifecycle: ", "onMove MyItemTouchHelper");
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();

        itemTouchHelperAdapter.onItemMove(fromPosition,toPosition);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d("Lifecycle: ", "onSwiped MyItemTouchHelper");
        itemTouchHelperAdapter.onItemSwiped(viewHolder.getAdapterPosition());

    }
}
