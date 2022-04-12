package edu.neu.earful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void launchMusicianMode(View view){
        Intent activity2Intent = new Intent(getApplicationContext(), MusicianActivity.class);
        startActivity(activity2Intent);
    }

    public void launchMixingMode(View view){
        Intent activity2Intent = new Intent(getApplicationContext(), TestAudioActivity.class);
        startActivity(activity2Intent);
    }
}