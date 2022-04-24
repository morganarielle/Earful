package edu.neu.earful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MixingExerciseActivity extends AppCompatActivity {
    Button playAudioButton;
    Button submitButton;
    ProgressBar progressBar;
    RadioGroup radioGroup;
    MediaPlayer player;
    TextView whichFreqText;
    boolean includeCuts;
    boolean includeBoosts;
    Map<Integer, String> assetMap = new HashMap<>();
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mixing_exercise);

        playAudioButton = findViewById(R.id.play_button);
        progressBar = findViewById(R.id.progress_bar);
        submitButton = findViewById(R.id.submit_button);
        radioGroup = findViewById(R.id.radio_group);
        whichFreqText = findViewById(R.id.which_freq_textview);

        includeCuts = getIntent().getBooleanExtra("includeCuts", false);
        includeBoosts = getIntent().getBooleanExtra("includeBoosts", false);

        setWhichFreqText();
        generateAssetMap();
        path = getRandomFilePath();

        playAudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player == null) {
                    player = new MediaPlayer();
                    AssetFileDescriptor afd;
                    try {
                        System.out.println(path);
                        afd = getAssets().openFd(path);
                        player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                        //player.setLooping(true); // Loops the audio
                        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                // Play the audio
                                playAudio();
                            }
                        });

                        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                stopAudio();
                            }
                        });

                        player.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Stop the audio
                    stopAudio();
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
                    stopAudio();
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

                // Get a new random file to play for the user
                path = getRandomFilePath();
            }
        });
    }

    public void setWhichFreqText() {
        if (includeBoosts && includeCuts) {
            whichFreqText.setText("Which frequency was boosted or cut?");
        } else if (includeBoosts) {
            whichFreqText.setText("Which frequency was boosted?");
        } else {
            whichFreqText.setText("Which frequency was cut?");
        }
    }

    public String getRandomFilePath() {
        int randomNum;
        String directory;

        if (includeBoosts && includeCuts) {
            // include both boosts and cuts
            randomNum = new Random().nextInt(10);
            if (randomNum < 5) {
                directory = "Boosts";
            } else {
                directory = "Cuts";
            }
        } else if (includeBoosts) {
            // include just boosts
            randomNum = new Random().nextInt(5);
            directory = "Boosts";
        } else {
            // include just cuts
            randomNum = new Random().nextInt(5) + 5;
            directory = "Cuts";
        }

        String path = directory + "/" + assetMap.get(randomNum);
        return path;
    }

    public void generateAssetMap() {
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

    public void stopAudio() {
        // Stop the audio
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
            player = null;
            playAudioButton.setText("ᐅ");
        }
    }

    public void playAudio() {
        player.start();
        playAudioButton.setText("||");
    }

    @Override
    protected void onDestroy() {
        stopAudio(); // Must come before super
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAudio();
    }
}