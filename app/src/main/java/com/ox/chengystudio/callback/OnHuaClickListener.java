package com.ox.chengystudio.callback;

import com.ox.greendao.Hua;
import com.ox.greendao.TimeArea;

import java.util.List;

/**
 * Created by ox on 16/12/29.
 */
public interface OnHuaClickListener {
    void onHuaAdd();

    void onHuaUpdate(Hua hua, List<TimeArea> listTimeArea);

    void onHuaDelete(Hua hua);

    void onHuaHasDrawn(Hua hua);
}
