package aasa.aasa.hangman;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class HangmanActivity extends Activity {

    String[] words = {"enmity".toUpperCase(), "ferment".toUpperCase(), "filling".toUpperCase(), "matured".toUpperCase(),
            "despot".toUpperCase(), "demure".toUpperCase(), "lurid".toUpperCase()};
    char[] word;
    char[] line1 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
    char[] line2 = {'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R'};
    char[] line3 = {'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    String a, wholeword;
    int z = 0, k = 0;
    Random random = new Random();
    int randno, wordr1, wordr2, userscore = 50;

    TextView wordtextView, score;
    TextView[] textViews;
    ImageView imageView;
    LinearLayout linearLayout2, linearLayout3, linearLayout4, linearLayout5;
//    LinearLayout.LayoutParams params;

    int underscorecount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);

        imageView = (ImageView) findViewById(R.id.circle);
        score = (TextView) findViewById(R.id.score);

        randno = random.nextInt(words.length);
        wholeword = words[randno];
        char[] randchar = words[randno].toCharArray();
        word = new char[randchar.length];
        textViews = new TextView[randchar.length];
        for (int i = 0; i < randchar.length; i++) {
            // System.out.println(String.valueOf(randchar[i]));
            word[i] = randchar[i];
        }

//        params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        params.setMargins(2, 2, 4, 2);
        linearLayout2 = (LinearLayout) findViewById(R.id.l2);
        linearLayout3 = (LinearLayout) findViewById(R.id.l3);
        linearLayout4 = (LinearLayout) findViewById(R.id.l4);
        linearLayout5 = (LinearLayout) findViewById(R.id.l5);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        userscore = sharedPreferences.getInt("Score", 50);
        score.setText(String.valueOf(userscore));

        wordrandomtext(word);
        for (int i = 0; i < word.length; i++) {
            wordtextView = new TextView(HangmanActivity.this);
//            if (i == wordr1 || i == wordr2) {
//                wordtextView.setText(String.valueOf(word[i]));
//            } else {
//                wordtextView.setText("_");
//            }

            wordtextView.setText("_");
            wordtextView.setId(word[i]);
            wordtextView.setWidth((int) getResources().getDimension(R.dimen.game_alphbets_blank_text_width));
            wordtextView.setHeight((int) getResources().getDimension(R.dimen.game_alphbets_blank_text_width));
            wordtextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.game_blank_text_size));
            linearLayout5.addView(wordtextView);
            textViews[i] = wordtextView;
        }

        for (int i = 0; i < line1.length; i++) {

            final TextView textView = new TextView(HangmanActivity.this);
            textView.setId(i);
            textView.setText(String.valueOf(line1[i]));
            setTextViews(textView, i);
            linearLayout2.addView(textView);
        }
        for (int i = 0; i < line2.length; i++) {

            final TextView textView = new TextView(HangmanActivity.this);
            textView.setId(i + 9);
            textView.setText(String.valueOf(line2[i]));
            setTextViews(textView, i);
            linearLayout3.addView(textView);
        }
        for (int i = 0; i < line3.length; i++) {

            final TextView textView = new TextView(HangmanActivity.this);
            textView.setId(i + 18);
            textView.setText(String.valueOf(line3[i]));
            setTextViews(textView, i);
            linearLayout4.addView(textView);
        }

    }

    void wordrandomtext(char[] s) {
        Random r = new Random();
        wordr1 = r.nextInt(s.length);
        wordr2 = r.nextInt(s.length);
        while (wordr1 == wordr2) {
            wordr2 = r.nextInt(s.length);
        }
    }

    void setimg(int p, int q) {
//        System.out.println("  setimage q=0 " + z + " " + k);

        if ((p == 0) && (q == 0)) {
            k++;
            decreaseScore();
            System.out.println("set image q=1 " + k + "  " + q);
            imageView.setImageResource(R.drawable.aa);
            checkCompletion();
            z = 0;

        } else if ((p == 0) && (q == 1)) {
            k++;
            decreaseScore();
            imageView.setImageResource(R.drawable.ab);
            checkCompletion();
            z = 0;

        } else if ((p == 0) && (q == 2)) {
            k++;
            decreaseScore();
            imageView.setImageResource(R.drawable.ac);
            checkCompletion();
            z = 0;
        } else if ((p == 0) && (q == 3)) {
            k++;
            decreaseScore();
            imageView.setImageResource(R.drawable.ad);
            checkCompletion();
            z = 0;
        } else if ((p == 0) && (q == 4)) {
            k++;
            decreaseScore();
            imageView.setImageResource(R.drawable.ae);
            checkCompletion();
            z = 0;
        } else {
            Constants.NO_OF_GAMES_PLAY++;
            Constants.NO_OF_GAMES_LOSE++;
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Oops!! Correct Answer is " + wholeword);
            alertDialogBuilder.setMessage("Are you want to continue ?");

            alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Intent i = getIntent();
                    startActivity(i);
                    finish();
                }
            });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    Intent intent = new Intent(HangmanActivity.this, GameoverActivity.class);
                    startActivity(intent);
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            z = 0;
            k = 0;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


    void checkCompletion() {
//        System.out.println("Entered in check complition");
        underscorecount = 0;
        for (int i = 0; i < textViews.length; i++) {
            if (textViews[i].getText().toString().equals("_")) {
                System.out.println(textViews[i].getText().toString());
                underscorecount++;
            }
        }
        if (underscorecount == 0) {
            Constants.NO_OF_GAMES_WIN++;
            Constants.NO_OF_GAMES_PLAY++;
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Congrats!! You guess the right word.");
            alertDialogBuilder.setMessage("Are you want to continue ?");

            alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                    Intent intent = new Intent(HangmanActivity.this, HangmanActivity.class);
                    startActivity(intent);
                }
            });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    Intent intent = new Intent(HangmanActivity.this, GameoverActivity.class);
                    startActivity(intent);
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();

        }
    }

    void score(int s) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Score", s);
        editor.commit();

    }

    void increaseScore() {
        userscore = userscore + 5;
        score.setText(String.valueOf(userscore));
        score(userscore);
    }

    void decreaseScore() {
        userscore = userscore - 1;
        score.setText(String.valueOf(userscore));
        score(userscore);
    }

    void setTextViews(final TextView textView, int i) {
        textView.setGravity(Gravity.CENTER);
        textView.setWidth((int) getResources().getDimension(R.dimen.game_alphbets_text_width));
        textView.setHeight((int) getResources().getDimension(R.dimen.game_alphbets_text_height));
        textView.setBackgroundColor(getResources().getColor(R.color.cyan));
//        textView.setLayoutParams(params);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.game_text_size));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println("  On click value x n z " + z + " " + k);
                textView.setClickable(false);
                a = textView.getText().toString();
                for (int i = 0; i < word.length; i++) {
                    if (a.equals(Character.toString((char) textViews[i].getId()))) {
                        textViews[i].setText(String.valueOf(word[i]));
//                        System.out.println("matched");
                        textView.setBackgroundColor(getResources().getColor(R.color.green));
                        z = 1;
                        checkCompletion();
                    }
                }
                if (z == 1) {
                    increaseScore();
                    z = 0;
                } else {
                    textView.setBackgroundColor(getResources().getColor(R.color.red));
                    setimg(z, k);
                }
            }
        });
    }

}
