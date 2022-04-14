package edu.neu.earful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MusicianActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician);
    }

    public void launchIntervalTrainingDemo(View view){
        Intent activity2Intent = new Intent(getApplicationContext(), IntervalTrainingActivity.class);
        startActivity(activity2Intent);
    }
}