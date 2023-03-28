package com.example.quizzgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.quizzgame.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    ActivitySignUpBinding binding;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_up);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.progressBar.setVisibility(View.INVISIBLE);

        binding.buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.buttonSignUp.setClickable(false);
                String userEmail = binding.editTextSignUpEmail.getText().toString();
                String userPassword = binding.editTextPassword.getText().toString();
                signUpFirebase(userEmail,userPassword);

            }
        });
    }
    public void signUpFirebase(String userEmail, String userPassword){
        {
            binding.progressBar.setVisibility(View.VISIBLE);
            auth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                Toast.makeText(SignUp.this
                                        , "Your account has been created", Toast.LENGTH_SHORT).show();
                                finish();
                                binding.progressBar.setVisibility(View.INVISIBLE);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUp.this
                                        , "There is a problem", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }
}