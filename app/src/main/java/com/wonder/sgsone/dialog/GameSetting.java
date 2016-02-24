package com.wonder.sgsone.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.wonder.sgsone.MainActivity;
import com.wonder.sgsone.common.ConfigAbout;
import com.wonder.sgsone.view.SettingView;

/**
 * Created by u6031313 on 2/23/2016.
 */
public class GameSetting extends Dialog {
    public int aiSpeed;
    public int musicValue;
    public int soundValue;
    int size;
    MainActivity ma;
    SettingView sv;
    public GameSetting(Context paramContext, MainActivity mainActivity)
    {
        super(paramContext);
        this.ma = mainActivity;
        requestWindowFeature(1);
        setCancelable(false);
    }
    public void ReadGameSetting(){
        if (ConfigAbout.Readconfig("Music") != null) {
            this.musicValue = Integer.valueOf(ConfigAbout.Readconfig("Music")).intValue();
        }
        else {
            this.musicValue = 30;
        }
        if (ConfigAbout.Readconfig("Sound") != null) {
            this.soundValue = Integer.valueOf(ConfigAbout.Readconfig("Sound")).intValue();
        }
        else{
            this.soundValue = 70;
        }



    }
    public void showDialog()
    {
        setCanceledOnTouchOutside(true);
        this.size = ((int)(MainActivity.nGameWidth / 320.0F * 8.0F));
        Window localWindow = getWindow();
        //this.sv = new SettingView(getContext(), zym.xyToScaled(306.0F), zym.xyToScaled(290.0F));
        //localWindow.setLayout(zym.xyToScaled(306.0F), zym.xyToScaled(290.0F));
        //setContentView(this.sv.getInnerView());
        localWindow.setBackgroundDrawable(new ColorDrawable(0));
        try
        {
            if (!isShowing()) {
                show();
            }
            return;
        }
        catch (Exception localException)
        {
            closeDialog();
        }
    }
    public void closeDialog()
    {
        try
        {
            if (isShowing()) {
                dismiss();
            }
            this.sv = null;
            MainActivity.mSwitchView.GC();
            return;
        }
        catch (Exception ex)
        {

        }
    }
    public int getMusicValue()
    {
        return this.musicValue;
    }

    public int getSoundValue()
    {
        return this.soundValue;
    }
}
