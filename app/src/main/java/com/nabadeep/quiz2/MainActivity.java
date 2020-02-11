package com.nabadeep.quiz2;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import com.nabadeep.quiz2.data.QuestionBank;
import com.nabadeep.quiz2.data.QuestionListAsyncResponse;
import com.nabadeep.quiz2.model.Question;
import com.nabadeep.quiz2.util.Prefs;

import java.util.ArrayList;
import java.util.List;


import static com.nabadeep.quiz2.R.*;
import static com.nabadeep.quiz2.R.id.imageButton2;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView title;
    private TextView counter;
    private TextView score;
    private TextView questioms;
    private TextView hscore;
    private Button Tbutton;
    private Button Fbutton;
    private ImageButton next;
    private ImageButton prev;
    public int questionIndex = 0;
    public int scoree=0;

    private Prefs prefs;
    private List<Question> questionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        title = findViewById(id.title);
        counter = findViewById(id.counterText);
        score = findViewById(id.scoreText);
        hscore=findViewById(id.highestscore);
        questioms = findViewById(id.question);

        Tbutton = findViewById(id.button);
        Fbutton = findViewById(id.button2);
        next = findViewById(imageButton2);
        prev = findViewById(id.imageButton);

        next.setOnClickListener(this);
        prev.setOnClickListener(this);
        Tbutton.setOnClickListener(this);
        Fbutton.setOnClickListener(this);
        score.setText("score:"+scoree);
        prefs=new Prefs(MainActivity.this);

        hscore.setText("Highest score :"+prefs.gethighscore());

        Log.d("intent", "onCreate: "+getIntent().getStringExtra("resume"));
         if(getIntent().getStringExtra("resume")!=null)
         {
             questionIndex=0;
             scoree=0;
         }
        questionList = new QuestionBank().getQuestion(new QuestionListAsyncResponse() {
            @Override
            public void processfinished(ArrayList<Question> questionArrayList) {
questioms.setText(questionArrayList.get(questionIndex).getQuestion());
counter.setText(questionIndex+1+"/" +questionList.size());
            }
        });
    }

    //get highest score

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.button:checkAnswer(true);

updatescore(10);
                break;
            case id.button2:checkAnswer(false);
updatescore(-5);
                break;
            case id.imageButton:if(questionIndex>0){
                questionIndex=(questionIndex-1)%questionList.size();
                updateQuestion();
            }

                break;
            case imageButton2:questionIndex=(questionIndex+1)%questionList.size();
                updateQuestion();
                break;
        }
    }

    public void updatescore(int iscore) {
        scoree+=iscore;
        score.setText("score:"+scoree);

    }




    private void checkAnswer(boolean selected) {

        boolean answer=questionList.get(questionIndex).isAnswer();
        int toastmessageId=0;
        if(selected==answer) {
            fadeview();

            toastmessageId= R.string.correctAnswer;

        }else {
            ShakeAnimation();

            toastmessageId= R.string.wrongAnswer;
        }
        Toast.makeText(MainActivity.this,toastmessageId,Toast.LENGTH_SHORT).show();
        questionIndex=(questionIndex+1)%questionList.size();
        updateQuestion();
    }

    private void updateQuestion() {
        questioms.setText(questionList.get(questionIndex).getQuestion());
        counter.setText(questionIndex+1+"/" + questionList.size());
    }
    private void fadeview(){
        final CardView cardView=findViewById(id.card);
        AlphaAnimation alphaAnimation=new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
             cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
 private void ShakeAnimation(){
     Animation shake= AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
    final CardView cardView=findViewById(R.id.card);
     cardView.setAnimation(shake);
     shake.setAnimationListener(new Animation.AnimationListener() {
         @Override
         public void onAnimationStart(Animation animation) {
             cardView.setCardBackgroundColor(Color.RED);
         }

         @Override
         public void onAnimationEnd(Animation animation) {
       cardView.setCardBackgroundColor(Color.WHITE);
         }

         @Override
         public void onAnimationRepeat(Animation animation) {

         }
     });
 }

    @Override
    protected void onPause() {

 prefs.sethighscore(scoree);

        super.onPause();
    }
}
