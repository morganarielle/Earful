package edu.neu.earful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MixingTrainingActivity extends AppCompatActivity {
    Button playAudioButton;
    Button submitButton;
    TextView whichFreqText;
    ProgressBar progressBar;
    ImageView putImageHereImageView;

    RadioGroup radioGroup;
    RadioButton radioButton_4kHz;
    RadioButton radioButton_2kHz;
    RadioButton radioButton_1kHz;
    RadioButton radioButton_500Hz;
    RadioButton radioButton_250Hz;

    MediaPlayer questionPlayer;
    MediaPlayer fxPlayer;
    Map<Integer, String> assetMap = new HashMap<>();
    String path;
    boolean includeCuts;
    boolean includeBoosts;
    boolean resetProgress = false;
    boolean audioPlayed = false;

    Toast toast;

    int correctIndex = -1; // -1 Pink Noise, 0 1kHz Boost, 1 250Hz Boost, 2 2kHz Boost, 3 4kHz Boost, 4 500Hz Boost, 5 1kHz Cut, 6 250Hz Cut, 7 2kHz Cut, 8 4kHz Cut, 9 500Hz Cut
    int pointsAwarded = 0; // we can award 1 point for boosts only, 2 points for cuts only, 3 points for both

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mixing_training);

        playAudioButton = findViewById(R.id.play_button);
        progressBar = findViewById(R.id.progress_bar);
        submitButton = findViewById(R.id.submit_button);
        radioGroup = findViewById(R.id.radio_group);
        whichFreqText = findViewById(R.id.which_freq_textview);
        putImageHereImageView = findViewById(R.id.put_image_here_imageview);

        radioButton_4kHz = findViewById(R.id.button_4kHz);
        radioButton_2kHz = findViewById(R.id.button_2kHz);
        radioButton_1kHz = findViewById(R.id.button_1kHz);
        radioButton_500Hz = findViewById(R.id.button_500Hz);
        radioButton_250Hz = findViewById(R.id.button_250Hz);

        includeCuts = getIntent().getBooleanExtra("includeCuts", false);
        includeBoosts = getIntent().getBooleanExtra("includeBoosts", false);

        setWhichFreqTextAndImage();
        generateAssetMap();
        path = getRandomFilePath();

        playAudioButton.setOnClickListener(view -> {
            if (questionPlayer == null) {
                questionPlayer = new MediaPlayer();
                questionPlayer.setVolume((float) 0.5,(float) 0.5);
                AssetFileDescriptor afd;
                try {
                    afd = getAssets().openFd(path);
                    questionPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    //player.setLooping(true); // Loops the audio
                    questionPlayer.setOnPreparedListener(mediaPlayer -> {
                        // Play the audio
                        playAudio();
                    });

                    questionPlayer.setOnCompletionListener(mediaPlayer -> stopAudio());

                    questionPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Stop the audio
                stopAudio();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (audioPlayed) {
                    // Enabled the submit button
                    submitButton.setEnabled(true);
                }
            }
        });

        submitButton.setOnClickListener(view -> {
            // Check if we should stop playing the audio
            if (questionPlayer != null) {
                // Stop the audio
                stopAudio();
            }

            // hide any previous toasts
            if (toast != null) {
                toast.cancel();
            }

            // Check what radio button was clicked
            int checkedButtonId = radioGroup.getCheckedRadioButtonId();
            if (checkedButtonId != -1) {
                // one of the radio buttons is checked
                if (checkedButtonId == radioButton_4kHz.getId()) {
                    if (correctIndex == 3 || correctIndex == 8) {
                        // 4kHz. Correct!
                        pointsAwarded += calcPointsToAward();
                        toast = Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT);
                        initializeFXPlayer("FX/answer_correct.wav");
                    } else {
                        toast = Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT);
                        initializeFXPlayer("FX/answer_wrong.wav");
                    }
                } else if (checkedButtonId == radioButton_2kHz.getId()) {
                    if (correctIndex == 2 || correctIndex == 7) {
                        // 2kHz. Correct!
                        pointsAwarded += calcPointsToAward();
                        toast = Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT);
                        initializeFXPlayer("FX/answer_correct.wav");
                    } else {
                        toast = Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT);
                        initializeFXPlayer("FX/answer_wrong.wav");
                    }
                } else if (checkedButtonId == radioButton_1kHz.getId()) {
                    if (correctIndex == 0 || correctIndex == 5) {
                        // 1kHz. Correct!
                        pointsAwarded += calcPointsToAward();
                        toast = Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT);
                        initializeFXPlayer("FX/answer_correct.wav");
                    } else {
                        toast = Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT);
                        initializeFXPlayer("FX/answer_wrong.wav");
                    }
                } else if (checkedButtonId == radioButton_500Hz.getId()) {
                    if (correctIndex == 4 || correctIndex == 9) {
                        // 500Hz. Correct!
                        pointsAwarded += calcPointsToAward();
                        toast = Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT);
                        initializeFXPlayer("FX/answer_correct.wav");
                    } else {
                        toast = Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT);
                        initializeFXPlayer("FX/answer_wrong.wav");
                    }
                } else if (checkedButtonId == radioButton_250Hz.getId()) {
                    if (correctIndex == 1 || correctIndex == 6) {
                        // 250Hz. Correct!
                        pointsAwarded += calcPointsToAward();
                        toast = Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT);
                        initializeFXPlayer("FX/answer_correct.wav");
                    } else {
                        toast = Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT);
                        initializeFXPlayer("FX/answer_wrong.wav");
                    }
                }
                // Show the toast
                toast.show();

                // Uncheck all
                radioGroup.clearCheck();

                // Disabled the submit button
                submitButton.setEnabled(false);
            }

            // Update the progress
            int currentProgress = progressBar.getProgress();
            if (currentProgress == 90) {
                progressBar.setProgress(100);
                stopFXAudio();

                // TODO: write the score to the database
                System.out.println("Total points awarded: " + pointsAwarded);

                Intent resultsActivityIntent = new Intent(MixingTrainingActivity.this, ResultsActivity.class);
                int scorePercentage;
                if (includeBoosts && includeCuts) {
                    scorePercentage = (pointsAwarded * 10) / 3;
                } else if (includeCuts) {
                    scorePercentage = (pointsAwarded * 10) / 2;
                } else {
                    scorePercentage = (pointsAwarded * 10);
                }
                resultsActivityIntent.putExtra("score", scorePercentage);
                startActivity(resultsActivityIntent);

                resetProgress = true;
                pointsAwarded = 0;
            } else {
                progressBar.setProgress(currentProgress + 10);
            }

            // Reset audioPlayed flag
            audioPlayed = false;

            // Get a new random file to play for the user
            path = getRandomFilePath();
        });
    }

    public int calcPointsToAward() {
        if (includeBoosts && includeCuts) {
            return 3;
        } else if (includeCuts) {
            return 2;
        } else {
            return 1;
        }
    }

    public void setWhichFreqTextAndImage() {
        if (includeBoosts && includeCuts) {
            whichFreqText.setText(R.string.boosted_or_cut_question);
            putImageHereImageView.setImageResource(R.drawable.boost_or_cut);
        } else if (includeBoosts) {
            whichFreqText.setText(R.string.boosted_question);
            putImageHereImageView.setImageResource(R.drawable.boost);
        } else {
            whichFreqText.setText(R.string.cut_question);
            putImageHereImageView.setImageResource(R.drawable.cut);
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

        correctIndex = randomNum;
        return directory + "/" + assetMap.get(randomNum);
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

    public void initializeFXPlayer(String path) {
        stopFXAudio();
        if (fxPlayer == null) {
            fxPlayer = new MediaPlayer();
            AssetFileDescriptor afd;
            try {
                afd = getAssets().openFd( path);
                fxPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                fxPlayer.setOnPreparedListener(mediaPlayer -> {
                    // Play the audio
                    playFXAudio();
                });

                fxPlayer.setOnCompletionListener(mediaPlayer -> stopFXAudio());

                fxPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Stop the audio
            stopFXAudio();
        }
    }

    public void stopAudio() {
        // Stop the audio
        if (questionPlayer != null) {
            if (questionPlayer.isPlaying()) {
                questionPlayer.stop();
            }
            questionPlayer.release();
            questionPlayer = null;
            playAudioButton.setText("ᐅ");
        }
    }

    public void playAudio() {
        questionPlayer.start();
        playAudioButton.setText("||");
        audioPlayed = true;
        if (radioGroup.getCheckedRadioButtonId() != -1) {
            // Enabled the submit button
            submitButton.setEnabled(true);
        }
    }

    public void playFXAudio() {
        fxPlayer.start();
    }

    public void stopFXAudio() {
        // Stop the audio
        if (fxPlayer != null) {
            if (fxPlayer.isPlaying()) {
                fxPlayer.stop();
            }
            fxPlayer.release();
            fxPlayer = null;
        }
    }


    @Override
    public void finish() {
        super.finish();
        if (toast != null) {
            toast.cancel();
        }
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
        if (toast != null) {
            toast.cancel();
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