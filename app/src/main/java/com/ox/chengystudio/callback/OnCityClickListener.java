package com.ox.chengystudio.callback;

import com.ox.greendao.City;

/**
 * Created by admin on 2016/12/27.
 */
public interface OnCityClickListener {
    void onCityClick(City city);

    void onCityDelete(City city);

    void onCityAdd();
}
