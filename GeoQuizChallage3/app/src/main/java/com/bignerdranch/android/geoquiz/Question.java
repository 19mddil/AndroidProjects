/**Business logic.Sole purpose is to manage the data.**/
package com.bignerdranch.android.geoquiz;
public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(int textResId,boolean answerTrue){
        mAnswerTrue = answerTrue;
        mTextResId = textResId;//Resource ID is always a int.
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
