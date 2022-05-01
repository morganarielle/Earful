package edu.neu.earful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ModeMenuActivity extends AppCompatActivity {
    Button questionOptionsButton;
    Button chooseLevelButton;
    Button mixingTipsButton;
    FloatingActionButton settingsButton;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference database;
    private DatabaseReference currentUserReference;
    TextView musicianModePointsTV;
    TextView mixingModePointsTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_menu);

        chooseLevelButton = findViewById(R.id.choose_level_button);
        questionOptionsButton = findViewById(R.id.mixing_practice_button);
        mixingTipsButton = findViewById(R.id.mixing_tips_button);
        settingsButton = findViewById(R.id.settings_button);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        currentUserReference = database.child(getString(R.string.db_key_users)).child(currentUser.getUid());


        // set total points TVs
        setTotalPoints();
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

    public void launchChooseLevel(View view) {
        Intent chooseLevelsIntent = new Intent(getApplicationContext(), LevelSelectionActivity.class);
        startActivity(chooseLevelsIntent);
    }

    public void launchViewOptions(View view) {
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

    public void setTotalPoints() {
        currentUserReference.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.v("TAG", "Error getting data", task.getException());
            } else {
                musicianModePointsTV = findViewById(R.id.musician_total_points_value);
                boolean musicianScoreExists = task.getResult().child(getString(R.string.db_key_musician_score)).exists();
                final int musicianScore = musicianScoreExists ? task.getResult().child(getString(R.string.db_key_musician_score)).getValue(Integer.class) : 0;
                Log.v("TAG", "Musician score: " + musicianScore);
                musicianModePointsTV.setText(Integer.toString(musicianScore));

                mixingModePointsTV = findViewById(R.id.mixing_total_points_value);

                boolean mixingScoreExists = task.getResult().child(getString(R.string.db_key_mixing_score)).exists();
                final int mixingScore = mixingScoreExists ? task.getResult().child(getString(R.string.db_key_mixing_score)).getValue(Integer.class) : 0;
                Log.v("TAG", "Mixing score: " + mixingScore);
                mixingModePointsTV.setText(Integer.toString(mixingScore));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTotalPoints();
    }
}