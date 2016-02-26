package com.wonder.sgsone.view;

import android.content.Context;
import android.view.View;

import com.wonder.sgsone.MainActivity;
import com.wonder.sgsone.game.GameTable;

/**
 * Created by u6031313 on 2/23/2016.
 */
public class SwitchView extends View {
    public static int EnterGame = 1;
    public static final int GAMETABLE = 3;
    public static final int LOBBYVIEW = 1;
    public static final int LOGINVIEW = 0;
    public static int LeaveGame = 2;
    public static final int MATCH_RANK = 7;
    public static final int MOREGAME = 4;
    public static final int NEWRANK = 6;
    public static final int SHOPVIEW = 5;
    public static final int TABLEINSIDEVIEW = 2;
    public MainActivity mActivity;
    public FlushView mFlushView;
    public GameTable mGameTable;
    public Welcome mWelcome;
    public int currentView;

    public SwitchView(Context paramContext, MainActivity mainActivity)
    {
        super(paramContext);
        this.mActivity = mainActivity;

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
        this.mActivity.game.setVisibility(INVISIBLE);
        MainActivity.mViewFlipper.setVisibility(INVISIBLE);
        if (this.mWelcome == null) {
            this.mWelcome = new Welcome(getContext(), this.mActivity);
        }
    }

    public void showLoginScreen(int i) {

    }
}
