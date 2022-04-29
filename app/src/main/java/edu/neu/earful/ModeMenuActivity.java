package edu.neu.earful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModeMenuActivity extends AppCompatActivity {
    Button questionOptionsButton;
    Button chooseLevelButton;
    Button mixingTipsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_menu);

        chooseLevelButton = findViewById(R.id.choose_level_button);
        questionOptionsButton = findViewById(R.id.mixing_practice_button);
        mixingTipsButton = findViewById(R.id.mixing_tips_button);
    }

    public void onClick(View view) {
        int id = view.getId();

        if (id == questionOptionsButton.getId()) {
            launchViewOptions(view);
        } else if (id == chooseLevelButton.getId()) {
            launchChooseLevel(view);
        } else if (id == mixingTipsButton.getId()) {
            launchMixingTips(view);
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

    public void launchMixingTips(View view) {
        Intent mixingTipsIntent = new Intent(getApplicationContext(), MixingTipsActivity.class);
        startActivity(mixingTipsIntent);
    }
}