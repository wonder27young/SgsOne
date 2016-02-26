package com.wonder.sgsone.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;

import com.wonder.sgsone.R;
import com.wonder.sgsone.common.Zym;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by u6031313 on 2/23/2016.
 */
public abstract class BaseView extends View {
    View mView = inflate(getContext(), R.layout.common,null);
    AbsoluteLayout mAbsoluteLayout;
    Timer timer;
    protected Paint paint = new Paint();
    public BaseView(Context context) {
        super(context);
        this.mView.setBackgroundColor(0);
        this.mAbsoluteLayout = ((AbsoluteLayout)this.mView.findViewById(R.id.CommonAbsoluteLayout));
    }
    protected void addToLayout(View paramView)
    {
        if (paramView == null) {
            return;
        }
        try
        {
            if (paramView.getParent() == null)
            {
                this.mAbsoluteLayout.addView(paramView);
                return;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            removeFromLayout(paramView);
            addToLayout(paramView);
            return;
        }
        ((ViewGroup)paramView.getParent()).removeView(paramView);
        this.mAbsoluteLayout.addView(paramView);
    }

    protected void removeFromLayout(View view)
    {
        if (view == null) {
            return;
        }
        try
        {
            this.mAbsoluteLayout.removeView(view);
            return;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void cleanBitmap(Bitmap bitmap)
    {
        if ((bitmap != null) && (!bitmap.isRecycled())) {
            bitmap.recycle();
        }
    }
    public Bitmap createImage(String paramString, boolean paramBoolean)
            throws IOException
    {
        return createImage(getContext(), paramString, paramBoolean);
    }
    public static Bitmap createImage(Context paramContext, String paramString, boolean paramBoolean)
            throws IOException
    {
        if (paramBoolean) {
            return Zym.ToScaledBitmap(createImage(paramContext, paramString));
        }
        return createImage(paramContext, paramString);
    }

    public Bitmap createImage(String paramString)
            throws IOException
    {
        return createImage(getContext(), paramString);
    }
    public static Bitmap createImage(Context paramContext, String paramString)
            throws IOException
    {
        if (paramContext == null) {
            return null;
        }
        String str = paramString;
        if (paramString.startsWith("/")) {
            str = paramString.substring(1, paramString.length());
        }
        return createImage(paramContext.getAssets().open(str));
    }
    public static Bitmap createImage(InputStream paramInputStream)
            throws IOException
    {
        return BitmapFactory.decodeStream(paramInputStream);
    }
    public void drawImage(Canvas paramCanvas, Bitmap paramBitmap, int paramInt1, int paramInt2)
    {
        if ((paramBitmap != null) && (!paramBitmap.isRecycled()) && (paramCanvas != null))
        {
            this.paint.setAlpha(255);
            paramCanvas.drawBitmap(paramBitmap, paramInt1, paramInt2, this.paint);
        }
    }

    protected int doTimerTask()
    {
        return -1;
    }
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        paint(canvas);
    }

    protected abstract void paint(Canvas canvas);

    protected void StartTimerTask(boolean paramBoolean, long delay, long period)
    {
        if (paramBoolean)
        {
            if (this.timer != null) {
                this.timer.cancel();
            }
        }
        if (this.timer == null)
        {
            TimerTask task = new TimerTask()
            {
                public void run()
                {
                    doTimerTask();
                }
            };
            this.timer = new Timer(true);
            this.timer.schedule(task, delay, period);
        }
        this.timer.cancel();
        return;
    }

}
