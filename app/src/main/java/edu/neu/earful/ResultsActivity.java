package edu.neu.earful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {
    Button menuButton;
    Button retryButton;
    TextView scoreValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        menuButton = findViewById(R.id.menu_button);
        retryButton = findViewById(R.id.retry_button);
        scoreValueTextView = findViewById(R.id.exercise_score_value);

        scoreValueTextView.setText(getIntent().getIntExtra("score", -1) + "%");
    }

    public void onClick(View view) {
        int id = view.getId();

        if (id == menuButton.getId()) {
            Intent menuActivityIntent = new Intent(ResultsActivity.this, MenuActivity.class);
            startActivity(menuActivityIntent);
        } else if (id == retryButton.getId()) {
            finish();
        }
    }

}