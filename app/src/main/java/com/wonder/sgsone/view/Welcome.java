package com.wonder.sgsone.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.wonder.sgsone.MainActivity;

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
    }
    protected int doTimerTask()
    {
        for (this.nCurrentPos += 1;; this.nCurrentPos = 0)
        {
            return super.doTimerTask();
        }
    }
    public void init() throws IOException {
        this.switchFlag = false;
        this.Logo = new Bitmap[this.mCount];
        String str1 = "/logo";
        Bitmap logos = createImage("/logo/logo_bg.png");
        this.LogoBg = Bitmap.createScaledBitmap(logos,mainActivity.nGameWidth,mainActivity.nGameHeight,false);
        int i=0;
        this.Logo[0] = createImage(str1 + "/logo_" + (i + 1) + ".png", MainActivity.isScaled);
    }
    public void paint(Canvas paramCanvas)
    {
        if (this.nCurrentPos == 0)
        {
            drawImage(paramCanvas, this.LogoBg, 0, 0);
            if ((this.Logo != null) && (this.Logo[0] != null)) {
                drawImage(paramCanvas, this.Logo[0], (MainActivity.nGameWidth - this.Logo[0].getWidth()) / 2, (MainActivity.nGameHeight - this.Logo[0].getHeight()) / 2);
            }
            if ((!this.switchFlag) && (this.nCurrentPos >= this.mCount))
            {
                if (!Def.bNeedReloadTextures) {
                    break label241;
                }
                com.bf.sgs.GameTable.s_status = 8;
                MainActivity.mSwitchView.showLoginScreen(0);
                this.mMa.game.setVisibility(0);
                MainActivity.mViewFlipper.setVisibility(4);
            }
        }
        for (;;)
        {
            StartTimerTask(false, 0, 0);
            this.switchFlag = true;
            return;
            zym.fillRect(paramCanvas, -1, 0, 0, MainActivity.nGameWidth, MainActivity.nGameHeight, 255);
            if ((this.nCurrentPos >= this.mCount) || (this.Logo == null) || (this.Logo[this.nCurrentPos] == null)) {
                break;
            }
            drawImage(paramCanvas, this.Logo[this.nCurrentPos], (MainActivity.nGameWidth - this.Logo[this.nCurrentPos].getWidth()) / 2, (MainActivity.nGameHeight - this.Logo[this.nCurrentPos].getHeight()) / 2);
            break;
            label241:
            this.mainActivity.game.setVisibility(0);
            MainActivity.mViewFlipper.setVisibility(4);
        }
    }

}
