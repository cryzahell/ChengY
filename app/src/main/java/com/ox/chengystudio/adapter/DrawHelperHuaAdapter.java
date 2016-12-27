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
import com.ox.greendao.Hua;
import com.ox.greendao.TimeArea;
import com.ox.greendao.TimeAreaDao;
import com.ox.mylibrary.util.UtilCollection;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    private OnHuaClickListener onHuaClickListener;

    public void setOnHuaClickListener(OnHuaClickListener onHuaClickListener) {
        this.onHuaClickListener = onHuaClickListener;
    }

    public DrawHelperHuaAdapter(Activity activity, TimeAreaDao timeAreaDao) {
        super(activity);
        this.timeAreaDao = timeAreaDao;
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
        HuaVH holder = (HuaVH) convertView.getTag();
        holder.entity = hua;
        holder.tvHuaName.setText(hua.getName());
        if (hua.getHasDrawn()) {
            holder.tvHuaStatus.setTextColor(mActivity.getResources().getColor(R.color.colorFontGray));
            holder.tvHuaStatus.setText(MessageFormat.format("状态:{0}", "已完成"));
        } else {
            holder.tvHuaStatus.setTextColor(mActivity.getResources().getColor(R.color.colorFontGreen));
            holder.tvHuaStatus.setText(MessageFormat.format("状态：{0}", "未完成"));
        }
        List<TimeArea> timeList = timeAreaDao.queryBuilder()
                .where(TimeAreaDao.Properties.HuaId.eq(hua.getId()))
                .orderAsc(TimeAreaDao.Properties.StartTime)
                .list();
        holder.listTimeArea = timeList;
        holder.llTimeContainer.removeAllViews();
        if (!UtilCollection.isEmpty(timeList)) {
            long minLeftMillis = -1;
            Calendar calCur = Calendar.getInstance();
            for (TimeArea timeArea : timeList) {
                TextView tvTime = (TextView) mInflater.inflate(R.layout.simple_text, null);
                tvTime.setTextSize(12);
                tvTime.setTextColor(mActivity.getResources().getColor(R.color.colorFontGray));
                holder.llTimeContainer.addView(tvTime);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH : mm");
                Long millisStart = getMillisInToday(calCur, timeArea.getStartTime());
                Long millisEnd = getMillisInToday(calCur, timeArea.getEndTime());
                if (millisEnd < millisStart) {
                    millisEnd += DateUtils.DAY_IN_MILLIS;
                }
                tvTime.setText(
                        MessageFormat.format(
                                "{0} ~ {1}",
                                dateFormat.format(new Date(millisStart)),
                                dateFormat.format(new Date(millisEnd))
                        )
                );
                if (minLeftMillis < 0 && timeArea.getStartTime() > 0 && millisStart > calCur.getTimeInMillis()) {
                    tvTime.setTextColor(mActivity.getResources().getColor(R.color.colorFontGreen));
                    minLeftMillis = millisStart - calCur.getTimeInMillis();
                    holder.tvMinLeftTime.setText(
                            MessageFormat.format(
                                    "时间（离现在最近还有： {0}）",
                                    new SimpleDateFormat("HH : mm : ss").format(new Date(minLeftMillis))
                            )
                    );
                    if (DateUtils.HOUR_IN_MILLIS > minLeftMillis) {
                        holder.tvMinLeftTime.setTextColor(mActivity.getResources().getColor(R.color.colorFontRed));
                    } else {
                        holder.tvMinLeftTime.setTextColor(mActivity.getResources().getColor(R.color.colorFontGray));
                    }
                }
            }
        }
        return convertView;
    }

    @NonNull
    private Long getMillisInToday(Calendar calCur, Long millis) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTimeInMillis(millis);
        calStart.set(Calendar.YEAR, calCur.get(Calendar.YEAR));
        calStart.set(Calendar.MONTH, calCur.get(Calendar.MONTH));
        calStart.set(Calendar.DAY_OF_MONTH, calCur.get(Calendar.DAY_OF_MONTH));
        return calStart.getTimeInMillis();
    }

    class HuaVH extends BaseViewHolder<Hua> {

        @InjectView(R.id.tv_hua_name)
        TextView tvHuaName;
        @InjectView(R.id.tv_hua_status)
        TextView tvHuaStatus;
        @InjectView(R.id.tv_min_left_time)
        TextView tvMinLeftTime;
        @InjectView(R.id.ll_hua_time_container)
        LinearLayout llTimeContainer;
        @InjectView(R.id.btn_hua_update)
        Button btnHuaUpdate;
        @InjectView(R.id.btn_hua_delete)
        Button btnHuaDelete;
        @InjectView(R.id.btn_hua_done)
        Button btnHuaDone;

        List<TimeArea> listTimeArea;

        HuaVH(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @OnClick(R.id.btn_hua_update)
        void clickUpdate(View view) {
            if (onHuaClickListener != null) {
                onHuaClickListener.onHuaUpdate(entity, listTimeArea);
            }
        }

        @OnClick(R.id.btn_hua_delete)
        void clickDelete(View view) {
            if (onHuaClickListener != null) {
                onHuaClickListener.onHuaDelete(entity);
            }
        }

        @OnClick(R.id.btn_hua_done)
        void clickHuaDone(View view) {
            if (onHuaClickListener != null) {
                entity.setHasDrawn(true);
                onHuaClickListener.onHuaHasDrawn(entity);
            }
        }

    }

    public interface OnHuaClickListener {
        void onHuaAdd();

        void onHuaUpdate(Hua hua, List<TimeArea> listTimeArea);

        void onHuaDelete(Hua hua);

        void onHuaHasDrawn(Hua hua);
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
