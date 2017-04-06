package com.ox.chengystudio.decor;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ox.chengystudio.R;
import com.ox.chengystudio.base.BaseViewHolder;
import com.ox.chengystudio.callback.EmptyOnClickListener;
import com.ox.chengystudio.callback.OnHuaSaveListener;
import com.ox.greendao.Hua;
import com.ox.greendao.TimeArea;
import com.ox.mylibrary.util.UtilCollection;
import com.ox.mylibrary.util.UtilMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by admin on 2016/12/27.
 */

public class DecorUpdateHua {

    private static final int MAX_TIME_AREA_COUNT_FOR_EACH_HUA = 6;

    private static final int OTHER_VIEW_COUNT_IN_TIME_EDIT_CONTAINER = 1;

    public static void updateHua(Activity activity, OnHuaSaveListener onHuaSaveListener, Hua hua, List<TimeArea> listTimeArea) {
        editHua(activity, onHuaSaveListener, hua, listTimeArea);
    }

    public static void addHua(Activity activity, OnHuaSaveListener onHuaSaveListener) {
        editHua(activity, onHuaSaveListener, null, null);
    }

    private static void editHua(Activity activity, OnHuaSaveListener onHuaSaveListener, Hua hua, List<TimeArea> listTimeArea) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        LayoutInflater inflater = activity.getLayoutInflater();
        View contentView = inflater.inflate(R.layout.decor_add_hua, null);
        ViewHolder holder = new ViewHolder(contentView, activity, onHuaSaveListener, hua, listTimeArea);
        //添加显示时分的EditText
        addTimeView(inflater, holder);

        int lastEditPosition = holder.llEndTime.getChildCount() - 1;
        EditText lastEdit = (EditText) ((ViewGroup) holder.llEndTime.getChildAt(lastEditPosition)).getChildAt(1);
        lastEdit.setImeOptions(EditorInfo.IME_ACTION_DONE);
        if (hua != null && !UtilCollection.isEmpty(listTimeArea)) {
            holder.tvTitle.setText("编辑");
            holder.editHuaName.setText(hua.getName());
            if (hua.getHasDrawn()) {
                holder.rbHasDone.setChecked(true);
            } else {
                holder.rbHasNotDone.setChecked(true);
            }
            for (int i = 0; i < MAX_TIME_AREA_COUNT_FOR_EACH_HUA; i++) {
                setTimeData(holder.llStartTime, i, listTimeArea, true);
                setTimeData(holder.llEndTime, i, listTimeArea, false);
            }
        } else {
            holder.tvTitle.setText("新增");
            holder.rbHasNotDone.setChecked(true);
        }

