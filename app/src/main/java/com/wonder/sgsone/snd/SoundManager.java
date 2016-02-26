package com.wonder.sgsone.snd;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

import com.wonder.sgsone.MainActivity;
import com.wonder.sgsone.common.Zym;

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
    public static void playSound(String paramString, boolean paramBoolean1, boolean paramBoolean2)
    {
        try
        {
            if ((MainActivity.bSilentMode) || (MainActivity.silentFlag) || (MainActivity.onBackGround))
            {
                releasePlayerList();
                return;
            }
            if ((MainActivity.isLock != 0) && (getContext() != null) && (paramString != null) && (!"".equals(paramString)))
            {
                //Zym.pt("ready to play");
                if (sNumPlayers > 4)
                {
                    if (sQueueOfSndsAwaits == null) {
                        sQueueOfSndsAwaits = new Vector();
                    }
                    sQueueOfSndsAwaits.add(new SoundConfig(paramString, paramBoolean1, paramBoolean2));
                    Zym.pt("snd"+"Enqueue, snd=" + paramString + ", num=" + sNumPlayers);
                    return;
                }
                AssetFileDescriptor assetFileDescriptor = getContext().getAssets().openFd(paramString);
                final MediaPlayer localMediaPlayer = new MediaPlayer();
                localMediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                sNumPlayers = (byte)(sNumPlayers + 1);
                if (sPlayerList == null) {
                    sPlayerList = new Vector();
                }
                sPlayerList.add(localMediaPlayer);
                float f = MainActivity.gameSetting.getSoundValue() / 100.0F;
                if (paramBoolean2) {
                    f = MainActivity.gameSetting.getMusicValue() / 100.0F;
                }
                localMediaPlayer.setVolume(f, f);
                localMediaPlayer.setLooping(paramBoolean1);
                localMediaPlayer.prepare();
                localMediaPlayer.start();
                if (!paramBoolean1) {
                    localMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                    {
                        public void onCompletion(MediaPlayer paramAnonymousMediaPlayer)
                        {
                        SoundManager.sNumPlayers = (byte)(SoundManager.sNumPlayers - 1);
                        if (SoundManager.sPlayerList != null) {
                            for (int i=0;i<SoundManager.sPlayerList.size();i++)
                            {
                                if (paramAnonymousMediaPlayer != null) {
                                    paramAnonymousMediaPlayer.release();
                                }
                                if ((SoundManager.sQueueOfSndsAwaits != null) && (SoundManager.sQueueOfSndsAwaits.size() > 0))
                                {
                                    SoundConfig soundConfig = (SoundConfig)SoundManager.sQueueOfSndsAwaits.get(0);
                                    SoundManager.sQueueOfSndsAwaits.remove(0);
                                    SoundManager.playSound(soundConfig.path, soundConfig.isLoop, soundConfig.isMusic);
                                    Log.v("snd", "Pop queue, snd=" + soundConfig.path + ", num=" + SoundManager.sNumPlayers);
                                }
                                MediaPlayer mediaPlayer = SoundManager.sPlayerList.get(i);
                                if ((mediaPlayer == null) || (mediaPlayer.hashCode() != paramAnonymousMediaPlayer.hashCode())) {
                                    break;
                                }
                                SoundManager.sPlayerList.remove(mediaPlayer);
                            }
                        }

                        }
                    });
                }
            }
            return;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
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
                    float f = MainActivity.gameSetting.getSoundValue() / 100.0F;
                    if (localMediaPlayer.isLooping()) {
                        f = MainActivity.gameSetting.getMusicValue() / 100.0F;
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
    public static void releasePlayerList()
    {
        sNumPlayers = 0;
        if (sPlayerList == null) {
            return;
        }
        for (MediaPlayer player :
                sPlayerList) {
            player.release();
        }
        sPlayerList.clear();
        return;
    }


}
