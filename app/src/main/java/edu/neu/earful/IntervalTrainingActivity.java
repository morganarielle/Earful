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
import java.util.stream.Collectors;

public class IntervalTrainingActivity extends AppCompatActivity {
    private DifficultyLevel difficulty = DifficultyLevel.LEVEL1;
    Button playAudioButton;
    ImageButton menuButton;
    GridView intervalOptions;
    MediaPlayer player1, player2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        this.difficulty = (DifficultyLevel) bundle.get("difficulty");

        setContentView(R.layout.activity_interval_training);
        playAudioButton = findViewById(R.id.playButton);
        menuButton = findViewById(R.id.menuButton);
        intervalOptions = findViewById(R.id.intervalOptions);

        ArrayList<Interval> intervals = Interval.getIntervalsForDifficulty(difficulty);
        ArrayList<IntervalCard> intervalCards = intervals.stream().map(IntervalCard::new).collect(Collectors.toCollection(ArrayList::new));

        IntervalGVAdapter adapter = new IntervalGVAdapter(this, intervalCards);
        intervalOptions.setAdapter(adapter);

        setupIntervalOptions();
        setupAudioPlayback();
    }

    /**
     * Setup audio playback queued by clicking the play button.
     */
    private void setupAudioPlayback() {
        playAudioButton.setOnClickListener(view -> {
            if (player1 == null) {
                player1 = new MediaPlayer();
                player2 = new MediaPlayer();
                AssetFileDescriptor afd1, afd2 = null;
                try {
                    afd1 = getAssets().openFd("Notes/c3.mp3");
                    afd2 = getAssets().openFd("Notes/d3.mp3");
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
    public void setupIntervalOptions() {
        ArrayList<Interval> intervals =  Interval.getIntervalsForDifficulty(difficulty);
        if (intervals.size() % 3 == 0) {
            intervalOptions.setNumColumns(3);
        }
        else if (intervals.size() > 9) {
            intervalOptions.setNumColumns(5);
        }
        else {
            intervalOptions.setNumColumns(4);
        }
    }
}
