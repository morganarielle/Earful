package edu.neu.earful.training;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import edu.neu.earful.R;

public class ResultsActivity extends AppCompatActivity {
    Button menuButton;
    Button retryButton;
    TextView scoreValueTextView;
    MediaPlayer resultsPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        menuButton = findViewById(R.id.menu_button);
        retryButton = findViewById(R.id.retry_button);
        scoreValueTextView = findViewById(R.id.exercise_score_value);

        int score = getIntent().getIntExtra("score", -1);
        scoreValueTextView.setText(score + "%");

        resultsPlayer = new MediaPlayer();

        String path;
        if (score == 100) {
            path = "FX/score_positive_3.wav";
        } else if (score == 0) {
            path = "FX/score_negative_3.wav";
        } else if (score < 25) {
            path = "FX/score_negative_2.wav";
        } else if (score < 50) {
            path = "FX/score_negative_1.wav";
        } else if (score < 75) {
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