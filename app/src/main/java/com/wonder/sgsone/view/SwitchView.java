package com.wonder.sgsone.view;

import android.content.Context;
import android.view.View;

import com.wonder.sgsone.MainActivity;
import com.wonder.sgsone.game.GameTable;

/**
 * Created by u6031313 on 2/23/2016.
 */
public class SwitchView extends View {

    public MainActivity mActivity;
    public FlushView mFlushView;
    public GameTable mGameTable;
    public Welcome mWelcome;

    public SwitchView(Context context) {
        super(context);
    }
    public SwitchView(Context paramContext, MainActivity mainActivity)
    {
        super(paramContext);
        this.mActivity = mainActivity;
        //this.mDealLogicFrame = new DealLogicFrame(paramContext, mainActivity);
        //this.msgframe = new MsgFrame(paramContext, mainActivity);
        //zym.pt("做到这里");
    }
    public void GC()
    {
        System.gc();
        Runtime.getRuntime().gc();
    }
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        showWelcomeScreen();
    }

    private void showWelcomeScreen() {
        //this.mActivity.game.setVisibility(0);
        //MainActivity.mViewFlipper.setVisibility(4);
        if (this.mWelcome == null) {
            this.mWelcome = new Welcome(getContext(), this.mActivity);
        }
    }
}
