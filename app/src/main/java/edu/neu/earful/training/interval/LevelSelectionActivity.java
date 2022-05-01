package edu.neu.earful.training.interval;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import edu.neu.earful.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LevelSelectionActivity extends AppCompatActivity {
    private Button level1button, level2button, level3button, level4button, level5button;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference database;
    private DatabaseReference currentUserReference;
    private Integer musicianPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);
        String DIFFICULTY = "difficulty";

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        currentUserReference = database.child(getString(R.string.db_key_users)).child(currentUser.getUid());

        level1button = findViewById(R.id.level1Button);
        level2button = findViewById(R.id.level2Button);
        level3button = findViewById(R.id.level3Button);
        level4button = findViewById(R.id.level4Button);
        level5button = findViewById(R.id.level5Button);
        Intent i = new Intent(LevelSelectionActivity.this, IntervalTrainingActivity.class);

        musicianPoints = 0;
        currentUserReference.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.v("TAG", "Error getting data", task.getException());
            }
            else {
                musicianPoints = task.getResult().child(getString(R.string.db_key_musician_score)).getValue(Integer.class);
                Log.v("TAG", "Accumulated points: " + musicianPoints);
                for (DifficultyLevel level: DifficultyLevel.values()) {
                    int pointsNeeded = DifficultyLevel.pointsNeededToReachLevel(level);
                    Button levelButton = getLevelButton(level);
                    Log.v("TAG", "Level: " + level);
                    Log.v("TAG", "Button: " + levelButton);
                    if (levelButton != null) {
                        levelButton.setOnClickListener(view -> {
                            i.putExtra(DIFFICULTY, level);
                            startActivity(i);
                        });
                        levelButton.setEnabled(musicianPoints >= pointsNeeded);
                    }
                }
            }
        });
    }

    private Button getLevelButton(DifficultyLevel level) {
        switch(level) {
            case LEVEL1:
                return level1button;
            case LEVEL2:
                return level2button;
            case LEVEL3:
                return level3button;
            case LEVEL4:
                return level4button;
            case LEVEL5:
                return level5button;
        }
        return null;
    }
}