        decorView.addView(contentView);
        fadeIn(activity, contentView);
    }

    private static void setTimeData(ViewGroup viewGroup, int position, List<TimeArea> listTimeArea, boolean isStart) {
        ViewGroup timeContainer = (ViewGroup) viewGroup.getChildAt(position + OTHER_VIEW_COUNT_IN_TIME_EDIT_CONTAINER);
        EditText editHour = (EditText) timeContainer.getChildAt(0);
        EditText editMinute = (EditText) timeContainer.getChildAt(1);
        long millis = isStart ? listTimeArea.get(position).getStartTime()
                : listTimeArea.get(position).getEndTime();
        long hour = millis / DateUtils.HOUR_IN_MILLIS;
        long minute = millis % DateUtils.HOUR_IN_MILLIS / DateUtils.MINUTE_IN_MILLIS;
        editHour.setText(hour > 0 ? String.valueOf(hour) : "");
        editMinute.setText(minute > 0 ? String.valueOf(minute) : "");
    }

    private static void addTimeView(LayoutInflater inflater, ViewHolder holder) {
        for (int i = 0; i < MAX_TIME_AREA_COUNT_FOR_EACH_HUA * 2; i++) {
            View timeView = inflater.inflate(R.layout.group_time, null);
            if (i % 2 == 0) {
                holder.llStartTime.addView(timeView);
            } else {
                holder.llEndTime.addView(timeView);
            }
        }
    }

    private static void fadeIn(Activity activity, View contentView) {
        Animation animFadeIn = AnimationUtils.loadAnimation(activity, android.R.anim.fade_in);
        contentView.startAnimation(animFadeIn);
    }

    private static void fadeOut(final Activity activity, final View contentView) {
        Animation animFadeOut = AnimationUtils.loadAnimation(activity, android.R.anim.fade_out);
        animFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ((ViewGroup) activity.getWindow().getDecorView()).removeView(contentView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        contentView.startAnimation(animFadeOut);
    }

    private static boolean checkTime_hour(int value) {
        return value >= 0 && value < 24;
    }

    private static boolean checkTime_minute(int value) {
        return value >= 0 && value < 60;
    }

    private static List<Long> getTimeList(LinearLayout container, Activity mActivity) {
        ArrayList<Long> timeList = new ArrayList<>();
        for (int i = OTHER_VIEW_COUNT_IN_TIME_EDIT_CONTAINER; i < container.getChildCount(); i++) {
            ViewGroup timeView = (ViewGroup) container.getChildAt(i);
            EditText editHour = (EditText) timeView.getChildAt(0);
            EditText editMinute = (EditText) timeView.getChildAt(1);
            String strHour = editHour.getText().toString();
            String strMinute = editMinute.getText().toString();
            int hour = Integer.parseInt(TextUtils.isEmpty(strHour) ? "0" : strHour);
            int minute = Integer.parseInt(TextUtils.isEmpty(strMinute) ? "0" : strMinute);
            boolean checkHour = checkTime_hour(hour);
            boolean checkMinute = checkTime_minute(minute);
            if (checkHour && checkMinute) {
                int colorFontGray = mActivity.getResources().getColor(R.color.colorFontGray);
                editHour.setTextColor(colorFontGray);
                editMinute.setTextColor(colorFontGray);
                long millis = DateUtils.HOUR_IN_MILLIS * hour + DateUtils.MINUTE_IN_MILLIS * minute;
                timeList.add(millis);
            } else {
                UtilMessage.showToast(mActivity, "请输入正确的时间");
                if (checkHour) {
                    editMinute.setTextColor(mActivity.getResources().getColor(android.R.color.holo_red_dark));
                }
                return null;
            }
        }
        return timeList;
    }

    static class ViewHolder extends BaseViewHolder {

        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @InjectView(R.id.edit_hua_name)
        EditText editHuaName;
        @InjectView(R.id.rg_hua_status)
        RadioGroup rgHuaStatus;
        @InjectView(R.id.rb_has_not_done)
        RadioButton rbHasNotDone;
        @InjectView(R.id.rb_has_done)
        RadioButton rbHasDone;
        @InjectView(R.id.ll_start_time)
        LinearLayout llStartTime;
        @InjectView(R.id.ll_end_time)
        LinearLayout llEndTime;
        @InjectView(R.id.btn_save)
        Button btnSave;
        @InjectView(R.id.btn_cancel)
        Button btnCancel;

        private static int ACTION_TYPE_ADD = 0;
        private static int ACTION_TYPE_UPDATE = 1;

        private View contentView;
        private Activity mActivity;
        private OnHuaSaveListener onHuaSaveListener;
        @Nullable
        private Hua hua;
        @Nullable
        private List<TimeArea> listTimeArea;
        private int actionType = ACTION_TYPE_ADD;

        public ViewHolder(View itemView, Activity activity,
                          OnHuaSaveListener onHuaSaveListener,
                          @Nullable Hua hua, @Nullable List<TimeArea> listTimeArea) {
            super(itemView);
            itemView.setOnClickListener(new EmptyOnClickListener());
            contentView = itemView;
            mActivity = activity;
            this.onHuaSaveListener = onHuaSaveListener;
            this.hua = hua;
            this.listTimeArea = listTimeArea;
            setActionType();
            ButterKnife.inject(this, itemView);
        }

        private void setActionType() {
            if (hua != null && !UtilCollection.isEmpty(listTimeArea)) {
                actionType = ACTION_TYPE_UPDATE;
            }
            if (hua == null) {
                hua = new Hua();
            }
            if (UtilCollection.isEmpty(listTimeArea)) {
                listTimeArea = new ArrayList<>();
            }
        }

        @OnClick(R.id.btn_save)
        void clickSave(View view) {
            if (onHuaSaveListener != null) {
                List<Long> startList = getTimeList(llStartTime, mActivity);
                List<Long> endList = getTimeList(llEndTime, mActivity);
                if (!UtilCollection.isEmpty(startList) && !UtilCollection.isEmpty(endList)) {
                    String strName = editHuaName.getText().toString();
                    if (TextUtils.isEmpty(strName)) {
                        UtilMessage.showToast(mActivity, "请输入名称");
                        return;
                    } else {
                        assert hua != null;
                        hua.setName(strName);
                        hua.setHasDrawn(rgHuaStatus.getCheckedRadioButtonId() == R.id.rb_has_done);
                    }
                    saveTimeData(startList, endList);
                    save();
                    fadeOut(mActivity, contentView);
                }
            }
        }

        private void saveTimeData(List<Long> startList, List<Long> endList) {
            for (int i = 0; i < MAX_TIME_AREA_COUNT_FOR_EACH_HUA; i++) {
                TimeArea timeArea;
                if (actionType == ACTION_TYPE_UPDATE) {
                    assert listTimeArea != null;
                    timeArea = listTimeArea.get(i);
                } else {
                    timeArea = new TimeArea();
                }
                timeArea.setStartTime(startList.get(i));
                timeArea.setEndTime(endList.get(i));
                assert listTimeArea != null;
                listTimeArea.add(timeArea);
            }
        }

        private void save() {
            if (actionType == ACTION_TYPE_ADD) {
                onHuaSaveListener.add(hua, listTimeArea);
            } else {
                onHuaSaveListener.update(hua, listTimeArea);
            }
        }

        @OnClick(R.id.btn_cancel)
        void clickCancel(View view) {
            fadeOut(mActivity, contentView);
        }

    }
}
