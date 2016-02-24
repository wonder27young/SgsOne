package com.wonder.sgsone.snd;

import android.content.Context;
import android.media.MediaPlayer;

import com.wonder.sgsone.MainActivity;

import java.util.Iterator;
import java.util.Vector;

/**
 * Created by u6031313 on 2/24/2016.
 */
public class SoundManager {
    static final byte NUM_MAX_CONCURRENT_PLAYERS = 4;
    private static Context mCtx;
    static byte sNumPlayers;
    static Vector<MediaPlayer> sPlayerList;
    static Vector<SoundConfig> sQueueOfSndsAwaits;

    public static void setContext(MainActivity context) {
        mCtx = context;
    }
    public static Context getContext()
    {
        return mCtx;
    }
    public static void updateVolume()
    {
        try
        {
            if (sPlayerList == null) {
                return;
            }
            Iterator localIterator = sPlayerList.iterator();
            while (localIterator.hasNext())
            {
                MediaPlayer localMediaPlayer = (MediaPlayer)localIterator.next();
                if (localMediaPlayer != null)
                {
                    float f = MainActivity.GameSetting.getSoundValue() / 100.0F;
                    if (localMediaPlayer.isLooping()) {
                        f = MainActivity.GameSetting.getMusicValue() / 100.0F;
                    }
                    localMediaPlayer.setVolume(f, f);
                }
            }
            return;
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
    }

}
