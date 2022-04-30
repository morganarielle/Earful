package edu.neu.earful;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button signInButton;
    private Button createNewAccountButton;
    private EditText usernameText;
    private EditText passwordText;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInButton = findViewById(R.id.signin);
        createNewAccountButton = findViewById(R.id.createNewAccount);
        usernameText = findViewById(R.id.username);
        usernameText.addTextChangedListener(editTextWatcher);
        passwordText = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();

        createNewAccountButton.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
            startActivity(activity2Intent);
        });

        signInButton.setOnClickListener(view -> {
            mAuth.signInWithEmailAndPassword(usernameText.getText().toString(), passwordText.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            currentUser = mAuth.getCurrentUser();
                            startMenuActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startMenuActivity();
        }
    }

    public void onLogIn(View view) {
        startMenuActivity();
    }

    private void startMenuActivity() {
        Intent activity2Intent = new Intent(getApplicationContext(), ModeMenuActivity.class);
        activity2Intent.putExtra("user", currentUser);
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