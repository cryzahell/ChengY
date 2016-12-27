package com.ox.chengystudio.callback;

import com.ox.greendao.Hua;
import com.ox.greendao.TimeArea;

import java.util.List;

/**
 * Created by admin on 2016/12/27.
 */

public interface OnHuaSaveListener {
    void add(Hua hua, List<TimeArea> listTimeArea);
    void update(Hua hua, List<TimeArea> listTimeArea);
}
