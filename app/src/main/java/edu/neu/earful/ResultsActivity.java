package edu.neu.earful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.io.IOException;

public class ResultsActivity extends AppCompatActivity {
    Button menuButton;
    Button retryButton;
    TextView scoreValueTextView;
    TextView numPointsAwardedTextView;
    MediaPlayer resultsPlayer;
    DifficultyLevel newHighestLevel = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        menuButton = findViewById(R.id.menu_button);
        retryButton = findViewById(R.id.retry_button);
        scoreValueTextView = findViewById(R.id.exercise_score_value);
        numPointsAwardedTextView = findViewById(R.id.numPointsAwarded);

        int newScore = getIntent().getIntExtra("percent", -1);
        int points = getIntent().getIntExtra("points", 0);
        scoreValueTextView.setText(newScore + "%");
        numPointsAwardedTextView.setText(Integer.toString(points));

        resultsPlayer = new MediaPlayer();

        String path;
        if (newScore == 100) {
            path = "FX/score_positive_3.wav";
        } else if (newScore == 0) {
            path = "FX/score_negative_3.wav";
        } else if (newScore < 25) {
            path = "FX/score_negative_2.wav";
        } else if (newScore < 50) {
            path = "FX/score_negative_1.wav";
        } else if (newScore < 75) {
            path = "FX/score_positive_1.wav";
        } else {
            path = "FX/score_positive_2.wav";
        }

        AssetFileDescriptor afd;
        try {
            afd = getAssets().openFd(path);
            resultsPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            resultsPlayer.setOnPreparedListener(mediaPlayer -> {
                // Play the audio
                playResultsAudio();
            });

            resultsPlayer.setOnCompletionListener(mediaPlayer -> stopResultsAudio());

            resultsPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String mode = getIntent().getStringExtra("mode");
        if (mode.equals(getString(R.string.musician_results_key))) {
            Log.v("TAG", "ResultsActivity: musician mode is true");
            boolean displayNewLevel = getIntent().getBooleanExtra("display_new_level", false);
            Log.v("TAG", "ResultsActivity: displayNewLevel = " + displayNewLevel);
            if (displayNewLevel) {
                Log.v("TAG", "ResultsActivity: display_new_level is true, attempting to show dialog");
                newHighestLevel = (DifficultyLevel) getIntent().getSerializableExtra("highest_level");
                NewLevelFragment newLevelFragment = new NewLevelFragment(this);
                newLevelFragment.setHighestLevel(newHighestLevel);
                newLevelFragment.show(getSupportFragmentManager(), "NewLevelFragment");
            }
        }
    }

    public void playResultsAudio() {
        resultsPlayer.start();
    }

    public void stopResultsAudio() {
        // Stop the audio
        if (resultsPlayer != null) {
            if (resultsPlayer.isPlaying()) {
                resultsPlayer.stop();
            }
            resultsPlayer.release();
            resultsPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        stopResultsAudio(); // Must come before super
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopResultsAudio();
    }

    public void onClick(View view) {
        int id = view.getId();

        if (id == menuButton.getId()) {
            Intent menuActivityIntent = new Intent(ResultsActivity.this, ModeMenuActivity.class);
            startActivity(menuActivityIntent);
        } else if (id == retryButton.getId()) {
            finish();
        }
    }

}