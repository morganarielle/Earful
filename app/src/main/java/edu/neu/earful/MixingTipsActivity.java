package edu.neu.earful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MixingTipsActivity extends AppCompatActivity {
    Button listenToPinkNoiseButton;
    Button boostsPinkNoiseButton;
    Button cutsPinkNoiseButton;

    String currentlyPlayingButtonID = null; // null will represent no buttons are currently playing
    Map<Integer, String> assetMap = new HashMap<>();
    MediaPlayer player;
    //String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mixing_tips);

        listenToPinkNoiseButton = findViewById(R.id.listen_to_pink_noise_button);
        boostsPinkNoiseButton = findViewById(R.id.boosts_pink_noise_button);
        cutsPinkNoiseButton = findViewById(R.id.cuts_pink_noise_button);

        generateAssetMap();

        listenToPinkNoiseButton.setOnClickListener(view -> {
            int id = listenToPinkNoiseButton.getId();
            if (!Integer.toString(id).equals(currentlyPlayingButtonID)) {
                stopOtherAudio();
            }
            String path = assetMap.get(-1);
            if (player == null) {
                currentlyPlayingButtonID = Integer.toString(id);
                player = new MediaPlayer();
                AssetFileDescriptor afd;
                try {
                    System.out.println(path);
                    afd = getAssets().openFd(path);
                    player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    //player.setLooping(true); // Loops the audio
                    player.setOnPreparedListener(mediaPlayer -> {
                        // Play the audio
                        playAudio(id);
                    });

                    player.setOnCompletionListener(mediaPlayer -> stopAudio(id));

                    player.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Stop the audio
                stopAudio(id);
                currentlyPlayingButtonID = null;
            }
        });
    }

    public void stopOtherAudio() {
        // Stop the audio of the currently playing button
        if (currentlyPlayingButtonID != null) {
            stopAudio(Integer.parseInt(currentlyPlayingButtonID));
        }
    }

    public void stopAudio(int buttonID) {
        // Stop the audio
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
            player = null;
            if (buttonID == listenToPinkNoiseButton.getId()) {
                listenToPinkNoiseButton.setText("ᐅ");
            }
        }
    }

    public void playAudio(int buttonID) {
        player.start();
        if (buttonID == listenToPinkNoiseButton.getId()) {
            listenToPinkNoiseButton.setText("||");
        }
    }


    public void generateAssetMap() {
        assetMap.put(-1, "PinkNoise.mp3");
        try {
            int index = 0;

            String[] boostFiles = getAssets().list("Boosts");
            for (String file : boostFiles) {
                assetMap.put(index, file);
                index++;
            }

            String[] cutFiles = getAssets().list("Cuts");
            for (String file : cutFiles) {
                assetMap.put(index, file);
                index++;
            }
        } catch (Exception e) {
            // There was an issue listing the files
        }

    }

    @Override
    protected void onDestroy() {
        if (currentlyPlayingButtonID != null) {
            stopAudio(Integer.parseInt(currentlyPlayingButtonID)); // Must come before super
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (currentlyPlayingButtonID != null) {
            stopAudio(Integer.parseInt(currentlyPlayingButtonID));
        }
    }
}