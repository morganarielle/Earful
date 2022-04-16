package edu.neu.earful;

import static edu.neu.earful.DifficultyLevel.LEVEL1;
import static edu.neu.earful.DifficultyLevel.LEVEL2;
import static edu.neu.earful.DifficultyLevel.LEVEL3;
import static edu.neu.earful.DifficultyLevel.LEVEL4;
import static edu.neu.earful.DifficultyLevel.LEVEL5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MusicianActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician);
        String DIFFICULTY = "difficulty";
        Intent i = new Intent(MusicianActivity.this, IntervalTrainingActivity.class);

        Button level1button = findViewById(R.id.level1Button);
        level1button.setOnClickListener(view -> {
            i.putExtra(DIFFICULTY, LEVEL1);
            startActivity(i);
        });

        Button level2button = findViewById(R.id.level2Button);
        level2button.setOnClickListener(view -> {
            i.putExtra(DIFFICULTY, LEVEL2);
            startActivity(i);
        });

        Button level3button = findViewById(R.id.level3Button);
        level3button.setOnClickListener(view -> {
            i.putExtra(DIFFICULTY, LEVEL3);
            startActivity(i);
        });

        Button level4button = findViewById(R.id.level4Button);
        level4button.setOnClickListener(view -> {
            i.putExtra(DIFFICULTY, LEVEL4);
            startActivity(i);
        });

        Button level5button = findViewById(R.id.level5Button);
        level5button.setOnClickListener(view -> {
            i.putExtra(DIFFICULTY, LEVEL5);
            startActivity(i);
        });
    }
}