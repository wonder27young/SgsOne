package com.wonder.sgsone.common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.wonder.sgsone.MainActivity;

/**
 * Created by u6031313 on 2/24/2016.
 */
public class Zym {
    public static Bitmap ToScaledBitmap(Bitmap paramBitmap)
    {
        if (paramBitmap != null)
        {
            Bitmap localBitmap = Bitmap.createScaledBitmap(paramBitmap, ToScaled(paramBitmap.getWidth()), ToScaled(paramBitmap.getHeight()), true);
            if ((localBitmap != null) && (paramBitmap != null) && ((localBitmap.getWidth() != paramBitmap.getWidth()) || (localBitmap.getHeight() != paramBitmap.getHeight()))) {
                paramBitmap.recycle();
            }
            return localBitmap;
        }
        return null;
    }
    public static int ToScaled(float paramFloat)
    {
        if (MainActivity.scaled == 1.0F) {
            return (int)paramFloat;
        }
        return Math.round(MainActivity.scaled * paramFloat);
    }
    public static void pt(Object object) {
        Log.i("sgs",object.toString());
    }

    public static void pt(String msg) {
        Log.i("sgs",msg);
    }

    public static void drawString(Canvas canvas, String str, int i3, int i, int i1) {

    }

    public static void displayStr(Canvas canvas, String str, int i, int i1, int i2) {

    }
}
