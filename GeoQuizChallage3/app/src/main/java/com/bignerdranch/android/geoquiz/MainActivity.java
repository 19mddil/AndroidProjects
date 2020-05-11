/**Controllers tie the view and model objects together.They contain "APPLICATON LOGIC".
 * Controllers are designed to respond to various event triggered by view objects
 * And to manage the flow of data to and from model objects and the view layer
 *Controllers are typically a subclass of Activity or Fragment or Services
 **/

package com.bignerdranch.android.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private TextView mQuestionTextView;
    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX ="index";
    private static final String KEY_INDEX1 ="index1";
    private static final String KEY_INDEX2 ="index2";
    private static final String KEY_INDEX3 ="index3";
    private static final String KEY_INDEX4 ="index4";
    private TextView mScoreTextView;
    private Button mContinueButton;
    private TextView mScore;
    private Button mCheatButton;
    private boolean mIsCheater;
    private  static final int REQUEST_CODE_CHEAT = 0;
    private boolean flag ;
    private int count = 0;

    private void invisibleAll(){
        mTrueButton.setVisibility(View.INVISIBLE);
        mFalseButton.setVisibility(View.INVISIBLE);
        mNextButton.setVisibility(View.INVISIBLE);
        mQuestionTextView.setVisibility(View.INVISIBLE);
    }

    private void visibleAll(){
        mTrueButton.setVisibility(View.VISIBLE);
        mFalseButton.setVisibility(View.VISIBLE);
        mNextButton.setVisibility(View.VISIBLE);
        mQuestionTextView.setVisibility(View.VISIBLE);
    }

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.australia,true),
            new Question(R.string.africa,true),
            new Question(R.string.mideast,false),
            new Question(R.string.oceans,true),
            new Question(R.string.americas,false),
            new Question(R.string.asia,true)
    };

    private int mCurrentIndex = 0;
    private int score = 0;

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId(); // getter
        mQuestionTextView.setText(question);
    }

    private void disableButton(){
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
    }
    private void enableButtons(){
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
    }

    private boolean checkAnswer(boolean userPressedTrue){
        boolean answer = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageId = 0;
        boolean p = false;
        if(mIsCheater){
            messageId = R.string.judgement_toast;
        }
        if(answer == userPressedTrue){
            p = true;
        }
        if(messageId != 0) {
            Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
        }
        return p;
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if(resultCode != Activity.RESULT_OK) { //user didn't view the answer instead left by pressing back so not a cheater
            return;
        }
        if(requestCode == REQUEST_CODE_CHEAT) { //this is the activity that we passed from parent to child
            if(data != null) {
                mIsCheater = CheatActivity.wasAnswerShown(data);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) { // WHEN A ACITIVITY IS STOPPED OnsavedInstanceState is called.
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(KEY_INDEX1,score);
        savedInstanceState.putBoolean(KEY_INDEX2,mIsCheater);
        savedInstanceState.putBoolean(KEY_INDEX3,flag);
        savedInstanceState.putInt(KEY_INDEX4,count);
    }

    /**I do not call onCreate method.When user interects its called.I just override it**/
    @Override //So I am going to override the onCreate activity lifecylce callback.the @ thing means that compiler please check if there is a same class name upward.
    protected void onCreate(Bundle savedInstanceState) { //A activity results in this method call

        super.onCreate(savedInstanceState); // This method is called when an instance of the activity subclass is created. For example: a mainActivity instance
        Log.d(TAG,"onCreate(Bundle) called");
        /**This(below) method inflats a layout and puts it on the screen**/
        setContentView(R.layout.activity_main);//when an activity is created,it needs a ui to manage.Calling this function will give activity its UI.
        /**we can spacify which layout to infalte by passing in the layout's resources id**/



        mTrueButton = (Button)findViewById(R.id.trueButton);//wiring up the true button from xml via id
        mFalseButton = (Button)findViewById(R.id.falseButton);//wiring up the false button from xml via id
        mQuestionTextView = (TextView) findViewById(R.id.textView);
        mNextButton = (ImageButton)findViewById(R.id.nextButton);
        mScoreTextView = (TextView) findViewById(R.id.scoreTextView);
        mContinueButton = (Button) findViewById(R.id.continueButton);
        mScore = (TextView) findViewById(R.id.textView2);
        mCheatButton = (Button) findViewById(R.id.cheatButton);

        mScoreTextView.setVisibility(View.INVISIBLE);
        mContinueButton.setVisibility(View.INVISIBLE);
        mNextButton.setVisibility(View.INVISIBLE);
        mScore.setVisibility(View.INVISIBLE);

        /**Declare it after the resource finding**/
        if(savedInstanceState != null){ //Means if a object was saved also means something happened that did call the onStop()
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            score = savedInstanceState.getInt(KEY_INDEX1,0);
            mIsCheater = savedInstanceState.getBoolean(KEY_INDEX2,false);
            flag = savedInstanceState.getBoolean(KEY_INDEX3,false);
            count = savedInstanceState.getInt(KEY_INDEX4,0);
            if(flag == true){
                disableButton();
                mNextButton.setVisibility(View.VISIBLE);
            }
            if(count >= 3){
                mCheatButton.setEnabled(false);
                mCheatButton.setText("Maximum 3 cheats");
            }

        }

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();//Retrieving the correct answer for the current question
                Intent intent = CheatActivity.newIntent(MainActivity.this,answerIsTrue);//calling a cheatActivity static method passing the boolean answer with intent.
                startActivityForResult(intent,REQUEST_CODE_CHEAT);//This call is sent to the OS(part of the OS called activity manager
                /*How does the activity manager know which activity to start?That information is in the Intent parameter*/
                count++;
            }
        });


        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1)%mQuestionBank.length;
                updateQuestion();
            }
        });

        updateQuestion();

        /**Android applications are typically event driven**/
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAnswer(true)){
                    score += 1;
                }
                disableButton();
                flag = true;
                mNextButton.setVisibility(View.VISIBLE);
                mCheatButton.setEnabled(false);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {//This is a inner class object passing onto as funciton parameter
            @Override
            public void onClick(View view) {
                if(checkAnswer(false)){
                    score += 1;
                }
                flag = true;
                disableButton();
                mNextButton.setVisibility(View.VISIBLE);
                mCheatButton.setEnabled(false);
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count >= 3){
                    mCheatButton.setEnabled(false);
                    mCheatButton.setText("Maximum 3 times reached");
                }
                if(count < 3){
                    mCheatButton.setEnabled(true);
                }
                mIsCheater = false;
                boolean p = false;
                flag = false;
                mScoreTextView.setVisibility(View.INVISIBLE);
                enableButtons();
                mCurrentIndex = (mCurrentIndex + 1)%mQuestionBank.length;
                if(mCurrentIndex == 0){
                    invisibleAll();
                    mScore.setVisibility(View.VISIBLE);
                    mScoreTextView.setVisibility(View.VISIBLE);
                    mScoreTextView.setText(Integer.toString(score));
                    p = true;
                    mContinueButton.setVisibility(View.VISIBLE);
                    mContinueButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            enableButtons();
                            updateQuestion();
                            score = 0;
                            mContinueButton.setVisibility(View.INVISIBLE);
                            mScoreTextView.setVisibility(View.INVISIBLE);
                            mScore.setVisibility(View.INVISIBLE);
                            visibleAll();
                        }
                    });

                }
                if(!p) {
                    updateQuestion();
                }
                mNextButton.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();
        Log.v(TAG,"onStart() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.v(TAG,"onPause() called");
    }

    @Override
    public void onStop(){
        super.onStop();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.v(TAG,"onDestroy() called");
    }
    @Override
    public void onResume(){
        super.onResume();
    }

}
