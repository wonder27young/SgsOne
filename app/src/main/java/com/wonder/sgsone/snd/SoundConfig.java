package com.wonder.sgsone.snd;

/**
 * Created by u6031313 on 2/24/2016.
 */
public class SoundConfig {
    boolean isLoop;
    boolean isMusic;
    String path;

    public SoundConfig(String path, boolean isLoop)
    {
        this.path = path;
        this.isLoop = isLoop;
    }

    public SoundConfig(String path, boolean isLoop, boolean isMusic)
    {
        this.path = path;
        this.isLoop = isLoop;
        this.isMusic = isMusic;
    }
}
