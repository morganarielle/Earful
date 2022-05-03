package edu.neu.earful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import edu.neu.earful.menu.MainActivity;

public class LaunchScreenActivity extends AppCompatActivity {
    ImageView app_icon_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        app_icon_image = findViewById(R.id.app_icon);
        app_icon_image.animate().rotation(-400).setDuration(1000).setStartDelay(1000);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainActivityIntent = new Intent(LaunchScreenActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);
            }
        }, 1250);
    }
}