package com.ox.chengystudio.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ox.chengystudio.R;
import com.ox.chengystudio.base.BaseListAdapter;
import com.ox.chengystudio.base.BaseViewHolder;
import com.ox.chengystudio.callback.OnHuaClickListener;
import com.ox.chengystudio.custom.AutoRefreshTextView;
import com.ox.chengystudio.ioc.OO;
import com.ox.chengystudio.ioc.OO_resColor;
import com.ox.greendao.Hua;
import com.ox.greendao.TimeArea;
import com.ox.greendao.TimeAreaDao;
import com.ox.mylibrary.util.UtilCollection;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by admin on 2016/12/26.
 */

public class DrawHelperHuaAdapter extends BaseListAdapter<Hua> {
    private static final int ITEM_VIEW_TYPE_HUA = 0;
    private static final int ITEM_VIEW_TYPE_ADD = 1;
    private final TimeAreaDao timeAreaDao;
    private final ExecutorService es;

    @OO_resColor(R.color.colorFontGray)
    int colorFontGray;
    @OO_resColor(R.color.colorFontGreen)
    int colorFontGreen;
    @OO_resColor(R.color.colorFontRed)
    int colorFontRed;

    private OnHuaClickListener onHuaClickListener;

    public DrawHelperHuaAdapter(Activity activity, TimeAreaDao timeAreaDao) {
        super(activity);
        this.timeAreaDao = timeAreaDao;
        es = Executors.newFixedThreadPool(4);
        OO.inject(mActivity, this);
    }

    public void recycle() {
        es.shutdown();
    }

    public void setOnHuaClickListener(OnHuaClickListener onHuaClickListener) {
        this.onHuaClickListener = onHuaClickListener;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return TextUtils.isEmpty(getItem(position).getName()) ?
                ITEM_VIEW_TYPE_ADD : ITEM_VIEW_TYPE_HUA;
    }

