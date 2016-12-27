package com.ox.chengystudio.decor;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ox.chengystudio.R;
import com.ox.chengystudio.base.BaseViewHolder;

import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/27.
 */

public class DecorUpdateHua {

    public static void show(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        View contentView = activity.getLayoutInflater().inflate(R.layout.decor_add_hua, null);
        decorView.addView(contentView);
        fadeIn(activity, contentView);
    }

    private static void fadeIn(Activity activity, View view) {
        Animation animFadeIn = AnimationUtils.loadAnimation(activity, android.R.anim.fade_in);
        view.setAnimation(animFadeIn);
        animFadeIn.start();
    }

    class ViewHolder extends BaseViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}
