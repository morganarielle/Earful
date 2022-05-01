package edu.neu.earful.training.mixing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import edu.neu.earful.R;

public class MixingOptionsActivity extends AppCompatActivity {
    CheckBox cutsCheckBox;
    CheckBox boostsCheckBox;
    Button startMixingExerciseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mixing_options);

        cutsCheckBox = findViewById(R.id.cuts_checkbox);
        boostsCheckBox = findViewById(R.id.boosts_checkbox);
        startMixingExerciseButton = findViewById(R.id.start_mixing_exercise_button);

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

    public void launchMixingExerciseActivity(View view) {
        int id = view.getId();

        if (id == startMixingExerciseButton.getId()) {
            Intent mixingModeIntent = new Intent(getApplicationContext(), MixingTrainingActivity.class);
            mixingModeIntent.putExtra("includeCuts", cutsCheckBox.isChecked());
            mixingModeIntent.putExtra("includeBoosts", boostsCheckBox.isChecked());
            startActivity(mixingModeIntent);
        }
    }

}