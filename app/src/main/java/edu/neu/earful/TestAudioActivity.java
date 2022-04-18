package edu.neu.earful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import java.io.IOException;

public class TestAudioActivity extends AppCompatActivity {
    Button playAudioButton;
    Button submitButton;
    ProgressBar progressBar;
    RadioGroup radioGroup;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_audio);

        playAudioButton = findViewById(R.id.play_button);
        progressBar = findViewById(R.id.progress_bar);
        submitButton = findViewById(R.id.submit_button);
        radioGroup = findViewById(R.id.radio_group);

        playAudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player == null) {
                    player = new MediaPlayer();
                    AssetFileDescriptor afd = null;
                    try {
                        afd = getAssets().openFd("Boosts/Boost_4kHz_+12db.mp3");
                        player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                        //player.setLooping(true); // Loops the audio
                        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                // Play the audio
                                player.start();
                                playAudioButton.setText("||");
                            }
                        });

                        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                // Stop the audio
                                player.release();
                                player = null;
                                playAudioButton.setText("ᐅ");
                            }
                        });

                        player.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Stop the audio
                    player.release();
                    player = null;
                    playAudioButton.setText("ᐅ");
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // Enabled the submit button
                submitButton.setEnabled(true);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if we should stop playing the audio
                if (player != null) {
                    // Stop the audio
                    player.release();
                    player = null;
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

                // Check what radio button was clicked
                int checkedButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedButtonId == -1) {
                    // no radio buttons are checked
                    System.out.println("No radio buttons are checked");
                } else {
                    // one of the radio buttons is checked
                    System.out.println("Radio button " + checkedButtonId + " clicked.");

                    // Uncheck all
                    radioGroup.clearCheck();

                    // Disabled the submit button
                    submitButton.setEnabled(false);
                }
            }
        });
    }
}