package com.example.android.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String QUESTIONNUMBER = "CURRENTQUESTION";
    private static final String SCORESAVED = "SCORE";
    List<String> questionList = new ArrayList<String>();
    int currentQuestion = 0;
    int score = 0;
    int rightAnswer = 1;
    int numberOfQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            readQuestions();
            numberOfQuestions = questionList.size() / 6;
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Problems: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Button continueTest = (Button) findViewById(R.id.start_button);
        buttonEnable(false);
        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(SCORESAVED);
            currentQuestion = savedInstanceState.getInt(QUESTIONNUMBER);
            if (currentQuestion < numberOfQuestions && currentQuestion > 0) {   //TODO skip the countinue button thingy
                continueTest.setText(getString(R.string.continueQuiz));
            } else if ( currentQuestion >= numberOfQuestions ){
                showEndScreen();
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(QUESTIONNUMBER, currentQuestion);
        outState.putInt(SCORESAVED, score);
    }

    /**
     * Displays current question and answers, sets right answer into variable.
     *
     * @param questionDesc Question description, provided by setQuestion method
     * @param answer01     Displayed as answer A, provided by setQuestion method
     * @param answer02     Displayed as answer B, provided by setQuestion method
     * @param answer03     Displayed as answer C, provided by setQuestion method
     * @param answer04     Displayed as answer D, provided by setQuestion method
     * @param solution     Sets right answer to variable read by setQuestion method
     */
    private void display(String questionDesc, String answer01,
                         String answer02, String answer03, String answer04, int solution) {
        TextView questionTextView = (TextView) findViewById(R.id.question);
        Button answer1 = (Button) findViewById(R.id.answer1);
        Button answer2 = (Button) findViewById(R.id.answer2);
        Button answer3 = (Button) findViewById(R.id.answer3);
        Button answer4 = (Button) findViewById(R.id.answer4);
        Button nextButton = (Button) findViewById(R.id.next_button);
        TextView scoreView = (TextView) findViewById(R.id.score_textview);
        scoreView.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.VISIBLE);
        questionTextView.setText(questionDesc);
        answer1.setText(answer01);
        answer2.setText(answer02);
        answer3.setText(answer03);
        answer4.setText(answer04);
        rightAnswer = solution;
        buttonStatus(2);
    }

    /**
     * Reads the questions from questions.txt and writes the values into the arraylist
     *
     * @throws IOException
     */
    private void readQuestions() throws IOException {
        String str;
        //InputStream is = getAssets().open("en_questions.txt");
        InputStream is = getAssets().open("en_questions.txt");
        if (Locale.getDefault().getLanguage().equals("hu")){
            is = getAssets().open("hu_questions.txt");
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            if (is != null) {
                while ((str = reader.readLine()) != null) {
                    questionList.add(str);
                }
            }
        } finally {
            try {
                is.close();
            } catch (Throwable ignore) {
            }
        }
    }

    /**
     * Gives input to the display method, displays current score and displays summary after last questions.
     *
     * @param view displays score and show try_again_button and link_button buttons.
     */
    public void setQuestion(View view) {
        Button answerButton = (Button) findViewById(R.id.answer1);
        if (answerButton.isEnabled()) {
            Toast.makeText(this, getString(R.string.toastMessage),
                    Toast.LENGTH_SHORT).show();
        }
        buttonEnable(true);
        TextView scoreView = (TextView) findViewById(R.id.score_textview);
        scoreView.setText(getString(R.string.score) + ": " + score + "/" + numberOfQuestions);
        buttonColorReset();
        if (currentQuestion < numberOfQuestions) {
            display(questionList.get(currentQuestion * 6),
                    questionList.get(currentQuestion * 6 + 1),
                    questionList.get(currentQuestion * 6 + 2),
                    questionList.get(currentQuestion * 6 + 3),
                    questionList.get(currentQuestion * 6 + 4),
                    Integer.valueOf(questionList.get(currentQuestion * 6 + 5)));
        }
        if (currentQuestion >= numberOfQuestions) {
            showEndScreen();
        }
    }

    /**
     * This method is called by display method and onSaveState to show the correct views
     * after starting quiz or switching orientation.
     *
     */
    private void showEndScreen() {
        TextView questionTextView = (TextView) findViewById(R.id.question);
        questionTextView.setText(getString(R.string.endScreen1) + score + "/" + numberOfQuestions +
                "\n" + getString(R.string.endScreen2) +
                "\n" + getString(R.string.endScreen3));
        buttonStatus(3);
        buttonColorReset();
        buttonEnable(false);
    }

    /**
     * Button A function, checks for the right answer.
     *
     * @param view Button background set to RED on wrong answer, GREEN if it's right.
     */
    public void button1(View view) {
        buttonEnable(false);
        showCorrectAnswer();
        Button answer1 = (Button) findViewById(R.id.answer1);
        if (1 == rightAnswer) {
            answer1.setBackgroundColor(Color.parseColor("#01DF01"));
            score = score + 1;
        } else {
            answer1.setBackgroundColor(Color.parseColor("#DF0101"));
        }
    }

    /**
     * Button B function, checks for the right answer.
     *
     * @param view Button background set to RED on wrong answer, GREEN if it's right.
     */
    public void button2(View view) {
        buttonEnable(false);
        showCorrectAnswer();
        Button answer2 = (Button) findViewById(R.id.answer2);
        if (2 == rightAnswer) {
            answer2.setBackgroundColor(Color.parseColor("#01DF01"));
            score = score + 1;
        } else {
            answer2.setBackgroundColor(Color.parseColor("#DF0101"));
        }
    }

    /**
     * Button C function, checks for the right answer.
     *
     * @param view Button background set to RED on wrong answer, GREEN if it's right.
     */
    public void button3(View view) {
        buttonEnable(false);
        showCorrectAnswer();
        Button answer3 = (Button) findViewById(R.id.answer3);
        if (3 == rightAnswer) {
            answer3.setBackgroundColor(Color.parseColor("#01DF01"));
            score = score + 1;
        } else {
            answer3.setBackgroundColor(Color.parseColor("#DF0101"));
        }
    }

    /**
     * Button D function, checks for the right answer.
     *
     * @param view Button background set to RED on wrong answer, GREEN if it's right.
     */
    public void button4(View view) {
        buttonEnable(false);
        showCorrectAnswer();
        Button answer4 = (Button) findViewById(R.id.answer4);
        if (4 == rightAnswer) {
            answer4.setBackgroundColor(Color.parseColor("#01DF01"));
            score = score + 1;
        } else {
            answer4.setBackgroundColor(Color.parseColor("#DF0101"));
        }
    }

    /**
     * Sets the background of the right answer to green, showing the right answer if the user
     * guessed wrong.
     */
    public void showCorrectAnswer() {
        currentQuestion = currentQuestion + 1;
        Button answer1 = (Button) findViewById(R.id.answer1);
        Button answer2 = (Button) findViewById(R.id.answer2);
        Button answer3 = (Button) findViewById(R.id.answer3);
        Button answer4 = (Button) findViewById(R.id.answer4);
        if (rightAnswer == 1) {
            answer1.setBackgroundColor(Color.parseColor("#01DF01"));
        }
        if (rightAnswer == 2) {
            answer2.setBackgroundColor(Color.parseColor("#01DF01"));
        }
        if (rightAnswer == 3) {
            answer3.setBackgroundColor(Color.parseColor("#01DF01"));
        }
        if (rightAnswer == 4) {
            answer4.setBackgroundColor(Color.parseColor("#01DF01"));
        }
    }

    /**
     * Sets answer buttons background to default, used by setQuestion method.
     */
    private void buttonColorReset() {
        Button answer1 = (Button) findViewById(R.id.answer1);
        Button answer2 = (Button) findViewById(R.id.answer2);
        Button answer3 = (Button) findViewById(R.id.answer3);
        Button answer4 = (Button) findViewById(R.id.answer4);
        answer1.setBackgroundResource(android.R.drawable.btn_default);
        answer2.setBackgroundResource(android.R.drawable.btn_default);
        answer3.setBackgroundResource(android.R.drawable.btn_default);
        answer4.setBackgroundResource(android.R.drawable.btn_default);
    }

    /**
     * Handles enabling and disabling buttons.
     * Used by other methods to disable answer buttons after the user made the input.
     *
     * @param buttonState true enables, false value disables the answer buttons.
     */
    private void buttonEnable(boolean buttonState) {
        Button answer1 = (Button) findViewById(R.id.answer1);
        Button answer2 = (Button) findViewById(R.id.answer2);
        Button answer3 = (Button) findViewById(R.id.answer3);
        Button answer4 = (Button) findViewById(R.id.answer4);
        if (buttonState) {
            answer1.setEnabled(true);
            answer2.setEnabled(true);
            answer3.setEnabled(true);
            answer4.setEnabled(true);
            answer1.setTextColor(Color.parseColor("#000000"));
            answer2.setTextColor(Color.parseColor("#000000"));
            answer3.setTextColor(Color.parseColor("#000000"));
            answer4.setTextColor(Color.parseColor("#000000"));

        } else {
            answer1.setEnabled(false);
            answer2.setEnabled(false);
            answer3.setEnabled(false);
            answer4.setEnabled(false);
        }
    }

    /**
     * Link_button's method to open triviaplaza's homepage in browser.
     *
     * @param view called by R.id.link_button.
     */
    public void openLink(View view) {
        String url = "http://www.triviaplaza.com";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    /**
     * Resets score and current question values and shows starting screen.
     *
     * @param view called by R.id.try_again_button.
     */
    public void tryAgain(View view) {
        score = 0;
        rightAnswer = 1;
        currentQuestion = 0;
        Button newGame = (Button) findViewById(R.id.start_button);
        newGame.setText(getString(R.string.start));
        buttonStatus(1);
    }

    private void buttonStatus(int state) {
        Button tryAgain = (Button) findViewById(R.id.try_again_button);
        Button linkButton = (Button) findViewById(R.id.link_button);
        TextView questionTextView = (TextView) findViewById(R.id.question);
        LinearLayout quizIntro = (LinearLayout) findViewById(R.id.quizIntro);
        Button nextButton = (Button) findViewById(R.id.next_button);
        TextView scoreView = (TextView) findViewById(R.id.score_textview);
        if (state == 1) {
            tryAgain.setVisibility(View.GONE);
            linkButton.setVisibility(View.GONE);
            questionTextView.setVisibility(View.GONE);
            quizIntro.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.GONE);
            scoreView.setVisibility(View.GONE);
        } else if (state == 2) {
            tryAgain.setVisibility(View.GONE);
            linkButton.setVisibility(View.GONE);
            questionTextView.setVisibility(View.VISIBLE);
            quizIntro.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);
            scoreView.setVisibility(View.VISIBLE);
        } else if (state == 3) {
            tryAgain.setVisibility(View.VISIBLE);
            linkButton.setVisibility(View.VISIBLE);
            questionTextView.setVisibility(View.VISIBLE);
            quizIntro.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
            scoreView.setVisibility(View.GONE);
        }

    }
}























