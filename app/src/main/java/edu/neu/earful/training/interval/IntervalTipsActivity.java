package edu.neu.earful.training.interval;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import edu.neu.earful.R;

public class IntervalTipsActivity extends AppCompatActivity {
    MediaPlayer player1, player2;
    private final String LOW = "LOW";
    private final String MID = "MID";
    private final String HIGH = "HIGH";
    private final ArrayList<Button> buttons = new ArrayList<>();
    private Button perfect1lowButton;
    private Button perfect1midButton;
    private Button perfect1highButton;
    private Button minor2lowButton;
    private Button minor2midButton;
    private Button minor2highButton;
    private Button major2lowButton;
    private Button major2midButton;
    private Button major2highButton;
    private Button minor3lowButton;
    private Button minor3midButton;
    private Button minor3highButton;
    private Button major3lowButton;
    private Button major3midButton;
    private Button major3highButton;
    private Button perfect4lowButton;
    private Button perfect4midButton;
    private Button perfect4highButton;
    private Button minor5lowButton;
    private Button minor5midButton;
    private Button minor5highButton;
    private Button perfect5lowButton;
    private Button perfect5midButton;
    private Button perfect5highButton;
    private Button minor6lowButton;
    private Button minor6midButton;
    private Button minor6highButton;
    private Button major6lowButton;
    private Button major6midButton;
    private Button major6highButton;
    private Button minor7lowButton;
    private Button minor7midButton;
    private Button minor7highButton;
    private Button major7lowButton;
    private Button major7midButton;
    private Button major7highButton;
    private Button perfect8lowButton;
    private Button perfect8midButton;
    private Button perfect8highButton;

