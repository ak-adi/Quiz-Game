package com.example.quizzgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.quizzgame.databinding.ActivityScorePageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Score_Page extends AppCompatActivity {
    ActivityScorePageBinding binding;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child("Scores");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    String userCorrect, userWrong;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_score_page);
        binding = ActivityScorePageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userUID = user.getUid();
                userCorrect = snapshot.child(userUID).child("correct").getValue().toString();
                userWrong = snapshot.child(userUID).child("wrong").getValue().toString();

                binding.correctScore.setText(""+userCorrect);
                binding.wrongScore.setText(""+userWrong);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Score_Page.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        binding.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}