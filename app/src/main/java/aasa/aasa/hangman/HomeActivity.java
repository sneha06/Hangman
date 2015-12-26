package aasa.aasa.hangman;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;

public class HomeActivity extends BaseGameActivity implements View.OnClickListener,View.OnTouchListener {

    ImageButton instruction, leaderboard, sound;
    ImageView play;
    private boolean show_leaderboard = false;
    View decorView;
    HangmanApplicationClass hangmanApplicationClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        checkSignin();

        decorView = getWindow().getDecorView();

        hangmanApplicationClass = (HangmanApplicationClass) getApplication();

        play = (ImageView) findViewById(R.id.play);
        instruction = (ImageButton) findViewById(R.id.instruction);
        leaderboard = (ImageButton) findViewById(R.id.leaderboard);
        sound = (ImageButton) findViewById(R.id.sound);

        play.setOnTouchListener(this);
        instruction.setOnClickListener(this);
        leaderboard.setOnClickListener(this);
        sound.setOnClickListener(this);

        if(Constants.click % 2 == 0){
            sound.setImageResource(R.drawable.volume);
        }
        else{
            sound.setImageResource(R.drawable.mute);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.play:
//                Intent playintent = new Intent(HomeActivity.this, HangmanActivity.class);
//                startActivity(playintent);
//                break;
            case R.id.instruction:
                Intent intent = new Intent(HomeActivity.this, InstructionActivity.class);
                startActivity(intent);
                break;
            case R.id.leaderboard:
                checkSignin();
                break;
            case R.id.sound:
                Constants.click++;
                if(Constants.click % 2 == 0){
                    sound.setImageResource(R.drawable.volume);
                    hangmanApplicationClass.resumePlayer();
                }
                else{
                    sound.setImageResource(R.drawable.mute);
                    hangmanApplicationClass.pausePlayer();
                }
                break;
            default:
                break;
        }
    }

    public void checkSignin() {
        if (isSignedIn()) {
            if (getApiClient().isConnected()) {
                show_leaderboard = true;
                onSignInSucceeded();
            }
        } else {
            beginUserInitiatedSignIn();
        }
    }

    @Override
    public void onSignInSucceeded() {
        try {
            if (show_leaderboard) {
                // show leaderboard
                startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getString(R.string.leaderboard_id)),
                        9999);
            }
            show_leaderboard = false;
        } catch (Exception ex) {
            reconnectClient();
            beginUserInitiatedSignIn();
        }
    }

    @Override
    public void onSignInFailed() {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.play) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                play.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            } else if(event.getAction() == MotionEvent.ACTION_UP){
                play.setScaleType(ImageView.ScaleType.FIT_XY);
                Intent playintent = new Intent(HomeActivity.this, HangmanActivity.class);
                startActivity(playintent);
                }

            }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onBackPressed() {
        ExitDialogFragment exitDialogFragment = new ExitDialogFragment();
        exitDialogFragment.show(HomeActivity.this.getSupportFragmentManager(), "exit");
    }
}