    String currentlyPlayingButtonID = null; // null will represent no buttons are currently playing
    Map<Integer, String> assetMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval_tips);

        perfect1lowButton = findViewById(R.id.perfect1_low_button);
        perfect1midButton = findViewById(R.id.perfect1_mid_button);
        perfect1highButton = findViewById(R.id.perfect1_high_button);
        minor2lowButton = findViewById(R.id.minor2_low_button);
        minor2midButton = findViewById(R.id.minor2_mid_button);
        minor2highButton = findViewById(R.id.minor2_high_button);
        major2lowButton = findViewById(R.id.major2_low_button);
        major2midButton = findViewById(R.id.major2_mid_button);
        major2highButton = findViewById(R.id.major2_high_button);
        minor3lowButton = findViewById(R.id.minor3_low_button);
        minor3midButton = findViewById(R.id.minor3_mid_button);
        minor3highButton = findViewById(R.id.minor3_high_button);
        major3lowButton = findViewById(R.id.major3_low_button);
        major3midButton = findViewById(R.id.major3_mid_button);
        major3highButton = findViewById(R.id.major3_high_button);
        perfect4lowButton = findViewById(R.id.perfect4_low_button);
        perfect4midButton = findViewById(R.id.perfect4_mid_button);
        perfect4highButton = findViewById(R.id.perfect4_high_button);
        minor5lowButton = findViewById(R.id.minor5_low_button);
        minor5midButton = findViewById(R.id.minor5_mid_button);
        minor5highButton = findViewById(R.id.minor5_high_button);
        perfect5lowButton = findViewById(R.id.perfect5_low_button);
        perfect5midButton = findViewById(R.id.perfect5_mid_button);
        perfect5highButton = findViewById(R.id.perfect5_high_button);
        minor6lowButton = findViewById(R.id.minor6_low_button);
        minor6midButton = findViewById(R.id.minor6_mid_button);
        minor6highButton = findViewById(R.id.minor6_high_button);
        major6lowButton = findViewById(R.id.major6_low_button);
        major6midButton = findViewById(R.id.major6_mid_button);
        major6highButton = findViewById(R.id.major6_high_button);
        minor7lowButton = findViewById(R.id.minor7_low_button);
        minor7midButton = findViewById(R.id.minor7_mid_button);
        minor7highButton = findViewById(R.id.minor7_high_button);
        major7lowButton = findViewById(R.id.major7_low_button);
        major7midButton = findViewById(R.id.major7_mid_button);
        major7highButton = findViewById(R.id.major7_high_button);
        perfect8lowButton = findViewById(R.id.perfect8_low_button);
        perfect8midButton = findViewById(R.id.perfect8_mid_button);
        perfect8highButton = findViewById(R.id.perfect8_high_button);
        HashMap<Button, Interval> buttonIntervalHashMap = new HashMap<Button, Interval>() {{
            put(perfect1lowButton, Interval.perfect1);
            put(perfect1midButton, Interval.perfect1);
            put(perfect1highButton, Interval.perfect1);
            put(minor2lowButton, Interval.minor2);
            put(minor2midButton, Interval.minor2);
            put(minor2highButton, Interval.minor2);
            put(major2lowButton, Interval.major2);
            put(major2midButton, Interval.major2);
            put(major2highButton,Interval.major2);
            put(minor3lowButton, Interval.minor3);
            put(minor3midButton, Interval.minor3);
            put(minor3highButton, Interval.minor3);
            put(major3lowButton, Interval.major3);
            put(major3midButton, Interval.major3);
            put(major3highButton, Interval.major3);
            put(perfect4lowButton, Interval.perfect4);
            put(perfect4midButton, Interval.perfect4);
            put(perfect4highButton,Interval.perfect4);
            put(minor5lowButton, Interval.minor5);
            put(minor5midButton, Interval.minor5);
            put(minor5highButton, Interval.minor5);
            put(perfect5lowButton, Interval.perfect5);
            put(perfect5midButton, Interval.perfect5);
            put(perfect5highButton, Interval.perfect5);
            put(minor6lowButton, Interval.minor6);
            put(minor6midButton, Interval.minor6);
            put(minor6highButton, Interval.minor6);
            put(major6lowButton, Interval.major6);
            put(major6midButton, Interval.major6);
            put(major6highButton, Interval.major6);
            put(minor7lowButton, Interval.minor7);
            put(minor7midButton, Interval.minor7);
            put(minor7highButton, Interval.minor7);
            put(major7lowButton, Interval.major7);
            put(major7midButton, Interval.major7);
            put(major7highButton, Interval.major7);
            put(perfect8lowButton, Interval.perfect8);
            put(perfect8midButton, Interval.perfect8);
            put(perfect8highButton, Interval.perfect8);
        }};
        HashMap<Button, String> buttonRangeHashMap = new HashMap<Button, String>() {{
            put(perfect1lowButton, LOW);
            put(perfect1midButton, MID);
            put(perfect1highButton, HIGH);
            put(minor2lowButton, LOW);
            put(minor2midButton, MID);
            put(minor2highButton, HIGH);
            put(major2lowButton, LOW);
            put(major2midButton, MID);
            put(major2highButton, HIGH);
            put(minor3lowButton, LOW);
            put(minor3midButton, MID);
            put(minor3highButton, HIGH);
            put(major3lowButton, LOW);
            put(major3midButton, MID);
            put(major3highButton, HIGH);
            put(perfect4lowButton, LOW);
            put(perfect4midButton, MID);
            put(perfect4highButton, HIGH);
            put(minor5lowButton, LOW);
            put(minor5midButton, MID);
            put(minor5highButton, HIGH);
            put(perfect5lowButton, LOW);
            put(perfect5midButton, MID);
            put(perfect5highButton, HIGH);
            put(minor6lowButton, LOW);
            put(minor6midButton, MID);
            put(minor6highButton, HIGH);
            put(major6lowButton, LOW);
            put(major6midButton, MID);
            put(major6highButton, HIGH);
            put(minor7lowButton, LOW);
            put(minor7midButton, MID);
            put(minor7highButton, HIGH);
            put(major7lowButton, LOW);
            put(major7midButton, MID);
            put(major7highButton, HIGH);
            put(perfect8lowButton, LOW);
            put(perfect8midButton, MID);
            put(perfect8highButton, HIGH);
        }};
        buttons.addAll(Arrays.asList(perfect1lowButton, perfect1midButton, perfect1highButton,
                minor2lowButton, minor2midButton, minor2highButton,
                major2lowButton, major2midButton, major2highButton,
                minor3lowButton, minor3midButton, minor3highButton,
                major3lowButton, major3midButton, major3highButton,
                perfect4lowButton, perfect4midButton, perfect4highButton,
                minor5lowButton, minor5midButton, minor5highButton,
                perfect5lowButton, perfect5midButton, perfect5highButton,
                minor6lowButton, minor6midButton, minor6highButton,
                major6lowButton, major6midButton, major6highButton,
                minor7lowButton, minor7midButton, minor7highButton,
                major7lowButton, major7midButton, major7highButton,
                perfect8lowButton, perfect8midButton, perfect8highButton));

        for (Button button: buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = button.getId();
                    Interval interval = buttonIntervalHashMap.get(button);
                    String range = buttonRangeHashMap.get(button);
                    String[] files = chooseFiles(interval, range);

                    if (!Integer.toString(id).equals(currentlyPlayingButtonID)) {
                        stopAllAudio();
                    }
                    setupAudioPlayback(button, files[0], files[1]);
                }
            });
        }
    }

    private String[] chooseFiles(Interval interval, String range) {
        String[] files = new String[2];
        int halfSteps = Interval.intervalHalfSteps.get(interval);
        Log.v("TAG", "Number of half steps is " + halfSteps);
        
        int startMidi = 48;
        if (range.equals(LOW)) {
            startMidi = 24;
        } else if (range.equals(MID)) {
            startMidi = 48;
        } else if (range.equals(HIGH)) {
            startMidi = 72;
        }

        Log.v("TAG", "First MIDI note is " + startMidi);
        int endMidi = startMidi + halfSteps;
        Log.v("TAG", "Second MIDI note is " + endMidi);

        // get files for chosen midi notes
        files[0] = "Notes/" + Interval.midiToFile.get(startMidi);
        Log.v("TAG", "First file is " + files[0]);
        files[1] = "Notes/" + Interval.midiToFile.get(endMidi);
        Log.v("TAG", "Second file is " + files[1]);
        return files;
    }

    /**
     * Setup audio playback queued by clicking the play button.
     */
    private void setupAudioPlayback(Button button, String file1, String file2) {
        button.setOnClickListener(view -> {
            currentlyPlayingButtonID = Integer.toString(button.getId());
            if (player1 == null) {
                if (player2 == null) {
                    player1 = new MediaPlayer();
                    player2 = new MediaPlayer();
                    AssetFileDescriptor afd1, afd2;
                    try {
                        afd1 = getAssets().openFd(file1);
                        afd2 = getAssets().openFd(file2);
                        player1.setDataSource(afd1.getFileDescriptor(),afd1.getStartOffset(),afd1.getLength());
                        player2.setDataSource(afd2.getFileDescriptor(),afd2.getStartOffset(),afd2.getLength());

                        player2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {

                            }
                        });

                        player1.setOnPreparedListener(mediaPlayer -> {
                            // Play the audio
                            player1.start();
                            // player1.setNextMediaPlayer(player2);
                            button.setText("||");
                        });

                        player1.setOnCompletionListener(mediaPlayer -> {
                            // Stop the audio
                            stopPlayer1();
                            player2.start();
                        });
                        player2.setOnCompletionListener(mediaPlayer -> {
                            // Stop the audio
                            stopPlayer2();
                            button.setText("ᐅ");
                        });

                        player1.prepareAsync();
                        player2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (player2.isPlaying()) {
                        stopPlayer2();
                        button.setText("ᐅ");
                    }
                }
            } else {
                if (player2 != null) {
                    if (player2.isPlaying()) {
                        // Stop player 2
                        stopPlayer2();
                        button.setText("ᐅ");
                    }
                    player2 = null;
                }
                // Stop player 1
                stopPlayer1();
                button.setText("ᐅ");
                currentlyPlayingButtonID = null;
            }
        });
    }

    public void stopPlayer2() {
        if(player2 != null) {
            if (player2.isPlaying())
                player2.stop();
            player2.reset();
            player2.release();
            player2 = null;
        }
    }

    public void stopPlayer1() {
        if(player1 != null) {
            if (player1.isPlaying())
                player1.stop();
            player1.reset();
            player1.release();
            player1 = null;
        }
    }

    public void stopAllAudio() {
        // Stop the audio of the currently playing button
        if (currentlyPlayingButtonID != null) {
            stopPlayer1();
            stopPlayer2();
        }
        for (Button button : buttons) {
            if (button.getText().toString().equals("||")) {
                button.setText("ᐅ");
            }
        }
    }
}
