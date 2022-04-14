package edu.neu.earful;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class IntervalTrainingActivity extends AppCompatActivity {
    private DifficultyLevel difficulty = DifficultyLevel.LEVEL1;
    ImageButton playButton;
    ImageButton menuButton;
    GridView intervalOptions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval_training);
        playButton = findViewById(R.id.playButton);
        menuButton = findViewById(R.id.menuButton);
        intervalOptions = findViewById(R.id.intervalOptions);

        ArrayList<Interval> intervals = Interval.getIntervalsForDifficulty(difficulty);
        ArrayList<IntervalCard> intervalCards = intervals.stream().map(IntervalCard::new).collect(Collectors.toCollection(ArrayList::new));

        IntervalGVAdapter adapter = new IntervalGVAdapter(this, intervalCards);
        intervalOptions.setAdapter(adapter);

        intervalOptions.setNumColumns(3);
    }

}
