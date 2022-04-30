package edu.neu.earful;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthException;

public class CreateAccountActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Button createAccount;
    private Button logIntoAccount;
    private EditText emailET;
    private EditText passwordET;
    private EditText passwordConfirmET;
    private TextView doPasswordsMatchTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mAuth = FirebaseAuth.getInstance();
        createAccount = (Button) findViewById(R.id.createAccount);
        logIntoAccount = (Button) findViewById(R.id.logIntoAccount);
        emailET = (EditText) findViewById(R.id.email);
        passwordET = (EditText) findViewById(R.id.password);
        passwordConfirmET = (EditText) findViewById(R.id.reenterPassword);
        doPasswordsMatchTV = (TextView) findViewById(R.id.doPasswordsMatch);

        emailET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateCreateAccountButton();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateCreateAccountButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateCreateAccountButton();
            }
        });

        passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.v("TAG", "passwordET:beforeTextChanged called");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.v("TAG", "passwordET:onTextChanged called");
                updatePasswordMatchTV();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.v("TAG", "passwordET:afterTextChanged called");
                updatePasswordMatchTV();
            }
        });

        passwordConfirmET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.v("TAG", "passwordConfirmET:beforeTextChanged called");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.v("TAG", "passwordConfirmET:onTextChanged called");
                updatePasswordMatchTV();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.v("TAG", "passwordConfirmET:afterTextChanged called");
                updatePasswordMatchTV();
            }
        });

        createAccount.setOnClickListener(view -> mAuth.createUserWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, direct to menu screen
                        Log.d("TAG", "createUserWithEmail:success");
                        currentUser = mAuth.getCurrentUser();
                        Intent activity2Intent = new Intent(getApplicationContext(), ModeMenuActivity.class);
                        activity2Intent.putExtra("user", currentUser);
                        startActivity(activity2Intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Exception e = task.getException();
                        Log.w("TAG", "createUserWithEmail:failure", e);
                        if (e instanceof FirebaseAuthWeakPasswordException) {
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed. Password not strong enough: " + ((FirebaseAuthWeakPasswordException) e).getReason(), Toast.LENGTH_LONG).show();
                        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed. Invalid email address.", Toast.LENGTH_LONG).show();
                        } else if (e instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed. Email is already in use.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }));

        logIntoAccount.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(activity2Intent);
        });
    }

    private void updatePasswordMatchTV() {
        Log.v("TAG", "updatePasswordMatchTV");
        Log.v("TAG", "passwordET: " + passwordET.getText().toString());
        Log.v("TAG", "passwordConfirmET: " + passwordConfirmET.getText().toString());
        Log.v("TAG", "doPasswordsMatch: " + doPasswordsMatchTV.getText().toString());
        // password fields match & text display says they do not match
        if (passwordET.getText().toString().equals(passwordConfirmET.getText().toString()) && !doPasswordsMatchTV.getText().toString().equals(R.string.passwords_match)) {
            Log.v("TAG", "updatePasswordMatchTV: change to passwords_match");
            doPasswordsMatchTV.setText(R.string.passwords_match);
            updateCreateAccountButton();
        }
        // password fields do not match & text display says they do match
        else if (!passwordET.getText().toString().equals(passwordConfirmET.getText().toString()) && !doPasswordsMatchTV.getText().toString().equals(R.string.passwords_do_not_match)){
            Log.v("TAG", "updatePasswordMatchTV: change to passwords_do_not_match");
            doPasswordsMatchTV.setText(R.string.passwords_do_not_match);
            updateCreateAccountButton();
        }
    }

    private void updateCreateAccountButton() {
        createAccount.setEnabled(passwordET.getText().toString().equals(passwordConfirmET.getText().toString()) &&
                !emailET.getText().toString().equals(""));
    }
}
