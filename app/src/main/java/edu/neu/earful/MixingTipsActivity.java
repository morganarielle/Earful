package edu.neu.earful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MixingTipsActivity extends AppCompatActivity {
    Button listenToPinkNoiseButton;
    Button boostsPinkNoiseButton;
    Button cutsPinkNoiseButton;

    Button boosts250HzButton;
    Button boosts500HzButton;
    Button boosts1kHzButton;
    Button boosts2kHzButton;
    Button boosts4kHzButton;

    Button cuts250HzButton;
    Button cuts500HzButton;
    Button cuts1kHzButton;
    Button cuts2kHzButton;
    Button cuts4kHzButton;

    String currentlyPlayingButtonID = null; // null will represent no buttons are currently playing
    Map<Integer, String> assetMap = new HashMap<>();
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mixing_tips);

        listenToPinkNoiseButton = findViewById(R.id.listen_to_pink_noise_button);
        boostsPinkNoiseButton = findViewById(R.id.boosts_pink_noise_button);
        cutsPinkNoiseButton = findViewById(R.id.cuts_pink_noise_button);

        boosts250HzButton = findViewById(R.id.boosts_250Hz_button);
        boosts500HzButton = findViewById(R.id.boosts_500Hz_button);
        boosts1kHzButton = findViewById(R.id.boosts_1kHz_button);
        boosts2kHzButton = findViewById(R.id.boosts_2kHz_button);
        boosts4kHzButton = findViewById(R.id.boosts_4kHz_button);

        cuts250HzButton = findViewById(R.id.cuts_250Hz_button);
        cuts500HzButton = findViewById(R.id.cuts_500Hz_button);
        cuts1kHzButton = findViewById(R.id.cuts_1kHz_button);
        cuts2kHzButton = findViewById(R.id.cuts_2kHz_button);
        cuts4kHzButton = findViewById(R.id.cuts_4kHz_button);

        generateAssetMap();

        listenToPinkNoiseButton.setOnClickListener(view -> {
            int id = listenToPinkNoiseButton.getId();
            if (!Integer.toString(id).equals(currentlyPlayingButtonID)) {
                stopAllAudio();
            }
            String path = getFilePath(-1);
            setUpMediaPlayer(id, path);
        });

        boostsPinkNoiseButton.setOnClickListener(view -> {
            int id = boostsPinkNoiseButton.getId();
            if (!Integer.toString(id).equals(currentlyPlayingButtonID)) {
                stopAllAudio();
            }
            String path = getFilePath(-1);
            setUpMediaPlayer(id, path);
        });

        cutsPinkNoiseButton.setOnClickListener(view -> {
            int id = cutsPinkNoiseButton.getId();
            if (!Integer.toString(id).equals(currentlyPlayingButtonID)) {
                stopAllAudio();
            }
            String path = getFilePath(-1);
            setUpMediaPlayer(id, path);
        });

        boosts250HzButton.setOnClickListener(view -> {
            int id = boosts250HzButton.getId();
            if (!Integer.toString(id).equals(currentlyPlayingButtonID)) {
                stopAllAudio();
            }
            String path = getFilePath(1); // correct
            setUpMediaPlayer(id, path);
        });

        boosts500HzButton.setOnClickListener(view -> {
            int id = boosts500HzButton.getId();
            if (!Integer.toString(id).equals(currentlyPlayingButtonID)) {
                stopAllAudio();
            }
            String path = getFilePath(4); // correct
            setUpMediaPlayer(id, path);
        });

        boosts1kHzButton.setOnClickListener(view -> {
            int id = boosts1kHzButton.getId();
            if (!Integer.toString(id).equals(currentlyPlayingButtonID)) {
                stopAllAudio();
            }
            String path = getFilePath(0); // correct
            setUpMediaPlayer(id, path);
        });

        boosts2kHzButton.setOnClickListener(view -> {
            int id = boosts2kHzButton.getId();
            if (!Integer.toString(id).equals(currentlyPlayingButtonID)) {
                stopAllAudio();
            }
            String path = getFilePath(2); // correct
            setUpMediaPlayer(id, path);
        });

        boosts4kHzButton.setOnClickListener(view -> {
            int id = boosts4kHzButton.getId();
            if (!Integer.toString(id).equals(currentlyPlayingButtonID)) {
                stopAllAudio();
            }
            String path = getFilePath(3); // correct
            setUpMediaPlayer(id, path);
        });

        cuts250HzButton.setOnClickListener(view -> {
            int id = cuts250HzButton.getId();
            if (!Integer.toString(id).equals(currentlyPlayingButtonID)) {
                stopAllAudio();
            }
            String path = getFilePath(6); // correct
            setUpMediaPlayer(id, path);
        });

        cuts500HzButton.setOnClickListener(view -> {
            int id = cuts500HzButton.getId();
            if (!Integer.toString(id).equals(currentlyPlayingButtonID)) {
                stopAllAudio();
            }
            String path = getFilePath(9); // correct
            setUpMediaPlayer(id, path);
        });

        cuts1kHzButton.setOnClickListener(view -> {
            int id = cuts1kHzButton.getId();
            if (!Integer.toString(id).equals(currentlyPlayingButtonID)) {
                stopAllAudio();
            }
            String path = getFilePath(5); // correct
            setUpMediaPlayer(id, path);
        });

        cuts2kHzButton.setOnClickListener(view -> {
            int id = cuts2kHzButton.getId();
            if (!Integer.toString(id).equals(currentlyPlayingButtonID)) {
                stopAllAudio();
            }
            String path = getFilePath(7); // correct
            setUpMediaPlayer(id, path);
        });

        cuts4kHzButton.setOnClickListener(view -> {
            int id = cuts4kHzButton.getId();
            if (!Integer.toString(id).equals(currentlyPlayingButtonID)) {
                stopAllAudio();
            }
            String path = getFilePath(8); // correct
            setUpMediaPlayer(id, path);
        });
    }

    public void setUpMediaPlayer(int id, String path) {
        if (player == null) {
            currentlyPlayingButtonID = Integer.toString(id);
            player = new MediaPlayer();
            AssetFileDescriptor afd;
            try {
                afd = getAssets().openFd(path);
                player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                //player.setLooping(true); // Loops the audio
                player.setOnPreparedListener(mediaPlayer -> {
                    // Play the audio
                    playAudioForButton(id);
                });

                player.setOnCompletionListener(mediaPlayer -> stopAudioForButton(id));

                player.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Stop the audio
            stopAudioForButton(id);
            currentlyPlayingButtonID = null;
        }
    }

    public void stopAllAudio() {
        // Stop the audio of the currently playing button
        if (currentlyPlayingButtonID != null) {
            stopAudioForButton(Integer.parseInt(currentlyPlayingButtonID));
        }
    }

    public void stopAudioForButton(int buttonID) {
        // Stop the audio
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
            player = null;
            if (buttonID == listenToPinkNoiseButton.getId()) {
                listenToPinkNoiseButton.setText("ᐅ");
            } else if (buttonID == boostsPinkNoiseButton.getId()) {
                boostsPinkNoiseButton.setText("ᐅ");
            } else if (buttonID == cutsPinkNoiseButton.getId()) {
                cutsPinkNoiseButton.setText("ᐅ");
            } else if (buttonID == boosts250HzButton.getId()) {
                boosts250HzButton.setText("ᐅ");
            } else if (buttonID == boosts500HzButton.getId()) {
                boosts500HzButton.setText("ᐅ");
            } else if (buttonID == boosts1kHzButton.getId()) {
                boosts1kHzButton.setText("ᐅ");
            } else if (buttonID == boosts2kHzButton.getId()) {
                boosts2kHzButton.setText("ᐅ");
            } else if (buttonID == boosts4kHzButton.getId()) {
                boosts4kHzButton.setText("ᐅ");
            } else if (buttonID == cuts250HzButton.getId()) {
                cuts250HzButton.setText("ᐅ");
            } else if (buttonID == cuts500HzButton.getId()) {
                cuts500HzButton.setText("ᐅ");
            } else if (buttonID == cuts1kHzButton.getId()) {
                cuts1kHzButton.setText("ᐅ");
            } else if (buttonID == cuts2kHzButton.getId()) {
                cuts2kHzButton.setText("ᐅ");
            } else if (buttonID == cuts4kHzButton.getId()) {
                cuts4kHzButton.setText("ᐅ");
            }
        }
    }

    public void playAudioForButton(int buttonID) {
        player.start();
        if (buttonID == listenToPinkNoiseButton.getId()) {
            listenToPinkNoiseButton.setText("||");
        } else if (buttonID == boostsPinkNoiseButton.getId()) {
            boostsPinkNoiseButton.setText("||");
        } else if (buttonID == cutsPinkNoiseButton.getId()) {
            cutsPinkNoiseButton.setText("||");
        } else if (buttonID == boosts250HzButton.getId()) {
            boosts250HzButton.setText("||");
        } else if (buttonID == boosts500HzButton.getId()) {
            boosts500HzButton.setText("||");
        } else if (buttonID == boosts1kHzButton.getId()) {
            boosts1kHzButton.setText("||");
        } else if (buttonID == boosts2kHzButton.getId()) {
            boosts2kHzButton.setText("||");
        } else if (buttonID == boosts4kHzButton.getId()) {
            boosts4kHzButton.setText("||");
        } else if (buttonID == cuts250HzButton.getId()) {
            cuts250HzButton.setText("||");
        } else if (buttonID == cuts500HzButton.getId()) {
            cuts500HzButton.setText("||");
        } else if (buttonID == cuts1kHzButton.getId()) {
            cuts1kHzButton.setText("||");
        } else if (buttonID == cuts2kHzButton.getId()) {
            cuts2kHzButton.setText("||");
        } else if (buttonID == cuts4kHzButton.getId()) {
            cuts4kHzButton.setText("||");
        }
    }

    public String getFilePath(int key) {
        String directory;

        if (key == -1) {
            return assetMap.get(key);
        }

        if (key < 5) {
            directory = "Boosts";
        } else {
            directory = "Cuts";
        }

        return directory + "/" + assetMap.get(key);
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
            stopAudioForButton(Integer.parseInt(currentlyPlayingButtonID)); // Must come before super
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (currentlyPlayingButtonID != null) {
            stopAudioForButton(Integer.parseInt(currentlyPlayingButtonID));
        }
    }
}