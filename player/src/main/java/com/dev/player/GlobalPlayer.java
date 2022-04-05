package com.dev.player;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * @author long.guo
 * @since 1/24/21
 */
public class GlobalPlayer {
    private MediaPlayer mediaPlayer;
    private static GlobalPlayer globalPlayer;
    private String path;

    private GlobalPlayer() {
    }

    public boolean isPlaying() {
        if (mediaPlayer == null) return false;
        return mediaPlayer.isPlaying();
    }

    public static GlobalPlayer getInstance() {
        if (globalPlayer == null) {
            globalPlayer = new GlobalPlayer();
        }
        return globalPlayer;
    }

    public void start(String path) {
        if (path.equals(this.path)) {
            mediaPlayer.start();
            return;
        }
        try {
            if (mediaPlayer != null) {
                destroy();
            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
            this.path = path;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void start() {
        mediaPlayer.start();
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        this.path = null;
    }

    public void destroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = null;
        this.path = null;
    }

}
