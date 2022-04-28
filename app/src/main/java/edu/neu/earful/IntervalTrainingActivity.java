package edu.neu.earful;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
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
    boolean resetProgress = false;

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
                selectedIntervalTV.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_blue));
            }
            // this interval is already selected, so deselect it
            else if (selectedIntervalTV == intervalTV) {
                selectedIntervalTV.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.medium_blue));
                selectedIntervalTV = null;
            }
            // a new interval is selected
            else {
                selectedIntervalTV.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.medium_blue));
                selectedIntervalTV = intervalTV;
                selectedIntervalTV.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_blue));
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
                player1.reset();
                player1.release();
                player1 = null;
                playAudioButton.setText("ᐅ");
            }
            if (player2 != null) {
                if (player2.isPlaying()) {
                    player2.stop();
                }
                player2.reset();
                player2.release();
                player2 = null;
                playAudioButton.setText("ᐅ");
            }

            // Update the progress
            int currentProgress = progressBar.getProgress();
            if (currentProgress == 90) {
                progressBar.setProgress(100);

                // TODO: write the score to the database

                Intent resultsActivityIntent = new Intent(IntervalTrainingActivity.this, ResultsActivity.class);
                // TODO: pass the actual score to the next activity
                resultsActivityIntent.putExtra("score", 100);
                startActivity(resultsActivityIntent);

                resetProgress = true;
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
            intervalTV.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.medium_blue));
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
        Log.v("TAG", "Number of half steps is " + halfSteps);

        // choose midi starting note (ending note must be within range of notes available)
        int minMidi = Collections.min(Interval.midiToFile.keySet());
        int startMidi = minMidi + rand.nextInt(Interval.midiToFile.size() - halfSteps);
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
    private void setupAudioPlayback(String file1, String file2) {
        playAudioButton.setOnClickListener(view -> {
            if (player1 == null) {
                player1 = new MediaPlayer();
                player2 = new MediaPlayer();
                AssetFileDescriptor afd1, afd2;
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
                        if(player1.isPlaying())
                            player1.stop();
                        player1.reset();
                        player1.release();
                        player1 = null;
                    });
                    player2.setOnCompletionListener(mediaPlayer -> {
                        // Stop the audio
                        if(player2.isPlaying())
                            player2.stop();
                        player2.reset();
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
                if(player1.isPlaying())
                    player1.reset();
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

    @Override
    protected void onResume() {
        super.onResume();
        if (resetProgress) {
            progressBar.setProgress(0);
            resetProgress = false;
        }
    }
}
