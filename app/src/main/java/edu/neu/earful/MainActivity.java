package edu.neu.earful;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button signInButton;
    private EditText usernameText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInButton = findViewById(R.id.signin);
        usernameText = findViewById(R.id.username);
        usernameText.addTextChangedListener(editTextWatcher);
        Intent notifyIntent = new Intent(this,MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (this, 3, notifyIntent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
                1000 * 60 * 60 * 24, pendingIntent);
    }

    public void onLogIn(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(activity2Intent);
    }

    TextWatcher editTextWatcher = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Enable the button if a username is entered
            signInButton.setEnabled(!signInButton.getText().toString().equals(""));
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };
}