package com.ox.chengystudio.custom;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;

/**
 * Created by ox on 16/12/29.
 */
public class AutoRefreshTextView extends TextView {
    private boolean isPlaying = false;
    private long playDuration = DateUtils.SECOND_IN_MILLIS;
    private TextPicker textPicker;
    private boolean hasDismiss = false;

    public AutoRefreshTextView(Context context) {
        super(context);
        init(context);
    }

    public AutoRefreshTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutoRefreshTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoRefreshTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {

    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        hasDismiss = visibility == INVISIBLE || visibility == GONE;
    }

    public void startToRefresh(ExecutorService es, TextPicker textPicker) {
        if (textPicker == null) {
            return;
        }
        if (!isPlaying) {
            isPlaying = true;
            this.textPicker = textPicker;
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    while (isPlaying && !hasDismiss) {
                        post(new Runnable() {
                            @Override
                            public void run() {
                                setText(AutoRefreshTextView.this.textPicker.getText(AutoRefreshTextView.this));
                            }
                        });
                        SystemClock.sleep(playDuration);
                    }
                }
            };
            es.execute(run);
        } else {
            this.textPicker = textPicker;
        }

    }

    public void setPlaying(boolean playing) {
        this.isPlaying = playing;
    }

    public void setPlayDuration(long playDuration) {
        this.playDuration = playDuration;
    }

    public interface TextPicker {
        String getText(TextView textView);
    }
}
