package edu.neu.earful;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

public class IntervalTrainingActivity extends AppCompatActivity {
    private DifficultyLevel difficulty;
    Button playAudioButton;
    ImageButton menuButton;
    GridView intervalOptionsGV;
    MediaPlayer player1, player2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        this.difficulty = (DifficultyLevel) bundle.get("difficulty");

        setContentView(R.layout.activity_interval_training);
        playAudioButton = findViewById(R.id.playButton);
        menuButton = findViewById(R.id.menuButton);
        intervalOptionsGV = findViewById(R.id.intervalOptions);

        ArrayList<Interval> intervals = Interval.getIntervalsForDifficulty(difficulty);
        ArrayList<IntervalCard> intervalCards = intervals.stream().map(IntervalCard::new).collect(Collectors.toCollection(ArrayList::new));

        IntervalGVAdapter adapter = new IntervalGVAdapter(this, intervalCards);
        intervalOptionsGV.setAdapter(adapter);

        setupIntervalOptions();
        String[] intervalFiles = chooseInterval();
        setupAudioPlayback(intervalFiles[0], intervalFiles[1]);
    }

    /**
     * Choose random interval within the intervals available at the given difficulty level.
     * @return String[] with the file paths to the notes in the interval.
     */
    private String[] chooseInterval() {
        ArrayList<Interval> intervals = Interval.getIntervalsForDifficulty(difficulty);

        // choose random interval
        Random rand = new Random();
        Interval interval = intervals.get(rand.nextInt(intervals.size()));
        String[] files = new String[2];
        int halfSteps = Interval.intervalHalfSteps.get(interval);

        // choose midi starting note (ending note must be within range of notes available)
        int minMidi = Collections.min(Interval.midiToFile.keySet());
        int startMidi = minMidi + rand.nextInt(Interval.midiToFile.size() - halfSteps);
        int endMidi = startMidi + halfSteps;

        // get files for chosen midi notes
        files[0] = "Notes/" + Interval.midiToFile.get(startMidi);
        files[1] = "Notes/" + Interval.midiToFile.get(endMidi);
        return files;
    }

    /**
     * Setup audio playback queued by clicking the play button.
     */
    private void setupAudioPlayback(String file1, String file2) {
        playAudioButton.setOnClickListener(view -> {
            if (player1 == null) {
                player1 = new MediaPlayer();
                player2 = new MediaPlayer();
                AssetFileDescriptor afd1, afd2 = null;
                try {
                    afd1 = getAssets().openFd(file1);
                    afd2 = getAssets().openFd(file2);
                    player1.setDataSource(afd1.getFileDescriptor(),afd1.getStartOffset(),afd1.getLength());
                    player2.setDataSource(afd2.getFileDescriptor(),afd2.getStartOffset(),afd2.getLength());
                    player1.setOnPreparedListener(mediaPlayer -> {
                        // Play the audio
                        player1.start();
                        player1.setNextMediaPlayer(player2);
                        playAudioButton.setText("||");
                    });

                    player1.setOnCompletionListener(mediaPlayer -> {
                        // Stop the audio
                        player1.release();
                        player1 = null;
                    });
                    player2.setOnCompletionListener(mediaPlayer -> {
                        // Stop the audio
                        player2.release();
                        player2 = null;
                        playAudioButton.setText("ᐅ");
                    });

                    player1.prepareAsync();
                    player2.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Stop the audio
                player1.release();
                player1 = null;
                playAudioButton.setText("ᐅ");
            }
        });
    }

    /**
     * Setup the GridView to display interval options.
     */
    private void setupIntervalOptions() {
        ArrayList<Interval> intervals =  Interval.getIntervalsForDifficulty(difficulty);
        if (intervals.size() % 3 == 0) {
            intervalOptionsGV.setNumColumns(3);
        }
        else if (intervals.size() > 9) {
            intervalOptionsGV.setNumColumns(5);
        }
        else {
            intervalOptionsGV.setNumColumns(4);
        }
    }
}
