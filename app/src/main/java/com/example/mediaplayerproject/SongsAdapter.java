package com.example.mediaplayerproject;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import java.net.URI;
import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongsViewHolder> { // implements ItemTouchHelperAdapter{

    private Context context;
    private List<Song> songsList;
    private static MySongListener songListener;
    //private ItemTouchHelper itemTouchHelper;


    interface MySongListener {
        void onSongClicked(int position, View view);
    }
   /* public void setContext(Context context){
        this.context = context;
    }
*/
    public void setSongListener(MySongListener songListener) {
        this.songListener = songListener;
    }

    public SongsAdapter(List<Song> songsList) {
        //Log.d("Lifecycle: ", "SongsAdapter CONSTRUCTOR");
        this.songsList = songsList;
    }


    public static class SongsViewHolder extends RecyclerView.ViewHolder {//implements View.OnTouchListener, GestureDetector.OnGestureListener {

        CardView cardView;
        TextView songName;
        TextView songArtist;
        TextView songDuration;
        ImageView songPicture;
        //GestureDetector gestureDetector;

        public SongsViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewAppCard);
            songName = itemView.findViewById(R.id.textViewSongName);
            songArtist = itemView.findViewById(R.id.textViewSongArtist);
            //songDuration = itemView.findViewById(R.id.textViewSongDuration);
            songPicture = itemView.findViewById(R.id.imageViewSongPic);

            //gestureDetector = new GestureDetector(itemView.getContext(),this);

            //itemView.setOnTouchListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (songListener != null)
                        songListener.onSongClicked(getAdapterPosition(), view);

                }
            });


        }

/**
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("Lifecycle: ", "onDown SongsAdapter");
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d("Lifecycle: ", "onShowPress SongsAdapter");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("Lifecycle: ", "onSingleTapUp SongsAdapter");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("Lifecycle: ", "onScroll SongsAdapter");
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("Lifecycle: ", "onLongPress SongsAdapter");

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("Lifecycle: ", "onFling SongsAdapter");
            return false;
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.d("Lifecycle: ", "onTouch SongsAdapter");
            gestureDetector.onTouchEvent(event);
            return true;
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        @NonNull
        @Override
        public SongsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.songs_recycler_view, parent, false);
            SongsViewHolder songsViewHolder = new SongsViewHolder(view);
            return songsViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SongsViewHolder holder, int position) {
            Song song = songsList.get(position);
            //SongsViewHolder songsViewHolder = (SongsViewHolder)holder;
            holder.songName.setText(song.getName());
            holder.songArtist.setText(song.getArtist());
            holder.songDuration.setText(song.getDuration());
            holder.songPicture.setImageResource(song.getPictureResId());
        }

       @Override
        public int getItemCount() {
            return songsList.size();
        }


    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Log.d("Lifecycle: ", "onItemMove SongsAdapter");

        Song fromSong = songsList.get(fromPosition);
        songsList.remove(fromSong);
        songsList.add(toPosition,fromSong);
        notifyItemMoved(fromPosition,toPosition);

    }

    @Override
    public void onItemSwiped(int position) {
     // need to create dialog like in main activity
        Log.d("Lifecycle: ", "onItemSwiped SongsAdapter");
        songsList.remove(position);
        notifyItemRemoved(position);
    }

    public void setTouchHelper(ItemTouchHelper touchHelper){
        Log.d("Lifecycle: ", "setTouchHelper SongsAdapter");
        this.itemTouchHelper = touchHelper;
    }
    */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }

    @NonNull
    @Override
    public SongsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.songs_recycler_view, parent, false);
        SongsViewHolder songsViewHolder = new SongsViewHolder(view);
        return songsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SongsViewHolder holder, int position) {

        Song song = songsList.get(position);
        holder.songName.setText(song.getName());
        holder.songArtist.setText(song.getArtist());
        //holder.songDuration.setText(song.getDuration());
        Glide.with(holder.songPicture.getContext()).load(song.getPictureLink()).fitCenter().into(holder.songPicture);
        //holder.songPicture.setImageResource(song.getPictureLink());

    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


}
