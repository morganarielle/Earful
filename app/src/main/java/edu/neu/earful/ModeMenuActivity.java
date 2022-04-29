package edu.neu.earful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModeMenuActivity extends AppCompatActivity {
    Button questionOptionsButton;
    Button chooseLevelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_menu);

        chooseLevelButton = findViewById(R.id.choose_level_button);
        questionOptionsButton = findViewById(R.id.questions_options_button);
    }

    public void onClick(View view) {
        int id = view.getId();

        if (id == questionOptionsButton.getId()) {
            launchViewOptions(view);
        } else if (id == chooseLevelButton.getId()) {
            launchChooseLevel(view);
        }
    }

    public void launchChooseLevel(View view){
        Intent chooseLevelsIntent = new Intent(getApplicationContext(), LevelSelectionActivity.class);
        startActivity(chooseLevelsIntent);
    }

    public void launchViewOptions(View view){
        Intent viewOptionsIntent = new Intent(getApplicationContext(), MixingOptionsActivity.class);
        startActivity(viewOptionsIntent);
    }
}