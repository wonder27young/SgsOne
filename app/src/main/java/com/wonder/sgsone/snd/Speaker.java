package com.wonder.sgsone.snd;

import android.util.Log;

import com.wonder.sgsone.MainActivity;
import com.wonder.sgsone.common.Zym;
import com.wonder.sgsone.game.GameTable;
import com.wonder.sgsone.info.SgsModeInfo;

/**
 * Created by u6031313 on 2/26/2016.
 */
public class Speaker {
    static final String PATH_MUSIC_HLG = "snd/music_hlg.ogg";
    static final String PATH_MUSIC_IN_GAME = "snd/music_in_game.ogg";
    static final String PATH_MUSIC_OUT_GAME = "snd/music_out_game.ogg";
    public static void playCardSnd(int paramInt, boolean paramBoolean)
    {
        if ((paramInt <= 0) || (paramInt > 9999))
        {
            Log.v("snd", String.format("Card id invalid: %d", new Object[]{Integer.valueOf(paramInt)}));
            return;
        }
        String str = "m";
        if (paramBoolean) {
            str = "w";
        }
        SoundManager.playSound(String.format("snd/card/card_%03d_%s.ogg", new Object[] { Integer.valueOf(paramInt), str }), false, false);
        return;
    }
    public static void playDeadWords(int paramInt)
    {
        if ((paramInt <= 0) || (paramInt > 9999))
        {
            Log.v("snd", String.format("Dead id invalid: %d", new Object[] { Integer.valueOf(paramInt) }));
            return;
        }
        SoundManager.playSound(String.format("snd/die/chr_%03d.ogg", new Object[] { Integer.valueOf(paramInt) }), false, false);
    }
    public static void playMusicHLG()
    {
        SoundManager.playSound(PATH_MUSIC_HLG, true, true);
    }
    public static void playMusicInGame()
    {
        SoundManager.playSound(PATH_MUSIC_IN_GAME, true, true);
    }
    public static void playMusicOutGame()    {
        SoundManager.playSound(PATH_MUSIC_OUT_GAME, true, true);
    }
    public static void playSkillSnd(int paramInt1, int paramInt2)
    {
        if ((paramInt1 <= 0) || (paramInt1 > 9999))
        {
            Log.v("snd", String.format("Skill id invalid: %d", new Object[] { Integer.valueOf(paramInt1) }));
            return;
        }
        SoundManager.playSound(String.format("snd/skill/skill_%03d_%d.ogg", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) }), false, false);
    }
}
