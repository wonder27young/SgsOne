package com.wonder.sgsone.view;

import android.content.Context;
import android.view.View;

import com.wonder.sgsone.MainActivity;

/**
 * Created by u6031313 on 2/23/2016.
 */
public class SettingView extends View {
    public MainActivity mainActivity;
    public SettingView(Context context,MainActivity mainActivity){
        super(context);
        this.mainActivity = mainActivity;
    }

}
