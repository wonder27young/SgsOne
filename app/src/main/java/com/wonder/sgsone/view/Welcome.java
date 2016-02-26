package com.wonder.sgsone.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.wonder.sgsone.MainActivity;
import com.wonder.sgsone.common.Zym;

import java.io.IOException;

/**
 * Created by u6031313 on 2/23/2016.
 */
public class Welcome extends BaseView {
    Bitmap[] Logo;
    Bitmap LogoBg;
    int mCount = 1;
    public MainActivity mainActivity;
    boolean switchFlag = false;
    int nCurrentPos;
    public Welcome(Context context, MainActivity mActivity) {
        super(context);
        addToLayout(this);
        this.mainActivity = mActivity;
        init();
    }
    public void init(){
        this.switchFlag = false;
        this.Logo = new Bitmap[this.mCount];
        MainActivity.scaled = 0.5f;
        String str1 = "/logo";
        try {
            Bitmap logos = createImage("/logo/logo_bg.png");
            this.LogoBg = Bitmap.createScaledBitmap(logos, mainActivity.nGameWidth, mainActivity.nGameHeight, false);
            int i=0;
            this.Logo[0] = createImage(str1 + "/logo_" + (i + 1) + ".png", MainActivity.isScaled);

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }


    public void paint(Canvas canvas)
    {
        if (this.nCurrentPos == 0)
        {
            drawImage(canvas, this.LogoBg, 0, 0);
            if ((this.Logo != null) && (this.Logo[0] != null)) {
                drawImage(canvas, this.Logo[0], (MainActivity.nGameWidth - this.Logo[0].getWidth()) / 2, (MainActivity.nGameHeight - this.Logo[0].getHeight()) / 2);
            }
        }
        if ((!this.switchFlag) && (this.nCurrentPos >= this.mCount))
        {
            MainActivity.mSwitchView.showLoginScreen(0);
            this.mainActivity.game.setVisibility(VISIBLE);
            MainActivity.mViewFlipper.setVisibility(INVISIBLE);
        }

    }

}
