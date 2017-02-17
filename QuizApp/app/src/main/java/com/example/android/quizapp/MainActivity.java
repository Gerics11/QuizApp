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

public class MainActivity extends AppCompatActivity {

    private static final String QUESTIONNUMBER = "CURRENTQUESTION";
    private static final String SCORESAVED = "SCORE";
    int currentQuestion = 0;
    int score = 0;
    int rightAnswer = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Button continueTest = (Button) findViewById(R.id.start_button);
        buttonEnable(false);
        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(SCORESAVED);
            currentQuestion = savedInstanceState.getInt(QUESTIONNUMBER);
            if (currentQuestion == 0) {
            } else if (currentQuestion < 10 && currentQuestion > 0) {
                continueTest.setText("Continue Quiz");
            } else {
                showEndScreen(true);
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
        showEndScreen(false);
        rightAnswer = solution;
    }

    /**
     * Gives input to the display method, displays current score and displays summary after last questions.
     *
     * @param view displays score and show try_again_button and link_button buttons.
     */
    public void setQuestion(View view) {
        buttonEnable(true);
        TextView scoreView = (TextView) findViewById(R.id.score_textview);
        scoreView.setText(getString(R.string.score) + ": " + score + "/10");
        buttonColorReset();
        if (currentQuestion == 0) {
            display("1. What is the capital city of Hungary?", "Budapest", "Bucharest", "Zagreb", "Wien", 1);
        } else if (currentQuestion == 1) {
            display("2. What is Earth's third largest continent by land surface size?", "Europe", "North America", "Africa", "South America", 2);
        } else if (currentQuestion == 2) {
            display("3. In which country is Mount Everest?", "Mongolia", "Hongkong", "India", "Nepal", 4);
        } else if (currentQuestion == 3) {
            display("4. How many states are in the U.S.A.?", "49", "50", "51", "52", 2);
        } else if (currentQuestion == 4) {
            display("5. Which country has the largest population in Africa?", "South Africa", "Nigeria", "Ethiopia", "Egypt", 2);
        } else if (currentQuestion == 5) {
            display("6. What is by surface area, the largest island in the Mediterranean Sea?", "Cyprus", "Corsica", "Sicily", "Sardinia", 3);
        } else if (currentQuestion == 6) {
            display("7. What is the longest river in Europe?", "Ural", "Volga", "Dnieper", "Danube", 2);
        } else if (currentQuestion == 7) {
            display("8. What is the world's largest landlocked country (has no border on an ocean)?", "Mongolia", "Chad", "Afghanistan", "Kazakhstan", 4);
        } else if (currentQuestion == 8) {
            display("9. Where is Bhutan located?", "Oceania", "South America", "Asia", "Africa", 3);
        } else if (currentQuestion == 9) {
            display("10. Which country's flag contains any other color than red, blue and white?", "Norway", "Russia", "Netherlands", "Belgium", 4);
        } else if (currentQuestion >= 10) {
            TextView questionTextView = (TextView) findViewById(R.id.question);
            questionTextView.setText("Final Score: " + score + "/10" +
                    "\nQuestions provided by Triviaplaza.com" +
                    "\nVisit their website for more quizzes in more topics!");
            Button nextButton = (Button) findViewById(R.id.next_button);
            nextButton.setVisibility(View.GONE);
            scoreView.setVisibility(View.GONE);
            Button tryAgain = (Button) findViewById(R.id.try_again_button);
            Button linkButton = (Button) findViewById(R.id.link_button);
            tryAgain.setVisibility(View.VISIBLE);
            linkButton.setVisibility(View.VISIBLE);
            buttonEnable(false);
        }
    }

    /**
     * This method is called by display method and onSaveState to show the correct views
     * after starting quiz or switching orientation.
     *
     * @param whichscreen true displays the end screen, false displays the current question.
     */
    private void showEndScreen(boolean whichscreen) {
        TextView questionTextView = (TextView) findViewById(R.id.question);
        LinearLayout quizIntro = (LinearLayout) findViewById(R.id.quizIntro);
        Button newGame = (Button) findViewById(R.id.start_button);
        if (whichscreen) {
            questionTextView.setVisibility(View.VISIBLE);
            quizIntro.setVisibility(View.GONE);
            newGame.setVisibility(View.GONE);
            questionTextView.setText("Final Score: " + score + "/10" +
                    "\nQuestions provided by Triviaplaza.com" +
                    "\nVisit their website for more quizzes in more topics!");
            Button linkButton = (Button) findViewById(R.id.link_button);
            Button retryButton = (Button) findViewById(R.id.try_again_button);
            linkButton.setVisibility(View.VISIBLE);
            retryButton.setVisibility(View.VISIBLE);
        } else {
            quizIntro.setVisibility(View.GONE);
//            newGame.setVisibility(View.GONE);
            questionTextView.setVisibility(View.VISIBLE);
        }
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
        Button tryAgain = (Button) findViewById(R.id.try_again_button);
        Button linkButton = (Button) findViewById(R.id.link_button);
        tryAgain.setVisibility(View.GONE);
        linkButton.setVisibility(View.GONE);
        score = 0;
        rightAnswer = 1;
        currentQuestion = 0;
        Button newGame = (Button) findViewById(R.id.start_button);
        TextView questionTextView = (TextView) findViewById(R.id.question);
        LinearLayout quizIntro = (LinearLayout) findViewById(R.id.quizIntro);
        newGame.setText("New Game");
        newGame.setVisibility(View.VISIBLE);
        questionTextView.setVisibility(View.GONE);
        quizIntro.setVisibility(View.VISIBLE);
    }


}
