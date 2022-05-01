package edu.neu.earful.training;

import static edu.neu.earful.training.DifficultyLevel.LEVEL1;
import static edu.neu.earful.training.DifficultyLevel.LEVEL2;
import static edu.neu.earful.training.DifficultyLevel.LEVEL3;
import static edu.neu.earful.training.DifficultyLevel.LEVEL4;
import static edu.neu.earful.training.DifficultyLevel.LEVEL5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import edu.neu.earful.R;
import edu.neu.earful.training.interval.IntervalTrainingActivity;

public class LevelSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);
        String DIFFICULTY = "difficulty";
        Intent i = new Intent(LevelSelectionActivity.this, IntervalTrainingActivity.class);

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