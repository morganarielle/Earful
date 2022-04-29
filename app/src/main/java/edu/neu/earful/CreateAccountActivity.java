package edu.neu.earful;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Button createAccount;
    private EditText emailET;
    private EditText usernameET;
    private EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mAuth = FirebaseAuth.getInstance();
        createAccount = (Button) findViewById(R.id.createAccount);
        emailET = (EditText) findViewById(R.id.email);
        usernameET = (EditText) findViewById(R.id.username);
        passwordET = (EditText) findViewById(R.id.password);

        createAccount.setOnClickListener(view -> mAuth.createUserWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, direct to menu screen
                        Log.d("TAG", "createUserWithEmail:success");
                        currentUser = mAuth.getCurrentUser();
                        Intent activity2Intent = new Intent(getApplicationContext(), MenuActivity.class);
                        activity2Intent.putExtra("user", currentUser);
                        startActivity(activity2Intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }));
    }
}
