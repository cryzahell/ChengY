package com.ox.chengystudio.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ox.chengystudio.R;
import com.ox.chengystudio.base.BaseListAdapter;
import com.ox.chengystudio.base.BaseViewHolder;
import com.ox.chengystudio.callback.OnCityClickListener;
import com.ox.greendao.City;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by admin on 2016/12/26.
 */

public class DrawHelperCityAdapter extends BaseListAdapter<City> {
    private static final int ITEM_VIEW_TYPE_CITY = 0;
    private static final int ITEM_VIEW_TYPE_ADD = 1;

    private int selectPosition = -1;

    public void resetSelectPosition() {
        selectPosition = -1;
    }

    private OnCityClickListener onCityClickListener;

    public void setOnCityClickListener(OnCityClickListener onCityClickListener) {
        this.onCityClickListener = onCityClickListener;
    }

    public DrawHelperCityAdapter(Activity activity) {
        super(activity);
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return TextUtils.isEmpty(getItem(position).getName()) ?
                ITEM_VIEW_TYPE_ADD : ITEM_VIEW_TYPE_CITY;
    }

    @Override
    public View mGetView(int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)) {
            case ITEM_VIEW_TYPE_CITY:
                return typeView_city(position, convertView, parent);
            case ITEM_VIEW_TYPE_ADD:
                return typeView_add(position, convertView, parent);
            default:
                return new View(mActivity);
        }
    }

    private View typeView_add(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_draw_helper_city_add, parent, false);
            AddVH holder = new AddVH(convertView);
            convertView.setTag(holder);
        }
        return convertView;
    }

    private View typeView_city(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_draw_helper_city, parent, false);
            CityVH holder = new CityVH(convertView);
            convertView.setTag(holder);
        }
        City city = getItem(position);
        CityVH holder = (CityVH) convertView.getTag();
        holder.entity = city;
        holder.position = position;
        holder.tvCityName.setText(city.getName());
        //第一次进来手动选择第一个 城市类别的选项
        firstInInit(position, city);
        //处理右边线
        treatRightLine(position, holder);
        if (position == selectPosition) {
            holder.tvCityDelete.setVisibility(View.VISIBLE);
        } else {
            holder.tvCityDelete.setVisibility(View.GONE);
        }
        return convertView;
    }

    private void treatRightLine(int position, CityVH holder) {
        if (position == selectPosition) {
            holder.viewRightLine.setVisibility(View.INVISIBLE);
        } else {
            holder.viewRightLine.setVisibility(View.VISIBLE);
        }
    }

    private void firstInInit(int position, City city) {
        if (selectPosition < 0) {
            selectPosition = position;
            if (onCityClickListener != null) {
                onCityClickListener.onCityClick(city);
            }
        }
    }

    class CityVH extends BaseViewHolder<City> {

        @InjectView(R.id.tv_city_name)
        TextView tvCityName;
        @InjectView(R.id.tv_city_delete)
        TextView tvCityDelete;
        @InjectView(R.id.view_right_line)
        View viewRightLine;

        CityVH(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @OnClick(R.id.tv_city_name)
        void clickCityName(View view) {
            if (onCityClickListener != null) {
                onCityClickListener.onCityClick(entity);
                selectPosition = position;
                notifyDataSetChanged();
            }
        }

        @OnClick(R.id.tv_city_delete)
        void clickCityDelete(View view) {
            if (onCityClickListener != null) {
                onCityClickListener.onCityDelete(entity);
            }
        }
    }

    class AddVH extends BaseViewHolder<City> {

        @InjectView(R.id.btn_city_add)
        Button btnCityAdd;

        AddVH(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @OnClick(R.id.btn_city_add)
        void clickCityAdd(View view) {
            if (onCityClickListener != null) {
                onCityClickListener.onCityAdd();
            }
        }

    }
}
