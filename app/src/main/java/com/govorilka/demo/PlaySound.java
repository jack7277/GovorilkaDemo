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
    }
}
