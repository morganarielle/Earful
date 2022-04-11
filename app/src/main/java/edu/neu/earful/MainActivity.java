package edu.neu.earful;

import androidx.appcompat.app.AppCompatActivity;

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

    }

    public void onLogIn(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MainMenuActivity.class);
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