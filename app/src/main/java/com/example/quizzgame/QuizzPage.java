package com.example.quizzgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.quizzgame.databinding.ActivityQuizzPageBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizzPage extends AppCompatActivity {
    ActivityQuizzPageBinding binding;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child("Questions");

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    DatabaseReference databaseReference2 = database.getReference();

    String quizQuestion;
    String quizOptionA;
    String quizOptionB;
    String quizOptionC;
    String quizOptionD;
    String quizAnswer;
    int questionCount;
    int questionNumber=1;

    String userAnswer;

    int userCorrect= 0;
    int userWrong=0;

    CountDownTimer countDownTimer;
    private static final long TOTAL_TIME = 25000;
    Boolean timeContinue;
    long leftTime = TOTAL_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_quizz_page);
        binding = ActivityQuizzPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        game();

        binding.nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
                game();
            }
        });

        binding.a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
                userAnswer = "A";
                if (quizAnswer.equals(userAnswer))
                {
                    binding.a.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    binding.textViewCorrectAnswer.setText(" "+ userCorrect);
                }
                else{
                    binding.a.setBackgroundColor(Color.RED);
                    userWrong++;
                    binding.textViewWrongAnswer.setText(" " + userWrong);
                    findAnswer();
                }

            }
        });
        binding.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
                userAnswer = "B";
                if (quizAnswer.equals(userAnswer))
                {
                    binding.b.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    binding.textViewCorrectAnswer.setText(" "+ userCorrect);
                }
                else{
                    binding.b.setBackgroundColor(Color.RED);
                    userWrong++;
                    binding.textViewWrongAnswer.setText(" " + userWrong);
                    findAnswer();
                }
            }
        });
        binding.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
                userAnswer = "C";
                if (quizAnswer.equals(userAnswer))
                {
                    binding.c.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    binding.textViewCorrectAnswer.setText(" "+ userCorrect);
                }
                else{
                    binding.c.setBackgroundColor(Color.RED);
                    userWrong++;
                    binding.textViewWrongAnswer.setText(" " + userWrong);
                    findAnswer();
                }
            }
        });
        binding.d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
                userAnswer = "D";
                if (quizAnswer.equals(userAnswer))
                {
                    binding.d.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    binding.textViewCorrectAnswer.setText(" "+ userCorrect);
                }
                else{
                    binding.d.setBackgroundColor(Color.RED);
                    userWrong++;
                    binding.textViewWrongAnswer.setText(" " + userWrong);
                    findAnswer();

                }
            }
        });
        binding.finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalScore();
                Intent i = new Intent(QuizzPage.this,Score_Page.class);
                startActivity(i);
                finish();
            }
        });

    }
   public void game()
    {
        startTimer();

        binding.a.setBackgroundColor(Color.WHITE);
        binding.b.setBackgroundColor(Color.WHITE);
        binding.c.setBackgroundColor(Color.WHITE);
        binding.d.setBackgroundColor(Color.WHITE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                questionCount =(int) dataSnapshot.getChildrenCount();

                quizQuestion = dataSnapshot.child(String.valueOf(questionNumber)).child("Q").getValue().toString();
                quizOptionA = dataSnapshot.child(String.valueOf(questionNumber)).child("A").getValue().toString();
                quizOptionB = dataSnapshot.child(String.valueOf(questionNumber)).child("B").getValue().toString();
                quizOptionC = dataSnapshot.child(String.valueOf(questionNumber)).child("C").getValue().toString();
                quizOptionD = dataSnapshot.child(String.valueOf(questionNumber)).child("D").getValue().toString();
                quizAnswer = dataSnapshot.child(String.valueOf(questionNumber)).child("Answer").getValue().toString();

                binding.question.setText(quizQuestion);
                binding.a.setText(quizOptionA);
                binding.b.setText(quizOptionB);
                binding.c.setText(quizOptionC);
                binding.d.setText(quizOptionD);

                if (questionNumber<questionCount)
                {
                    questionNumber++;
                }
                else{
                    Toast.makeText(QuizzPage.this, "You answered all the questions", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(QuizzPage.this, "There is an error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void findAnswer()
    {
        if (quizAnswer.equals("A"))
        {
            binding.a.setBackgroundColor(Color.GREEN);
        }
        else if (quizAnswer.equals("B")){
            binding.b.setBackgroundColor(Color.GREEN);
          }
       else if (quizAnswer.equals("C")){
        binding.c.setBackgroundColor(Color.GREEN);
         }
       else if (quizAnswer.equals("D")){
        binding.d.setBackgroundColor(Color.GREEN);
        }
    }

    public void startTimer()
    {
        countDownTimer = new CountDownTimer(leftTime,1000) {
            @Override
            public void onTick(long l) {
                leftTime = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeContinue = false;
                pauseTimer();
                binding.question.setText("Sorry, Time is up!");

            }
        }.start();
        timeContinue = true;
    }
    public void resetTimer(){
        leftTime = TOTAL_TIME;
        updateCountDownText();
    }
    public void updateCountDownText(){
        int second =(int) (leftTime / 1000) % 60;
        binding.textViewTimer.setText(" " + second);

    }
    public void pauseTimer()
    {
        countDownTimer.cancel();
        timeContinue = false;

    }
    public void finalScore()
    {
        String userUID = user.getUid();
        databaseReference2.child("Scores").child(userUID).child("correct").setValue(userCorrect)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(QuizzPage.this, "Scores sent successfully",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
        databaseReference2.child("Scores").child(userUID).child("wrong").setValue(userWrong);
        

    }
}