package edu.neu.earful;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

public class IntervalTrainingActivity extends AppCompatActivity {
    private final Random rand = new Random();
    private DifficultyLevel difficulty;
    Interval correctInterval;
    Button playAudioButton;
    TextView selectedIntervalTV = null;
    Button submitButton;
    ProgressBar progressBar;
    GridView intervalOptionsGV;
    MediaPlayer player1, player2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        this.difficulty = (DifficultyLevel) bundle.get("difficulty");

        setContentView(R.layout.activity_interval_training);
        playAudioButton = findViewById(R.id.playButton);
        intervalOptionsGV = findViewById(R.id.intervalOptions);
        submitButton = findViewById(R.id.submitButton);
        progressBar = findViewById(R.id.progressBar);

        ArrayList<Interval> intervals = Interval.getIntervalsForDifficulty(difficulty);
        ArrayList<IntervalCard> intervalCards = intervals.stream().map(IntervalCard::new).collect(Collectors.toCollection(ArrayList::new));

        IntervalGVAdapter adapter = new IntervalGVAdapter(this, intervalCards);
        intervalOptionsGV.setAdapter(adapter);

        setupIntervalOptions();
        String[] intervalFiles = chooseInterval();
        setupAudioPlayback(intervalFiles[0], intervalFiles[1]);

        intervalOptionsGV.setOnItemClickListener((adapterView, view, position, id) -> {
            CardView intervalCard = (CardView) intervalOptionsGV.getChildAt(position);
            TextView intervalTV = intervalCard.getChildAt(0).findViewById(R.id.intervalTV);

            // nothing is currently selected
            if (selectedIntervalTV == null) {
                selectedIntervalTV = intervalTV;
                selectedIntervalTV.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.teal_200));
            }
            // this interval is already selected, so deselect it
            else if (selectedIntervalTV == intervalTV) {
                selectedIntervalTV = null;
                selectedIntervalTV.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_500));
            }
            // a new interval is selected
            else {
                selectedIntervalTV.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_500));
                selectedIntervalTV = intervalTV;
                selectedIntervalTV.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.teal_200));
            }

            submitButton.setEnabled(!(selectedIntervalTV == null));
        });

        submitButton.setEnabled(!(selectedIntervalTV == null));

        submitButton.setOnClickListener(view -> {
            // Check if we should stop playing the audio
            if (player1 != null) {
                if (player1.isPlaying()) {
                    player1.stop();
                }
                player1.release();
                player1 = null;
                playAudioButton.setText("ᐅ");
            }
            if (player2 != null) {
                if (player2.isPlaying()) {
                    player2.stop();
                }
                player2.release();
                player2 = null;
                playAudioButton.setText("ᐅ");
            }

            // Update the progress
            int currentProgress = progressBar.getProgress();
            if (currentProgress == 100) {
                // TODO: we'd want to end the exercise and bring the user to a screen to see how they performed
                progressBar.setProgress(0);
            } else {
                progressBar.setProgress(currentProgress + 10);
            }

            // Check what interval was clicked
            if (selectedIntervalTV == null) {
                // no interval is selected
                System.out.println("No interval is selected");
            } else {
                // one of intervals is selected
                System.out.println("Interval " + IntervalCard.getIntervalFromDisplayText((String) selectedIntervalTV.getText()) + " clicked.");

                // clear selection
                resetIntervalCardSelection();

                // Disabled the submit button
                submitButton.setEnabled(false);
            }

            // make a toast telling the user if they were correct or not
            if (correctInterval == IntervalCard.getIntervalFromDisplayText((String) selectedIntervalTV.getText())) {
                Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
            }

            // Get a new random file to play for the user
            selectedIntervalTV = null;
            String[] newIntervalFiles = chooseInterval();
            setupAudioPlayback(newIntervalFiles[0], newIntervalFiles[1]);
        });
    }

    private void resetIntervalCardSelection() {
        for (int i = 0; i < intervalOptionsGV.getChildCount(); i++) {
            CardView card = (CardView) intervalOptionsGV.getChildAt(i);
            TextView intervalTV = card.findViewById(R.id.intervalTV);
            intervalTV.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_500));
        }
    }

    /**
     * Choose random interval within the intervals available at the given difficulty level.
     * @return String[] with the file paths to the notes in the interval.
     */
    private String[] chooseInterval() {
        ArrayList<Interval> intervals = Interval.getIntervalsForDifficulty(difficulty);

        // choose random interval
        correctInterval = intervals.get(rand.nextInt(intervals.size()));
        Log.v("TAG", "Correct interval is " + correctInterval);
        String[] files = new String[2];
        int halfSteps = Interval.intervalHalfSteps.get(correctInterval);

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