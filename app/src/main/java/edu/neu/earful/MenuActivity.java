package edu.neu.earful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MenuActivity extends AppCompatActivity {
    CheckBox cutsCheckBox;
    CheckBox boostsCheckBox;
    Button startMixingExerciseButton;
    Button startMusicianExerciseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        cutsCheckBox = findViewById(R.id.cuts_checkbox);
        boostsCheckBox = findViewById(R.id.boosts_checkbox);
        startMixingExerciseButton = findViewById(R.id.start_mixing_exercise_button);
        startMusicianExerciseButton = findViewById(R.id.musicianMode_button);

        cutsCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cutsCheckBox.isChecked() && !boostsCheckBox.isChecked()) {
                    boostsCheckBox.setChecked(true);
                }
            }
        });

        boostsCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!boostsCheckBox.isChecked() && !cutsCheckBox.isChecked()) {
                    cutsCheckBox.setChecked(true);
                }
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
        Intent mixingModeIntent = new Intent(getApplicationContext(), TestAudioActivity.class);
        mixingModeIntent.putExtra("includeCuts", cutsCheckBox.isChecked());
        mixingModeIntent.putExtra("includeBoosts", boostsCheckBox.isChecked());
        startActivity(mixingModeIntent);
    }
}