package edu.neu.earful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class TestAudioActivity extends AppCompatActivity {
    Button playAudioButton;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_audio);

        playAudioButton = findViewById(R.id.play_button);

        playAudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player == null) {
                    player = new MediaPlayer();
                    AssetFileDescriptor afd = null;
                    try {
                        afd = getAssets().openFd("Boost_4kHz_+12db.mp3");
                        player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                        //player.setLooping(true); // Loops the audio
                        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                player.start();
                                playAudioButton.setText("Stop");
                            }
                        });

                        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                player.release();
                                player = null;
                                playAudioButton.setText("Play");
                            }
                        });

                        player.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // player is not null
                    player.release();
                    player = null;
                    playAudioButton.setText("Play");
                }
            }
        });
    }
}