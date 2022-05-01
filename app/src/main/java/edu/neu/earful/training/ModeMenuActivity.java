package edu.neu.earful.training;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.neu.earful.R;
import edu.neu.earful.training.mixing.MixingOptionsActivity;
import edu.neu.earful.training.mixing.MixingTipsActivity;
import edu.neu.earful.settings.SettingsActivity;

public class ModeMenuActivity extends AppCompatActivity {
    Button questionOptionsButton;
    Button chooseLevelButton;
    Button mixingTipsButton;
    FloatingActionButton settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_menu);

        chooseLevelButton = findViewById(R.id.choose_level_button);
        questionOptionsButton = findViewById(R.id.mixing_practice_button);
        mixingTipsButton = findViewById(R.id.mixing_tips_button);
        settingsButton = findViewById(R.id.settings_button);
    }

    public void onClick(View view) {
        int id = view.getId();

        if (id == questionOptionsButton.getId()) {
            launchViewOptions(view);
        } else if (id == chooseLevelButton.getId()) {
            launchChooseLevel(view);
        } else if (id == mixingTipsButton.getId()) {
            launchMixingTips(view);
        } else if (id == settingsButton.getId()) {
            launchSettings(view);
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

    public void launchSettings(View view) {
        Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(settingsIntent);
    }
}