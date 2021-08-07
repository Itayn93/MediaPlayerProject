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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class AddSongActivity extends AppCompatActivity implements DialogInterface.OnClickListener{

    ArrayList<Song> songsList = new ArrayList<>();

    final String passSongList = "SONG_LIST";

    final int CAMERA_REQUEST = 1;
    Bitmap bitmap;
    File file;

    //boolean allDetails = false ;

    String songName;
    String songArtist = "user";
    String songDuration = "00:00";
    String songLink;
    String songPicLink = "";

    EditText songNameET;
    //EditText songArtistET;
    //EditText songDurationET;
    EditText songLinkET;

    Button addPictureButton;
    Button addToPlaylistButton;

    ImageView songPicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("alert: ", "onCreate AddSongActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        songNameET = findViewById(R.id.editTextSongName);
        //songArtistET = findViewById(R.id.editTextSongArtist);
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


                            }
                        })

                        .show();
            }
        });

        addToPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!songName.matches("")) && (!songLink.matches("")) && (!songPicLink.matches(""))){
                    Song newSong = new Song(songName,songArtist,songDuration,songLink,songPicLink);
                    songsList.add(newSong);
                    Intent songsListIntent = new Intent(getApplicationContext(), SongsListActivity.class);
                    songsListIntent.putExtra(passSongList,(Serializable)songsList);
                    startActivity(songsListIntent);
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
        Log.d("alert: ", "onStart AddSongActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("alert: ", "onResume AddSongActivity");

        songName = songNameET.getText().toString();
        //songArtist = songArtistET.getText().toString();
        //songDuration = songDurationET.getText().toString();
        songLink = songLinkET.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){
           // bitmap = (Bitmap) data.getExtras().get("data");
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            Uri uri = getImageUri(getApplicationContext(),bitmap);
            songPicture.setImageBitmap(bitmap);
            songPicLink = uri.toString();

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
}