package com.govorilka.demo;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Created by jack on 09.07.2016.
 */
public class PlaySound {
    public void playSound(String soundURL, Context context) {
        MediaPlayer mMediaPlayer = new MediaPlayer();

        try {
            Uri soundURI = Uri.parse(soundURL);
            mMediaPlayer.setDataSource(context, soundURI);
            mMediaPlayer.prepare();
            mMediaPlayer.setVolume(1f, 1f);
            mMediaPlayer.setLooping(false);
            mMediaPlayer.start();
        } catch (Exception e) {
            return;
        }

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            public void onCompletion(MediaPlayer mp)
            {
                mp.release();
            }
        });
    }

    public void playDefaultFailSound(Context context){
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.fail);
        mediaPlayer.setVolume(1f, 1f);
        mediaPlayer.setLooping(false);
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            public void onCompletion(MediaPlayer mp)
            {
                mp.release();
            }
        });
    }


    public void playDefaultTadaSound(Context context){
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.tada);
        mediaPlayer.setVolume(1f, 1f);
        mediaPlayer.setLooping(false);
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            public void onCompletion(MediaPlayer mp)
            {
                mp.release();
            }
        });
    }
}
