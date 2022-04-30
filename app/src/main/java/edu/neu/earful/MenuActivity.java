package edu.neu.earful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {
    CheckBox cutsCheckBox;
    CheckBox boostsCheckBox;
    Button startMixingExerciseButton;
    Button startMusicianExerciseButton;
    FloatingActionButton settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        settingsButton = findViewById(R.id.settings);
        settingsButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent activity2Intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(activity2Intent);
        });

        cutsCheckBox = findViewById(R.id.cuts_checkbox);
        boostsCheckBox = findViewById(R.id.boosts_checkbox);
        startMixingExerciseButton = findViewById(R.id.start_mixing_exercise_button);
        startMusicianExerciseButton = findViewById(R.id.musicianMode_button);

        cutsCheckBox.setOnClickListener(view -> {
            if (!cutsCheckBox.isChecked() && !boostsCheckBox.isChecked()) {
                boostsCheckBox.setChecked(true);
            }
        });

        boostsCheckBox.setOnClickListener(view -> {
            if (!boostsCheckBox.isChecked() && !cutsCheckBox.isChecked()) {
                cutsCheckBox.setChecked(true);
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();

        if (id == startMixingExerciseButton.getId()) {
            launchMixingMode(view);
        } else if (id == startMusicianExerciseButton.getId()) {
            launchMusicianMode(view);
        }
    }

    public void launchMusicianMode(View view){
        Intent musicianModeIntent = new Intent(getApplicationContext(), MusicianActivity.class);
        startActivity(musicianModeIntent);
    }

    public void launchMixingMode(View view){
        Intent mixingModeIntent = new Intent(getApplicationContext(), MixingExerciseActivity.class);
        mixingModeIntent.putExtra("includeCuts", cutsCheckBox.isChecked());
        mixingModeIntent.putExtra("includeBoosts", boostsCheckBox.isChecked());
        startActivity(mixingModeIntent);
    }
}