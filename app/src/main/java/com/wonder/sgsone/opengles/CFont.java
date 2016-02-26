package com.wonder.sgsone.opengles;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.wonder.sgsone.common.Zym;

import javax.microedition.khronos.opengles.GL10;

public class CFont {
    Bitmap bmp;
    int bmpH;
    int bmpW;
    CTexture ct;
    GL10 f12906g;
    Paint paint;

    public CFont(GL10 gl10) {
        this.paint = new Paint(1);
        this.f12906g = gl10;
        this.ct = new CTexture();
    }

    public CFont(GL10 gl10, int i, int i2) {
        this.paint = new Paint(1);
        this.f12906g = gl10;
        this.ct = new CTexture();
        this.bmpW = i;
        this.bmpH = i2;
        this.bmp = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        this.ct.initTextureWithFont(this.bmp, this.f12906g);
    }

    public void addText(int i, int i2, String str, int i3) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ALPHA_8);
        Zym.drawString(new Canvas(createBitmap), str, i3, 0, 0);
        this.ct.initTextureWithData(createBitmap, this.f12906g);
    }

    public void draw(int i, int i2) {
        this.ct.draw(i, i2, this.ct.getWidth(), this.ct.getHeight(), 0.0f, 0, 0, this.ct.getWidth(), this.ct.getHeight(), 1.0f, 0);
    }

    public void drawString(String str, int i, int i2, int i3) {
        int i4 = 1;
        this.paint.setColor(i);
        int measureText = (int) this.paint.measureText(str);
        int descent = (int) (this.paint.descent() + this.paint.ascent());
        if (this.bmpW < measureText) {
            this.bmpW = measureText;
            measureText = 1;
        } else {
            measureText = 0;
        }
        if (this.bmpH < descent) {
            this.bmpH = descent;
            measureText = 1;
        }
        descent = 1;
        while (descent < this.bmpW) {
            descent *= 2;
        }
        this.bmpW = descent;
        while (i4 < this.bmpH) {
            i4 *= 2;
        }
        this.bmpH = i4;
        if (measureText != 0) {
            Bitmap createBitmap = Bitmap.createBitmap(this.bmpW, this.bmpH, Config.ARGB_8888);
            new Canvas(createBitmap).drawText(str, 0.0f, 10.0f, this.paint);
            this.bmp = createBitmap;
            this.ct.initTextureWithFont(this.bmp, this.f12906g);
        } else {
            this.bmp.eraseColor(0);
            new Canvas(this.bmp).drawText(str, 0.0f, 10.0f, this.paint);
            this.ct.subTextureText(this.bmp, this.f12906g);
        }
        draw(i2, i3);
    }

    public void drawString(String str, int i, int i2, int i3, int i4) {
        this.paint.setTextSize((float) i4);
        drawString(str, i, i2, i3);
    }

    public void subText(String str, int i) {
        this.bmp.eraseColor(0);
        Canvas canvas = new Canvas(this.bmp);
        if (i < this.bmpW) {
            Zym.displayStr(canvas, str, 0, 0, i);
        } else {
            Zym.displayStr(canvas, str, 0, 0, this.bmpW);
        }
        this.ct.subTextureText(this.bmp, this.f12906g);
    }
}
