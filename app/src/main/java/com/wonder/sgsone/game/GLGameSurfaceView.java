package com.wonder.sgsone.game;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import com.wonder.sgsone.common.Zym;

/**
 * Created by u6031313 on 2/23/2016.
 */
public class GLGameSurfaceView extends SurfaceView implements Callback{
    GameTable render;
    public GLGameSurfaceView(Context context) {
        super(context);
    }
    public void onPause()
    {
        //super.onPause();
        Zym.pt("glgamesurfaceview pause...");
    }

    public void onResume()
    {
        //super.onResume();
        Zym.pt("glgamesurfaceview resume...");
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
        //this.render.onTouchEvent(paramMotionEvent);
        return true;
    }
    public void setRender(GameTable paramGameTable)
    {
        if (this.render != paramGameTable)
        {
            this.render = paramGameTable;
            //setRenderer(paramGameTable);
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
