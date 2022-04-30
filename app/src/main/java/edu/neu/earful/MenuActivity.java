package edu.neu.earful;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    CheckBox cutsCheckBox;
    CheckBox boostsCheckBox;
    Button startMixingExerciseButton;
    Button startMusicianExerciseButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        cutsCheckBox = findViewById(R.id.cuts_checkbox);
        boostsCheckBox = findViewById(R.id.boosts_checkbox);
        startMixingExerciseButton = findViewById(R.id.start_mixing_exercise_button);
        startMusicianExerciseButton = findViewById(R.id.musicianMode_button);

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
        Intent notifyIntent = new Intent(this,MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (this, 3, notifyIntent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
                1000 * 60 * 60 * 24, pendingIntent);
    }

    public void onClick(View view) {
        int id = view.getId();

        if (id == startMixingExerciseButton.getId()) {
            launchMixingMode(view);
        } else if (id == startMusicianExerciseButton.getId()) {
            launchMusicianMode(view);
        }
    }

    public void launchMusicianMode(View view) {
        Intent musicianModeIntent = new Intent(getApplicationContext(), MusicianActivity.class);
        startActivity(musicianModeIntent);
    }

    public void launchMixingMode(View view) {
        Intent mixingModeIntent = new Intent(getApplicationContext(), MixingExerciseActivity.class);
        mixingModeIntent.putExtra("includeCuts", cutsCheckBox.isChecked());
        mixingModeIntent.putExtra("includeBoosts", boostsCheckBox.isChecked());
        startActivity(mixingModeIntent);
    }

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w("FAIL", "loadPost:onCancelled");
        }
    };

}


