package com.example.mediaplayerproject;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.util.ArrayList;


public class MusicService extends Service implements MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener{

    MediaPlayer mediaPlayer = new MediaPlayer();
    ArrayList<Song> songs;
    int currentlyPlaying = 0;

    final int NOTIF_ID = 1;
    final String passSongList = "SONG_LIST";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.reset();

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        String channelId = "channel_id";
        String channelName = "Music channel";
        if(Build.VERSION.SDK_INT>=26){
        NotificationChannel channel = new NotificationChannel(channelId,channelName, NotificationManager.IMPORTANCE_HIGH);
        manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channelId);

        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.music_notif);

        Intent playIntent = new Intent(this,MusicService.class);
        playIntent.putExtra("command","play");
        PendingIntent playPendingIntent = PendingIntent.getService(this,0,playIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.play_btn,playPendingIntent);

        Intent pauseIntent = new Intent(this,MusicService.class);
        pauseIntent.putExtra("command","pause");
        PendingIntent pausePendingIntent = PendingIntent.getService(this,1,pauseIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.pause_btn,pausePendingIntent);

        Intent nextIntent = new Intent(this,MusicService.class);
        nextIntent.putExtra("command","next");
        PendingIntent nextPendingIntent = PendingIntent.getService(this,2,nextIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.next_btn,nextPendingIntent);

        Intent prevIntent = new Intent(this,MusicService.class);
        prevIntent.putExtra("command","prev");
        PendingIntent prevPendingIntent = PendingIntent.getService(this,3,prevIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.prev_btn,prevPendingIntent);

        Intent closeIntent = new Intent(this,MusicService.class);
        closeIntent.putExtra("command","close");
        PendingIntent closePendingIntent = PendingIntent.getService(this,4,closeIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.close_btn,closePendingIntent);

        builder.setCustomContentView(remoteViews);

        builder.setSmallIcon(android.R.drawable.ic_media_play);

        startForeground(NOTIF_ID,builder.build());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String command = intent.getStringExtra("command");

        switch (command){
            case "new_instance":
                if(!mediaPlayer.isPlaying()) {
                    songs = (ArrayList<Song>) intent.getSerializableExtra(passSongList);
                    try {
                        mediaPlayer.setDataSource(songs.get(currentlyPlaying).getLink());//.get(currentlyPlaying));
                        mediaPlayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case "play":
                if(!mediaPlayer.isPlaying())
                    mediaPlayer.start();
                break;

            case "next":
                if(mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                playSong(true);
                break;

            case "prev":
                if(mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                playSong(false);
                break;

            case "pause":
                if(mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                break;
            case "close":
                stopSelf();
                break;





        }
        return super.onStartCommand(intent, flags, startId);
    }


    private void playSong(boolean isNext){
        if(isNext) {
            currentlyPlaying++;
            if (currentlyPlaying == songs.size())
                currentlyPlaying = 0;
        }
        else{
            currentlyPlaying--;
            if(currentlyPlaying<0)
                currentlyPlaying = songs.size()-1;
        }
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(songs.get(currentlyPlaying).getLink());
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

      playSong(true);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
    }
}
