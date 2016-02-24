package com.wonder.sgsone;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.wonder.sgsone.dialog.*;
import com.wonder.sgsone.game.GLGameSurfaceView;
import com.wonder.sgsone.snd.SoundManager;
import com.wonder.sgsone.view.SwitchView;


public class MainActivity extends AppCompatActivity {

    public static MainActivity mActivity;
    public static MainActivity context;
    public static GameSetting gameSetting;
    public static int nGameHeight;
    public static int nGameWidth;
    public static float nGameDensity;
    public AbsoluteLayout baseContainer;
    public static SwitchView mSwitchView;
    public static ViewFlipper mViewFlipper;
    public GLGameSurfaceView game;
    public static float scaled;
    public static boolean isScaled;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        mActivity = this;
        context = this;
        this.game = new GLGameSurfaceView(context);
        DisplayMetrics displayMetrics =  new DisplayMetrics();
        nGameHeight = displayMetrics.heightPixels;
        nGameWidth = displayMetrics.widthPixels;
        nGameDensity = displayMetrics.density;
        isScaled = false;
        scaled = 1.0F;
        SoundManager.setContext(this);
        if (nGameHeight >= nGameWidth)
        {
            nGameWidth = displayMetrics.heightPixels;
            nGameHeight = displayMetrics.widthPixels;
        }
        gameSetting = new GameSetting(context, this);
        gameSetting.ReadGameSetting();

        this.baseContainer = new AbsoluteLayout(this);
        baseContainer.addView(this.game, new AbsoluteLayout.LayoutParams(nGameWidth, nGameHeight, 0, 0));

        //swichview
        if (mSwitchView == null) {
            mSwitchView = new SwitchView(context, this);
        }
        if (mViewFlipper==null){
            mViewFlipper = new ViewFlipper(context);
        }
        mViewFlipper.addView(mSwitchView);
        if (mViewFlipper.getParent() != null) {
            ((ViewGroup)mViewFlipper.getParent()).removeView(mViewFlipper);
        }
        this.baseContainer.addView(mViewFlipper);
        setContentView(this.baseContainer);
    }
}
