package aasa.aasa.hangman;

import android.app.Activity;
import android.app.Application;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by arpit on 13/12/15.
 */
public class HangmanApplicationClass extends Application {

    public static final String TAG = HangmanApplicationClass.class.getSimpleName();
    public static int activeActivities = 0;
    public MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();

        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.i(TAG, activity.getLocalClassName() + " onActivityCreated");
                ++activeActivities;
                if (activeActivities == 1) {
                    mediaPlayer = MediaPlayer.create(activity.getApplicationContext(), R.raw.bg_sound);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.i(TAG, activity.getLocalClassName() + " onActivityStarted");

            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.i(TAG, activity.getLocalClassName() + " onActivityResumed");
//                if(constants.isSoundPlaying){
                if(Constants.click % 2 == 0){
                    resumePlayer();
                }

            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.i(TAG, activity.getLocalClassName() + " onActivityPaused");
                pausePlayer();
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.i(TAG, activity.getLocalClassName() + " onActivityStopped");

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.i(TAG, activity.getLocalClassName() + " onActivitySaveInstanceState");

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                --activeActivities;
                Log.i(TAG, activity.getLocalClassName() + " onActivityDestroyed, Running Activity : " + activeActivities);
                if (activeActivities == 0) {
                    releasePlayer();
                    Log.i(TAG, "media player release");
                }
            }
        });
    }

    public void pausePlayer() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        }
    }

    public void resumePlayer() {
        if (mediaPlayer != null) {
            if (!(mediaPlayer.isPlaying())) {
                mediaPlayer.start();
            }
        }
    }

    public void releasePlayer(){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

}