    @Override
    public View mGetView(int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)) {
            case ITEM_VIEW_TYPE_HUA:
                return typeView_hua(position, convertView, parent);
            case ITEM_VIEW_TYPE_ADD:
                return typeView_add(position, convertView, parent);
            default:
                return new View(mActivity);
        }
    }

    private View typeView_add(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_draw_helper_hua_add, parent, false);
            AddVH holder = new AddVH(convertView);
            convertView.setTag(holder);
        }
        return convertView;
    }

    private View typeView_hua(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_draw_helper_hua, parent, false);
            HuaVH holder = new HuaVH(convertView);
            convertView.setTag(holder);
        }
        Hua hua = getItem(position);
        final HuaVH holder = (HuaVH) convertView.getTag();
        holder.entity = hua;
        bindViews(hua, holder);
        List<TimeArea> timeList = queryTimeAreas(hua);
        holder.listTimeArea = timeList;
        addTimeViews(holder, timeList);
        return convertView;
    }

    private List<TimeArea> queryTimeAreas(Hua hua) {
        return timeAreaDao.queryBuilder()
                .where(TimeAreaDao.Properties.HuaId.eq(hua.getId()))
                .orderAsc(TimeAreaDao.Properties.StartTime)
                .list();
    }

    private void bindViews(Hua hua, HuaVH holder) {
        holder.tvHuaName.setText(hua.getName());
        if (hua.getHasDrawn()) {
            holder.tvHuaStatus.setTextColor(colorFontGray);
            holder.tvHuaStatus.setText(MessageFormat.format("状态:{0}", "已完成"));
        } else {
            holder.tvHuaStatus.setTextColor(colorFontGreen);
            holder.tvHuaStatus.setText(MessageFormat.format("状态：{0}", "未完成"));
        }
    }

    private void addTimeViews(HuaVH holder, List<TimeArea> timeList) {
        holder.llTimeContainer.removeAllViews();
        if (!UtilCollection.isEmpty(timeList)) {
            long minLeftMillis = Long.MAX_VALUE;
            Calendar calCur = Calendar.getInstance();
            for (TimeArea timeArea : timeList) {
                TextView tvTime = (TextView) mInflater.inflate(R.layout.simple_text, null);
                tvTime.setTextSize(12);
                tvTime.setTextColor(colorFontGray);
                holder.llTimeContainer.addView(tvTime);
                DateFormat dateFormat = new SimpleDateFormat("MM-dd HH : mm");
                Long millisStart = getMillisInToday(timeArea.getStartTime());
                Long millisEnd = getMillisInToday(timeArea.getEndTime());
                if (timeArea.getStartTime() > 0 && millisStart < calCur.getTimeInMillis()) {
                    millisStart += DateUtils.DAY_IN_MILLIS;
                }
                if (timeArea.getEndTime() > 0 && millisEnd < millisStart) {
                    millisEnd += DateUtils.DAY_IN_MILLIS;
                }
                tvTime.setText(
                        MessageFormat.format(
                                "{0} ~ {1}",
                                dateFormat.format(new Date(millisStart)),
                                dateFormat.format(new Date(millisEnd))
                        )
                );
                long tempLeftMillis = millisStart - calCur.getTimeInMillis();
                if (tempLeftMillis > 0 && tempLeftMillis < minLeftMillis
                        && timeArea.getStartTime() > 0 && millisStart > calCur.getTimeInMillis()) {
                    setTvTextColorGray(holder.llTimeContainer);
                    tvTime.setTextColor(colorFontGreen);
                    minLeftMillis = calculateLeftMillis(millisStart);
                    startToRefresh(holder.tvRefreshMinLeftTime, millisStart);
                }
            }
        }
    }

    private void setTvTextColorGray(ViewGroup tvContainer) {
        for (int i = 0; i < tvContainer.getChildCount(); i++) {
            ((TextView) tvContainer.getChildAt(i)).setTextColor(
                    mActivity.getResources().getColor(R.color.colorFontGray)
            );
        }
    }

    private void startToRefresh(AutoRefreshTextView autoRefreshTextView, final long millisStart) {
        autoRefreshTextView.startToRefresh(es, new AutoRefreshTextView.TextPicker() {
            @Override
            public String getText(TextView textView) {
                long minLeft = calculateLeftMillis(millisStart);
                textView.setTextColor(colorFontGray);
                Calendar calTemp = Calendar.getInstance();
                calTemp.setTimeInMillis(minLeft);
                int hour = calTemp.get(Calendar.HOUR_OF_DAY);
                if (hour > 0) {
                    textView.setTextColor(colorFontGray);
                } else {
                    textView.setTextColor(colorFontRed);
                }
                return MessageFormat.format(
                        "时间（离现在最近还有： {0}）",
                        new SimpleDateFormat("HH : mm : ss").format(new Date(minLeft))
                );
            }
        });
    }

    private long calculateLeftMillis(Long millisStart) {
        return millisStart - Calendar.getInstance().getTimeInMillis() - TimeZone.getDefault().getRawOffset();
    }

    @NonNull
    private Long getMillisInToday(Long millis) {
        Calendar calTemp = Calendar.getInstance();
        calTemp.set(Calendar.HOUR_OF_DAY, (int) (millis / DateUtils.HOUR_IN_MILLIS));
        calTemp.set(Calendar.MINUTE, (int) (millis % DateUtils.HOUR_IN_MILLIS / DateUtils.MINUTE_IN_MILLIS));
        calTemp.set(Calendar.SECOND, 0);
        return calTemp.getTimeInMillis();
    }

    class HuaVH extends BaseViewHolder<Hua> {

        @InjectView(R.id.tv_hua_name)
        TextView tvHuaName;
        @InjectView(R.id.tv_hua_status)
        TextView tvHuaStatus;
        @InjectView(R.id.tv_refresh_min_left_time)
        AutoRefreshTextView tvRefreshMinLeftTime;
        @InjectView(R.id.ll_hua_time_container)
        LinearLayout llTimeContainer;
        @InjectView(R.id.tv_hua_update)
        TextView tvHuaUpdate;
        @InjectView(R.id.tv_hua_delete)
        TextView tvHuaDelete;
        @InjectView(R.id.tv_hua_done)
        TextView tvHuaDone;

        List<TimeArea> listTimeArea;

        HuaVH(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @OnClick(R.id.tv_hua_update)
        void clickUpdate(View view) {
            if (onHuaClickListener != null) {
                onHuaClickListener.onHuaUpdate(entity, listTimeArea);
            }
        }

        @OnClick(R.id.tv_hua_delete)
        void clickDelete(View view) {
            if (onHuaClickListener != null) {
                onHuaClickListener.onHuaDelete(entity);
            }
        }

        @OnClick(R.id.tv_hua_done)
        void clickHuaDone(View view) {
            if (onHuaClickListener != null) {
                entity.setHasDrawn(true);
                onHuaClickListener.onHuaHasDrawn(entity);
            }
        }

    }

    class AddVH extends BaseViewHolder<Hua> {

        @InjectView(R.id.btn_hua_add)
        Button btnHuaAdd;
        @InjectView(R.id.cb_hua_show_done)
        CheckBox cbShowDone;
        @InjectView(R.id.cb_hua_sort_by_time)
        CheckBox cbSortByTime;

        AddVH(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @OnClick(R.id.btn_hua_add)
        void clickHuaAdd(View view) {
            if (onHuaClickListener != null) {
                onHuaClickListener.onHuaAdd();
            }
        }

    }
}
