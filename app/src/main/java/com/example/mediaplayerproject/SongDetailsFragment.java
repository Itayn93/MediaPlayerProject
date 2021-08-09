package com.example.mediaplayerproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SongDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SongDetailsFragment extends Fragment {

    final String songNameToFragmentKey = "SONG_NAME";
    final String songArtistToFragmentKey = "SONG_ARTIST";
    //final String songDurationToFragmentKey = "SONG_DURATION";
    final String songPicToFragmentKey = "SONG_PICTURE";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    //private static final String ARG_PARAM4 = "param4";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    //private String mParam4;

    public SongDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SongDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SongDetailsFragment newInstance(String param1, String param2,String param3) {
        Log.d("Lifecycle: ", "SongDetailsFragment newInstance");
        SongDetailsFragment fragment = new SongDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        //args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("Lifecycle: ", "SongDetailsFragment onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            //mParam4 = getArguments().getString(ARG_PARAM4);

        }
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Lifecycle: ", "SongDetailsFragment onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_song_details, container, false);

        TextView songName = rootView.findViewById(R.id.songNameFragment);
        TextView songArtist = rootView.findViewById(R.id.songArtistFragment);
        //TextView songDuration = rootView.findViewById(R.id.songDurationFragment);
        ImageView songPicture = rootView.findViewById(R.id.songPicFragment);

        songName.setText(getArguments().getString(songNameToFragmentKey));
        songArtist.setText(getArguments().getString(songArtistToFragmentKey));
        //songDuration.setText(getArguments().getString(songDurationToFragmentKey));
        Glide.with(songPicture.getContext()).load(getArguments().getString(songPicToFragmentKey)).fitCenter().placeholder(rootView.getId()).into(songPicture);


        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle: ", "SongDetailsFragment onDestroy  ");
       /* Intent songsListIntent = new Intent(, SongsListActivity.class);

        startActivity(songsListIntent);*/
    }


}