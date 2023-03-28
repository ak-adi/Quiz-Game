package com.example.quizzgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.quizzgame.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_forgot_password);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userMail = binding.editTextMail.getText().toString();
                forgotPassword(userMail);
            }
        });
    }
    public void forgotPassword(String userMail){
        binding.progressBar2.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(userMail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgotPassword.this,
                                    "We send a link to reset your password", Toast.LENGTH_SHORT).show();
                            binding.buttonSend.setClickable(false);
                            binding.progressBar2.setVisibility(View.INVISIBLE);
                        }
                        else{
                            Toast.makeText(ForgotPassword.this,
                                    "Sorry, There is a problem", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        finish();
    }
}