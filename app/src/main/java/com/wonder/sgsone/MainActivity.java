package com.wonder.sgsone;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.inputmethodservice.Keyboard;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.ViewFlipper;

import com.wonder.sgsone.common.Zym;
import com.wonder.sgsone.dialog.*;
import com.wonder.sgsone.game.GLGameSurfaceView;
import com.wonder.sgsone.game.GameTable;
import com.wonder.sgsone.info.SgsModeInfo;
import com.wonder.sgsone.snd.SoundManager;
import com.wonder.sgsone.snd.Speaker;
import com.wonder.sgsone.view.NewLobbyView;
import com.wonder.sgsone.view.PreGamestartView;
import com.wonder.sgsone.view.SwitchView;


public class MainActivity extends Activity {

    public static MainActivity mActivity;
    public static MainActivity context;
    public static GameSetting gameSetting;
    public static int nGameHeight;
    public static int nGameWidth;
    public static float nGameDensity;
    public static boolean bSilentMode;
    public static boolean silentFlag;
    public static boolean onBackGround;
    public static int isLock;
    public static SgsModeInfo m_CurrentInModeInfo;
    public AbsoluteLayout baseContainer;
    public static SwitchView mSwitchView;
    public static ViewFlipper mViewFlipper;
    public GLGameSurfaceView game;
    public static float scaled;
    public static boolean isScaled;
    public GameTable mGameTable;
    static {
        isLock = 1;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //横版
        if (getRequestedOrientation() !=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        mActivity = this;
        context = this;
        this.game = new GLGameSurfaceView(context);
        if (this.mGameTable == null)
        {
            this.mGameTable = new GameTable(context, this, this.game);
            this.mGameTable.m_start = new PreGamestartView(context, this);
            this.mGameTable.newLobbyInstance = new NewLobbyView();
        }
        this.game.setRender(this.mGameTable);
        DisplayMetrics displayMetrics =  new DisplayMetrics();
        //获取当前屏幕分辨率
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        nGameHeight = displayMetrics.heightPixels;
        nGameWidth = displayMetrics.widthPixels;
        nGameDensity = displayMetrics.density;
        isScaled = true;
        scaled = 1.0F;
        SoundManager.setContext(this);
        gameSetting = new GameSetting(context, this);
        gameSetting.ReadGameSetting();
        if (nGameHeight >= nGameWidth)
        {
            nGameWidth = displayMetrics.heightPixels;
            nGameHeight = displayMetrics.widthPixels;
        }
        if (mSwitchView == null) {
            mSwitchView = new SwitchView(context, this);
        }
        if (mViewFlipper==null){
            mViewFlipper = new ViewFlipper(context);
        }

        this.baseContainer = new AbsoluteLayout(this);
        if (this.game.getParent() != null) {
            ((ViewGroup)this.game.getParent()).removeView(this.game);
        }
        baseContainer.addView(this.game, new AbsoluteLayout.LayoutParams(nGameWidth, nGameHeight, 0, 0));
        if (mSwitchView.getParent() != null) {
            ((ViewGroup)mSwitchView.getParent()).removeView(mSwitchView);
        }
        mViewFlipper.addView(mSwitchView);
        if (mViewFlipper.getParent() != null) {
            ((ViewGroup)mViewFlipper.getParent()).removeView(mViewFlipper);
        }
        this.baseContainer.addView(mViewFlipper);
        //setContentView(R.layout.activity_main);
        bSilentMode = false;
        silentFlag = false;
        onBackGround = false;
        this.game.setVisibility(View.INVISIBLE);
        setContentView(this.baseContainer);
        setAnimationAction(context);


    }
    public static void setAnimationAction(Context paramContext)
    {
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(paramContext, R.anim.push_left_in));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(paramContext, R.anim.push_left_out));
    }
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            Zym.pt(keyCode);
            if (mSwitchView.currentView != SwitchView.GAMETABLE ){
                Speaker.playMusicOutGame();
            }else {
                Speaker.playMusicInGame();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
