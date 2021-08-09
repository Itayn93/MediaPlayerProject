package com.example.mediaplayerproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class AddSongActivity extends AppCompatActivity implements DialogInterface.OnClickListener{

    ArrayList<Song> songsList = new ArrayList<>();

    final String passSongList = "SONG_LIST";

    //final int CAMERA_REQUEST = 1;
    Bitmap bitmap;
    File file;

    boolean byCamera; ;

    String songName;
    String songArtist;
    //String songDuration = "00:00";
    String songLink;
    String songPicLink = "";

    EditText songNameET;
    EditText songArtistET;
    //EditText songDurationET;
    EditText songLinkET;

    Button addPictureButton;
    Button addToPlaylistButton;

    ImageView songPicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Lifecycle: ", "AddSongActivity onCreate ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        songNameET = findViewById(R.id.editTextSongName);
        songArtistET = findViewById(R.id.editTextSongArtist);
        //songDurationET = findViewById(R.id.editTextSongDuration);
        songLinkET = findViewById(R.id.editTextSongLink);
        addPictureButton = findViewById(R.id.buttonAddPicture);
        addToPlaylistButton = findViewById(R.id.buttonAddToPlaylist);
        songPicture = findViewById(R.id.songPicture);

        /*songName = songNameET.getText().toString();
        //songArtist = songArtistET.getText().toString();
        //songDuration = songDurationET.getText().toString();
        songLink = songLinkET.getText().toString();*/

        file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),"pic.jpg");

        Intent intent = getIntent();
        songsList = (ArrayList<Song>) intent.getSerializableExtra(passSongList);


        addPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddSongActivity.this);
                builder.setTitle("Upload Song Picture").setMessage("Choose which way to upload song picture:")
                        .setPositiveButton("via Camera", new DialogInterface.OnClickListener() {
                            @Override
                            // upload picture with camera
                            public void onClick(DialogInterface dialog, int which) {
                                byCamera = true;
                                Log.d("alert: ", "onclick, upload picture with camera");
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                             /*   ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                                        new ActivityResultContracts.StartActivityForResult(),
                                        new ActivityResultCallback<ActivityResult>() {
                                            @Override
                                            public void onActivityResult(ActivityResult result) {
                                                if (result.getResultCode() == Activity.RESULT_OK) {
                                                    // There are no request codes
                                                    Intent data = result.getData();
                                                    bitmap = (Bitmap) data.getExtras().get("data");
                                                }
                                            }
                                        });

                                someActivityResultLauncher.launch(intent);*/
                                Uri fileUri = Uri.fromFile(file);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);

                                /** this function is deprecated need to fix*/
                                startActivityForResult(intent,1);
                            }
                        })

                        .setNegativeButton("via Gallery", new DialogInterface.OnClickListener() {

                            @Override
                            // upload picture with gallery
                            public void onClick(DialogInterface dialog, int which) {
                                byCamera = false;
                                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                photoPickerIntent.setType("image/*");
                                startActivityForResult(photoPickerIntent, 1);

                            }
                        })

                        .show();
            }
        });

        addToPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!songName.matches("")) && (!songLink.matches("")) && (!songPicLink.matches(""))){
                    Song newSong = new Song(songName,songArtist,songLink,songPicLink);
                    songsList.add(newSong);
                   /* Intent musicServiceIntent = new Intent(AddSongActivity.this,MusicService.class);
                    musicServiceIntent.putExtra(passSongList,(Serializable)songsList);*/

                    try {
                        FileOutputStream fos = openFileOutput("songsList",MODE_PRIVATE);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(songsList);
                        oos.close();
                        Log.d("Lifecycle: ", "AddSongActivity addToPlaylistButton Clicked songsList size = " + songsList.size());

                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }


                    Intent songsListIntent = new Intent(getApplicationContext(), SongsListActivity.class);
                    songsListIntent.putExtra(passSongList,(Serializable)songsList);
                    startActivity(songsListIntent);

                    /*Intent musicServiceIntent = new Intent(AddSongActivity.this,MusicService.class);
                    musicServiceIntent.putExtra(passSongList,(Serializable)songsList);
                    musicServiceIntent.putExtra("command","new_instance");
                    //intent.putExtra("song_title","new_instance");
                    startService(musicServiceIntent);*/
                }
                else{
                    Toast.makeText(AddSongActivity.this, "Please fill all details", Toast.LENGTH_LONG).show();
                }


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle: ", "AddSongActivity onStart ");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("Lifecycle: ", "AddSongActivity onResume ");

        songName = songNameET.getText().toString();
        songArtist = songArtistET.getText().toString();
        //songDuration = songDurationET.getText().toString();
        songLink = songLinkET.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (byCamera){
            if(requestCode == 1 && resultCode == RESULT_OK){
                // bitmap = (Bitmap) data.getExtras().get("data");
                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                Uri uri = getImageUri(getApplicationContext(),bitmap);
                Glide.with(this).load(uri).fitCenter().into(songPicture);
                //Glide.with(this).load(getArguments().getString(songPicToFragmentKey)).fitCenter().placeholder(rootView.getId()).into(songPicture);
                //songPicture.setImageBitmap(bitmap);
                songPicLink = uri.toString();
            }
        }
        else { // by Gallery
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    Glide.with(this).load(imageUri).fitCenter().into(songPicture);
                    //songPicture.setImageBitmap(selectedImage);
                    songPicLink = imageUri.toString();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(AddSongActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(AddSongActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
        }



    }



    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    // default onClick for dialog - does nothing
    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle: ", "AddSongActivity onPause ");
/*
        try {
            FileOutputStream fos = openFileOutput("songsList",MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(songsList);
            oos.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle: ", "AddSongActivity onStop ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle: ", "AddSongActivity onDestroy ");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("Lifecycle: ", "AddSongActivity onBackPressed ");
        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivityIntent);
    }
}