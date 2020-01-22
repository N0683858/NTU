package com.hamidraza.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 = yellow
    // 1 = red
    int activePlayer = 0;

    // 2 means its unused
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // horizontal win patterns
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // vertical win patterns
            {0, 4, 8}, {2, 4, 6}}; //2 cross (X) win patterns


    public void drop_in(View view) {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2) {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000f).setDuration(300);

            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {
                    String winner = "Red";

                    if (gameState[winningPosition[0]] == 0) {
                        winner = "Yellow";
                    }
                    //someone has won
                    TextView winningMessage = findViewById(R.id.win_message);
                    winningMessage.setText(winner + " has won!");

                    //change layout
                    LinearLayout layout = findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);

                    //play again

                }
            }
        }
    }

    public void playAgain(View view) {
        //make layout invisible again
        LinearLayout layout = findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        //reset player
        activePlayer = 0;

        //set gamestate back to normal
        for (int i = 0; i < gameState.length; i++)
        {
            gameState[i] = 2;
        }

        GridLayout gameBoard;
        gameBoard = findViewById(R.id.gameBoard);
        for(int i = 0; i < gameBoard.getChildCount(); i++)
        {
            ((ImageView) gameBoard.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
