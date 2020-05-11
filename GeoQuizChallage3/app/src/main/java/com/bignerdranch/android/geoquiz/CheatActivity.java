package com.bignerdranch.android.geoquiz;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**Buggy code**/
/**Fixeded all the bugs**/


public class CheatActivity extends AppCompatActivity {
    private boolean mAnswerIsTrue;
    private static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN ="com.bignerdranch.android.geoquiz.answer_shown";
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private static final String KEY_INDEX ="index";
    private TextView mApiLevelShow;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState1) { // WHEN A ACITIVITY IS STOPPED OnsavedInstanceState is called.
        super.onSaveInstanceState(savedInstanceState1);
        savedInstanceState1.putBoolean(KEY_INDEX,mAnswerIsTrue);
    }

    public static Intent newIntent(Context packageContext,boolean answerIsTrue){//quizactivity.this,questionAnswer
        /**
         * in intent first argument tells activity manager which package the activity class(the second argument that the activity manager should start) can be found in
         */
        Intent intent = new Intent(packageContext,CheatActivity.class);//like from and to and Intent class provides different constructors depending on what you are using the intent to do.
        intent.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);//Also passing the intent some extras
        return intent;//returning intent to from classs so the same class is called again
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,data);//is used by child method to send data back to the parent.
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState1) {
        super.onCreate(savedInstanceState1);
        setContentView(R.layout.activity_cheat);

        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
        mShowAnswerButton = (Button) findViewById(R.id.showAnswerButton);
        mApiLevelShow = (TextView) findViewById(R.id.apiLevelShow);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);//getIntent gives what intent has been passed to it by its parents

        mApiLevelShow.setText("API Level "+Integer.toString(Integer.valueOf(android.os.Build.VERSION.SDK)));

        if(savedInstanceState1 != null){
            mAnswerIsTrue = savedInstanceState1.getBoolean(KEY_INDEX);
            if(mAnswerIsTrue == true){
                mAnswerTextView.setText(R.string.true_button);
            }else{
                mAnswerTextView.setText(R.string.false_button);
            }
            setAnswerShownResult(true);//newly added
        }

        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAnswerIsTrue == true){
                    mAnswerTextView.setText(R.string.true_button);
                }else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });

    }
}
