package aasa.aasa.hangman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.perk.perksdk.PerkManager;

public class GameoverActivity extends BaseGameActivity implements View.OnClickListener{

    ImageButton replay, home, leaderboard, volume;
    TextView scoreView, no_of_game, no_of_win, no_of_lose;
    HangmanApplicationClass hangmanApplicationClass;
    private boolean show_leaderboard = false;
    private View decorView;
    int userScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        decorView = getWindow().getDecorView();

        PerkManager.startSession(GameoverActivity.this, Constants.PERK_APP_KEY);
        PerkManager.trackEvent(GameoverActivity.this, Constants.PERK_APP_KEY, Constants.PERK_EVENT_KEY, true, null);

        hangmanApplicationClass = (HangmanApplicationClass) getApplication();

        scoreView = (TextView) findViewById(R.id.scoreView);
        replay = (ImageButton) findViewById(R.id.go_replay);
        home = (ImageButton) findViewById(R.id.go_home);
        leaderboard = (ImageButton) findViewById(R.id.go_leaderboard);
        volume = (ImageButton) findViewById(R.id.go_volume);
        no_of_game = (TextView) findViewById(R.id.no_of_game);
        no_of_win = (TextView) findViewById(R.id.no_of_win);
        no_of_lose = (TextView) findViewById(R.id.no_of_lose);

        int win = Constants.NO_OF_GAMES_WIN;
        int games = Constants.NO_OF_GAMES_PLAY;
        no_of_game.setText(games + "");
        no_of_win.setText(win + "");
        no_of_lose.setText(Constants.NO_OF_GAMES_LOSE + "");
        Constants.NO_OF_GAMES_PLAY = 0;
        Constants.NO_OF_GAMES_WIN = 0;
        Constants.NO_OF_GAMES_LOSE = 0;

        SharedPreferences sharedPreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        userScore = sharedPreferences.getInt("Score",0);
        scoreView.setText(String.valueOf(userScore));

        replay.setOnClickListener(this);
        home.setOnClickListener(this);
        leaderboard.setOnClickListener(this);
        volume.setOnClickListener(this);

        if(Constants.click % 2 == 0){
            volume.setImageResource(R.drawable.volume);
        }
        else{
            volume.setImageResource(R.drawable.mute);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_replay:
                Intent intent = new Intent(GameoverActivity.this, HangmanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.go_home:
                Intent homeIntent = new Intent(GameoverActivity.this, HomeActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.go_leaderboard:
                checkSignin();
                break;
            case R.id.go_volume:
                Constants.click++;
                if(Constants.click % 2 == 0){
                    volume.setImageResource(R.drawable.volume);
                    hangmanApplicationClass.resumePlayer();
                }
                else{
                    volume.setImageResource(R.drawable.mute);
                    hangmanApplicationClass.pausePlayer();
                }
                break;
//            case R.id.go_exit:
//                ExitDialogFragment exitDialogFragment = new ExitDialogFragment();
//                exitDialogFragment.show(GameoverActivity.this.getSupportFragmentManager(), "exit");
//                break;
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
            SharedPreferences sharedPreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
            // save score to leaderboard
            if (show_leaderboard) {
                        Games.Leaderboards.submitScore(getApiClient(), getString(R.string.leaderboard_id), sharedPreferences.getInt("Score", 0));

                // show leaderboard
                startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getString(R.string.leaderboard_id)),
                        9999);
            }

            // get score from leaderboard
            Games.Leaderboards.loadCurrentPlayerLeaderboardScore(getApiClient(), getString(R.string.leaderboard_id),
                    LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC).setResultCallback(
                    new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {
                        @Override
                        public void onResult(final Leaderboards.LoadPlayerScoreResult scoreResult) {
                            if (scoreResult != null && scoreResult.getStatus().getStatusCode() == GamesStatusCodes.STATUS_OK
                                    && scoreResult.getScore() != null) {
                                // save score locally
                            }
                        }
                    });

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
}
